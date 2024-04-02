package com.figure.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JSONUtil {

	private static final Gson gson = new GsonBuilder()
			.setDateFormat("YYYY-MM-dd HH:mm:ss").serializeNulls().create();


	public static Gson getGson(){
		return gson;
	}
	
	/**
	 * Java对象转换成JSON字符串
	 * @param obj 要转换的对象
	 * @return
	 */
	public static String Object2JsonStr(Object obj){
		String json = gson.toJson(obj);
		return json;
	}
	
	/**
	 * Java对象转换成JsonObject
	 *
	 * @param obj 要转换的对象
	 * @return
	 */
	public static JsonObject Object2JsonObj(Object obj){
		JsonObject jsonObject = (JsonObject) gson.toJsonTree(obj);
		return jsonObject;
	}

    /**
     * JSON格式转换为class对象
     *
     * @param json     Json字符串
     * @param classOfT class
     * @return
     */
    public static Object json2Object(String json, Class classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * JSON格式转换为class对象
     *
     * @param json Json字符串
     * @param c    class
     * @return
     */
    public static <T> T json2ObjectByT(String json, Class<T> c) {
        return gson.fromJson(json, c);
    }
}
