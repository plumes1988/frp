package com.figure.core.helper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class VOHelper {

	public static Field[] getFs(Class<?> cls){
		Field[] fs = cls.getDeclaredFields();
		if(cls.getSuperclass() != Object.class){
			Field[] parentFs = cls.getSuperclass().getDeclaredFields();
			fs = arrayAdd(parentFs, fs);
			if(cls.getSuperclass().getSuperclass()!= Object.class){
				fs = arrayAdd(getFs(cls.getSuperclass()),fs);
			}
		}
		return fs;
	}

	public static Field[] arrayAdd(Field[] a,Field[] b){
		Field[] c = new Field[a.length+b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	public static Object getModel(Class<?> cls, Map<String, String[]> map,boolean multiValuesSetField) {
		try {
			Object obj = cls.newInstance();
			Field[] fs = getFs(cls);
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible(true);
				try {
					if (f.getName().equals("method")) {
						continue;
					}
					if (f.getName().equals("status")) {
						f.set(obj, 1);
						continue;
					}
					if (map.containsKey(f.getName())) {
						String[] o = map.get(f.getName());
						if(o.length>1&&!multiValuesSetField){
							continue;
						}
						if (o.length > 0&&!o[0].equals("")) {
							if (f.getType().equals(Long.class))
								f.set(obj, getLong(o[0]));

							else if (f.getType().equals(Integer.class))
								f.set(obj, getInter(o[0]));

							else if (f.getType().equals(Float.class))
								f.set(obj, getFloat(o[0]));
							else if (f.getType().equals(Byte.class))
								f.set(obj, getByte(o[0]));
							else if (f.getType().equals(Date.class))
								f.set(obj, getDate(o[0]));

							else if (f.getType().equals(String.class)){
								if(o.length>1){
									StringBuilder sb = new StringBuilder();
									for(Object ob:o){
										sb.append(ob.toString()).append(",");
									}
									if(sb.length()>0){
										f.set(obj, sb.deleteCharAt(sb.length()-1).toString());
									}
								}else{
									f.set(obj, getStringFormSubmit(o[0]));
								}
							}
							else if (f.getType().equals(boolean.class))
								f.set(obj, getBoolean(o[0]));
							else if (f.getType().equals(BigDecimal.class)) {
								f.set(obj, getDecimal(o[0]));
							}
							else if (f.getType().equals(Short.class)) {
								f.set(obj, getShort(o[0]));
							}
							else {
								try {
									f.set(obj, o[0]);
								} catch (IllegalAccessException ie) {
									ie.printStackTrace();
									System.out.print(f.getType() + o[0]);
								} catch (IllegalArgumentException ia) {
									ia.printStackTrace();
									System.out.print(f.getType() + o[0]);
								}

							}

						}
					}


					// if (value.containsKey(f.getName()))
					// f.set(obj, value.get(f.getName()));
					// else
					// f.set(obj, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return obj;

		} catch (Exception e) {

		}
		return null;
	}

	public static Object getModel(Object obj, Map<String, String[]> map) {
		try {

			Field[] fs = getFs(obj.getClass());
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible(true);
				try {
					if (map.containsKey(f.getName())) {
						Object[] o = map.get(f.getName());
						if (o.length > 0) {
							if (f.getType().equals(Long.class))
								f.set(obj, getLong(o[0].toString()));

							if (f.getType().equals(Integer.class))
								f.set(obj, getInter(o[0].toString()));

							if (f.getType().equals(Float.class))
								f.set(obj, getFloat(o[0].toString()));

							if (f.getType().equals(Byte.class))
								f.set(obj, getByte(o[0].toString()));

							if (f.getType().equals(Date.class))
								f.set(obj, getDate(o[0].toString()));

							if (f.getType().equals(String.class)){
								if(fs.length>1){
									StringBuilder sb = new StringBuilder();
									for(Object ob:o){
										sb.append(ob.toString()).append(",");
									}
									if(sb.length()>0){
										f.set(obj, sb.deleteCharAt(sb.length()-1).toString());
									}
								}else{
									f.set(obj, getStringFormSubmit(o[0].toString()));
								}
							}
							if (f.getType().equals(boolean.class))
								f.set(obj, getBoolean(o[0].toString()));
						}
					}

					// if (value.containsKey(f.getName()))
					// f.set(obj, value.get(f.getName()));
					// else
					// f.set(obj, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return obj;

		} catch (Exception e) {

		}
		return null;
	}


	public static Map<String, Object> ObjectToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class cls = obj.getClass();

		Field[] fs = cls.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			f.setAccessible(true);
			try {
				Object val = f.get(obj);

				map.put(f.getName(), val);
			} catch (Exception e) {

			}
		}
		return map;

	}

	public static String VOToJSON(Object obj, Class<?> cls) {
		StringBuffer str = new StringBuffer();
		// Map<String, Object> map = new HashMap<String, Object>();
		// Class cls = (Class) obj.getClass();
		str.append("{");
		Field[] fs = cls.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			if (i > 0)
				str.append(",");
			Field f = fs[i];
			f.setAccessible(true);
			try {
				Object val = f.get(obj);
				// map.put(f.getName(), val);
				str.append("\"" + f.getName() + "\":\"" + getString(val) + "\"");
			} catch (Exception e) {
				str.append("\"" + f.getName() + "\":\"" + "\"");
			}
		}
		str.append("}");
		return str.toString();
	}

	private static String getString(Object v) {
		if (v == null)
			return "";
		else
			return v.toString().trim();
	}

	public static String ListToJSON(List<?> list, Class<?> cls) {
		StringBuffer str = new StringBuffer();
		str.append("[");
		for (int i = 0; i < list.size(); i++) {
			if (i > 0)
				str.append(",");
			str.append(VOToJSON(list.get(i), cls));
		}
		str.append("]");
		return str.toString();
	}

	private static BigDecimal getDecimal(String obj) {
		return BigDecimal.valueOf(getLong(obj));
		// java.math.BigDecimal
	}





	private static Long getLong(String obj) {
		if (obj == null || obj.equals(""))
			return null;
		else {
			try {
				return Long.parseLong(obj);
			} catch (Exception e) {
				return null;
			}
		}
	}
	private static Short getShort(String obj) {
		if (obj == null || obj.equals(""))
			return null;
		else {

			try {
				return Short.parseShort(obj);
			} catch (Exception e) {
				return null;
			}
		}
	}

	private static Integer getInter(String obj) {
		if (obj == null || obj.equals(""))
			return null;
		else {

			try {
				return Integer.parseInt(obj);
			} catch (Exception e) {
				return null;
			}
		}
	}

	private static float getFloat(String obj) {
		if (obj == null || obj.equals(""))
			return 0;
		else {
			try {
				return Float.parseFloat(obj);
			} catch (Exception e) {
				return 0;
			}
		}
	}

	private static Byte getByte(String obj) {
		if (obj == null || obj.equals(""))
			return 0;
		else {
			try {
				return Byte.parseByte(obj);
			} catch (Exception e) {
				return 0;
			}
		}
	}

	private static Date getDate(String obj) {
		if (obj == null || obj.equals(""))
			return null;
		else {
			try {
				if(obj.length()==8||obj.length()==7){
					return CommonUtil.format(obj, DateHelper.patterns_masks[12]);
				}else if(obj.length()==10){
					return CommonUtil.format(obj, DateHelper.patterns_masks[4]);
				}else{
					return CommonUtil.format(obj, DateHelper.patterns_masks[2]);
				}
			} catch (Exception e) {
				return null;
			}
		}
	}

	private static byte[] getBtyes(Object obj) {
		if (obj == null || obj.toString().equals(""))
			return null;
		else {
			try {
				// return //Byte.
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	private static boolean getBoolean(String obj) {
		if (obj == null || obj.equals(""))
			return false;
		else {
			try {
				return obj.equals("1")
						|| obj.equalsIgnoreCase("true");
			} catch (Exception e) {
				return false;
			}
		}
	}

	private static String getStringFormSubmit(String obj) {
		if (obj == null||obj.equals(""))
			return null;
		return obj;
	}

	public static Map<String, String> getParamters(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			map.put(paramName, request.getParameter(paramName));
//
//			 entry.getValue());

		}
		return map;

	}

	public static Object setValue(Map<String, Object> value, Class<?> cls) {
		try {
			Object obj = cls.newInstance();
			Field[] fs = cls.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible(true);
				try {
					if (value.containsKey(f.getName()))
						f.set(obj, value.get(f.getName()));
					else
						f.set(obj, "");
				} catch (Exception e) {

				}
			}
			return obj;

		} catch (Exception e) {

		}
		return null;
	}

}
