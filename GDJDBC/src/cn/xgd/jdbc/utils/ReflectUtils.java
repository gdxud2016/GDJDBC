package cn.xgd.jdbc.utils;

import java.lang.reflect.Method;

public class ReflectUtils {
	
	public static void objectSetMethod(Object objc,String fieldName,Object fieldV) {
		try {
			Method m = objc.getClass().getDeclaredMethod(fieldName,fieldV.getClass());
			m.setAccessible(true);
			m.invoke(objc, fieldV);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object objectGetMethod(Object objc,String fieldName) {
		try {
			Method m = objc.getClass().getDeclaredMethod(fieldName);
			m.setAccessible(true);
			return m.invoke(objc);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
