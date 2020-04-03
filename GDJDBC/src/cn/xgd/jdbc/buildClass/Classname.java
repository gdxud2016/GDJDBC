package	cn.xgd.jdbc.buildClass;

import	java.util.*;
import	java.sql.Date;
import	java.sql.Timestamp;

@SuppressWarnings(value = "all")
public	class Classname {

	private Integer class_id;
	private String className;
	private Integer id;


	public Classname() {
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	public Integer getClass_id() {
		return class_id;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}