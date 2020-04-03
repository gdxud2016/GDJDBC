package cn.xgd.jdbc.utils;

import cn.xgd.jdbc.bean.DBTableInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import cn.xgd.jdbc.bean.DBColumnKey;
import cn.xgd.jdbc.bean.GDConfiguration;

/**  
 * 
 * @author xgd  
 * @date 2020Äê4ÔÂ2ÈÕ  
 */
public class GDJavaUtils {
//	public String 
	public static String  tableInfoToString(DBTableInfo info) {
		StringBuilder builder = new StringBuilder();
		GDConfiguration config = GDConfiguration.shareConfiguration();
		builder.append("package\t" + config.getPackageName() + ";\r\n\n");
		
		builder.append("import\tjava.util.*;\r\n");
		builder.append("import\tjava.sql.Date;\r\n");
		builder.append("import\tjava.sql.Timestamp;\r\n");
		builder.append("\r\n");
		String className = GDStringUtils.uppercaseString(info.getTab_name());
		builder.append("@SuppressWarnings(value = \"all\")\r\n");
		builder.append("public\tclass " + className + " {\r\n\n");
		
		for (DBColumnKey dBKey : info.getKeysSet().values()) {
			builder.append("\tprivate " + dBKey.getType_name() + " " + dBKey.getColumn_name() + ";\r\n");
		}
		
		builder.append("\r\n\n");
		builder.append("\tpublic " + className + "() {\r\n\t}\r\n\n");
		for (DBColumnKey dbKey : info.getKeysSet().values()) {
			builder.append("\tpublic void set" + GDStringUtils.uppercaseString(dbKey.getColumn_name()) + 
					"(" + dbKey.getType_name() + " " + dbKey.getColumn_name() + ") {\r\n");
			builder.append("\t\tthis." + dbKey.getColumn_name() + " = " + dbKey.getColumn_name() + ";\r\n");
			builder.append("\t}\r\n\n");
			
			builder.append("\tpublic " + dbKey.getType_name() + " get" + GDStringUtils.uppercaseString(dbKey.getColumn_name()) +"() {\r\n");
			builder.append("\t\treturn " + dbKey.getColumn_name() + ";\r\n");
			builder.append("\t}\r\n\n");
			
		}
		builder.append("}");
		return builder.toString();
	}
	
	public static void buildJavaClassFromTableInfo(DBTableInfo info) {
		String javaDataString = tableInfoToString(info);
		System.out.println(javaDataString);
		
		GDConfiguration config = GDConfiguration.shareConfiguration();
		String path = config.getBeanPath() + config.getPackageName().replaceAll("\\.", "\\\\");
		File parentFile = new File(path);
		if (parentFile.isFile()) {
			parentFile.deleteOnExit();
		}
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		String className = GDStringUtils.uppercaseString(info.getTab_name());
		File creatFile = new File(parentFile,className + ".java");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(creatFile));
			writer.write(javaDataString);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}




