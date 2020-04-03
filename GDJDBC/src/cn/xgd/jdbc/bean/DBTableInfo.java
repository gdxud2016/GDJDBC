package cn.xgd.jdbc.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
 * 
 * @author xgd  
 * @date 2020年4月1日  
 */
public class DBTableInfo {
	/**
	 *tab_name 数据表的名称
	 */  
	private String tab_name;
	
	/**
	 *set 存放表里所有键的容器
	 */  
	private Map<String,DBColumnKey> keysSet;
	
	/**
	 *set 存放表里所有主键的容器
	 */  
	private List<DBColumnKey> primaryKeysSet;
	
	/**
	 *primary_key 主键
	 */  
	private DBColumnKey primary_key;
	
	public DBTableInfo(){
		this.primaryKeysSet = new ArrayList<DBColumnKey>();
		this.keysSet = new HashMap<String, DBColumnKey>();
	}

//	setter and getter
	public String getTab_name() {
		return tab_name;
	}
  
	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}

	
	public Map<String, DBColumnKey> getKeysSet() {
		return keysSet;
	}
	
	public void setKeysSet(Map<String, DBColumnKey> keysSet) {
		this.keysSet = keysSet;
	}

	
	public List<DBColumnKey> getPrimaryKeysSet() {
		return primaryKeysSet;
	}


	public void setPrimaryKeysSet(List<DBColumnKey> primaryKeysSet) {
		this.primaryKeysSet = primaryKeysSet;
	}


	public DBColumnKey getPrimary_key() {
		return primary_key;
	}

	public void setPrimary_key(DBColumnKey primary_key) {
		this.primary_key = primary_key;
	}
	
	
}
