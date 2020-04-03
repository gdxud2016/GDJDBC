package	cn.xgd.jdbc.buildClass;

import	java.util.*;
import	java.sql.Date;
import	java.sql.Timestamp;

@SuppressWarnings(value = "all")
public	class Sys_config {

	private Timestamp set_time;
	private String set_by;
	private String variable;
	private String value;


	public Sys_config() {
	}

	public void setSet_time(Timestamp set_time) {
		this.set_time = set_time;
	}

	public Timestamp getSet_time() {
		return set_time;
	}

	public void setSet_by(String set_by) {
		this.set_by = set_by;
	}

	public String getSet_by() {
		return set_by;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getVariable() {
		return variable;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}