package com.figure.core.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.figure.core.constant.Constants.EXACT_MATCH_FIELDS;


public class PageHelper<T> {

    /**
     * sql查询类型
     */
    public enum ConditionType {

        /** 使用mybtisplus框架查询类 */
        QUERYWRAPPER(1),

        /** 使用HashMap 参数查询 */
        MAP(2);

        private final int value;

        ConditionType(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

    }



    private final Constructor<? extends T> ctor;

    private T t;

    private ConditionType conditionType = ConditionType.QUERYWRAPPER;

    public PageHelper(Class<? extends T> impl) throws NoSuchMethodException {
        this.ctor = impl.getConstructor();
    }

    public PageHelper(Class<? extends T> impl,ConditionType conditionType) throws NoSuchMethodException {
        this.ctor = impl.getConstructor();
        this.conditionType = conditionType;
    }


    public void newInstance() throws Exception {
        t = ctor.newInstance();
    }

    public static OrderItem string2OrderItem(String sorter){
          if(StringUtils.isEmpty(sorter)){
              return new OrderItem();
          }
          sorter  = URLDecoder.decode(sorter);
          String[] strs  =  sorter.replace("{","").replace("}","").split(":");
          if(strs.length<2){
              return new OrderItem();
          }
          String col = strs[0].replace("\"","");
          String order = strs[1].replace("\"","");
          return new OrderItem(col,order.equals("ascend"));
    }

    public PageWrapper<T> getPageWrapper(HttpServletRequest request) throws Exception {
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String[]> multiParamsMap = new HashMap<>();
        Map<String, String> singleParamsMap = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String paraName = enumeration.nextElement();
            String[] paraValue = request.getParameterValues(paraName);
            if (paraValue.length > 1) {
                multiParamsMap.put(paraName, paraValue);
            } else {
                singleParamsMap.put(paraName, paraValue[0]);
                if(paraName.startsWith("multi_")){
                    String new_paraName = paraName.replace("multi_","");
                    multiParamsMap.put(new_paraName,paraValue[0].split(","));
                }
            }
        }
        String current = request.getParameter("current");
        current = current == null ? "1" : current;
        String pageSize = request.getParameter("pageSize");
        pageSize = pageSize == null ? "100000000" : pageSize;
        String sorter = request.getParameter("sorter");
        String filter = request.getParameter("filter");
        String[]  exactMatchFields = request.getParameterValues(EXACT_MATCH_FIELDS);

        if(exactMatchFields==null){
            exactMatchFields = new String[0];
        }

        OrderItem orderItem = PageHelper.string2OrderItem(sorter);

        newInstance();

        Page<T> page = new Page<>(Integer.parseInt(current), Integer.parseInt(pageSize));

        page.addOrder(orderItem);

        Field[] fs = VOHelper.getFs(t.getClass());

        switch (conditionType) {
            case QUERYWRAPPER:
                QueryWrapper<T> queryWrapper = new QueryWrapper<>();

                try {
                    for (int i = 0; i < fs.length; i++) {
                        Field f = fs[i];
                        String fieldName = f.getName();
                        Class fieldClass = f.getType();
                        if (multiParamsMap.containsKey(fieldName)) {
                            if (fieldClass.equals(Date.class)) {
                                queryWrapper.ge(f.getName(), multiParamsMap.get(fieldName)[0]);
                                queryWrapper.le(f.getName(), multiParamsMap.get(fieldName)[1]);
                            }else{
                                queryWrapper.in(f.getName(),multiParamsMap.get(fieldName));
                            }
                        }
                        if (singleParamsMap.containsKey(fieldName)) {
                            if (fieldClass.equals(String.class)) {
                                String fieldValue = singleParamsMap.get(fieldName);
                                if (StringHelper.isNumeric(fieldValue)||containsString(exactMatchFields,fieldName)) {
                                    queryWrapper.eq(f.getName(), fieldValue);
                                } else {
                                    queryWrapper.like(f.getName(), fieldValue);
                                }
                            } else if (fieldClass.equals(Float.class)) {
                                queryWrapper.eq(f.getName(), singleParamsMap.get(fieldName));
                            } else if (fieldClass.equals(Integer.class)) {
                                queryWrapper.eq(f.getName(), singleParamsMap.get(fieldName));
                            } else if (fieldClass.equals(Long.class)){
                                queryWrapper.eq(f.getName(), singleParamsMap.get(fieldName));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return new PageWrapper<T>(page, queryWrapper);
            case MAP:
                try {
                    for (int i = 0; i < fs.length; i++) {
                        Field f = fs[i];
                        String fieldName = f.getName();
                        Class fieldClass = f.getType();
                        if (multiParamsMap.containsKey(fieldName)) {
                            if (fieldClass.equals(Date.class)||fieldName.equals("collectTime")) {
                                singleParamsMap.put("begin_" + f.getName(), multiParamsMap.get(fieldName)[0]);
                                singleParamsMap.put("end_" + f.getName(), multiParamsMap.get(fieldName)[1]);
                            }else{
                                String[] vs = multiParamsMap.get(fieldName);
                                if(vs.length>0){
                                    singleParamsMap.put("multi_"+f.getName(),"'"+String.join("','",vs)+"'");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return new PageWrapper<T>(page, singleParamsMap);
            default:
                new PageWrapper<T>();

        }
        return null;
    }

    private  boolean containsString(String[] strings, String target) {
        for (String str : strings) {
            if (str.contains(target)) {
                return true;
            }
        }
        return false;
    }
}
