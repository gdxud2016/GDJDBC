package cn.xgd.jdbc.utils;

public class GDStringUtils {
	public static String uppercaseString(String lowString) {
		if (lowString == null || lowString.length() == 0) {
			return lowString;
		}
		return lowString.substring(0, 1).toUpperCase() + lowString.substring(1);
	}
}
