/**
 * 
 */
package cn.xgd.jdbc.bean;

import java.io.IOException;
import java.util.Properties;

/**  
 * 
 * @author xgd  
 * @date 2020年3月31日  
 */
public class GDConfiguration {
	
	private String DBDriver;
	private String DBLinker;
	private String username;
	private String password;
	private String beanPath;
	private String packageName;
	private Integer poolMax;
	private Integer	poolMin;
	
	
	/**
	 * 构造器
	 */
	private GDConfiguration() {
		//
		Properties p = new Properties();
		try {
			p.load(Thread.currentThread().getContextClassLoader().
					getResourceAsStream("GDConfiguration.properties"));
			this.DBDriver = p.getProperty("DBDriver");
			this.DBLinker = p.getProperty("DBLinker");
			this.username = p.getProperty("username");
			this.password = p.getProperty("password");
			this.beanPath = p.getProperty("beanPath");
			this.poolMax = Integer.valueOf(p.getProperty("poolMax"));
			this.poolMin = Integer.valueOf(p.getProperty("poolMin"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**  
	 * 单例类的创建
	 * @return  GDConfiguration
	 */  
	public static GDConfiguration shareConfiguration() {
		return InnerConfig.config;
	}
	
	private static class InnerConfig{
		static GDConfiguration config = new GDConfiguration();
	}

	public String getDBDriver() {
		return DBDriver;
	}

	public String getDBLinker() {
		return DBLinker;
	}

	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getBeanPath() {
		return beanPath;
	}

	public String getPackageName() {
		return packageName;
	}

	public Integer getPoolMax() {
		return poolMax;
	}

	public Integer getPoolMin() {
		return poolMin;
	}

	
}