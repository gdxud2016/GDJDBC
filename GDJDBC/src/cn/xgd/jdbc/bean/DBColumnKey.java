package cn.xgd.jdbc.bean;

/**  
 *	数据库每列的属性
 * @author xgd  
 * @date 2020年3月31日  
 */
public class DBColumnKey {
	//name
	private String column_name;
	
	private String type_name;
	
	private String sql_type_name;

	public DBColumnKey(String column_name,String sqlType, String type_name) {
		super();
		this.column_name = column_name;
		this.sql_type_name = sqlType;
		this.type_name = type_name;
	}


	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}


	public void setSql_type_name(String sql_type_name) {
		this.sql_type_name = sql_type_name;
	}


	public String getSql_type_name() {
		return sql_type_name;
	}
	
}
