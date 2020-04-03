package	cn.xgd.jdbc.buildClass;

import	java.util.*;
import	java.sql.Date;
import	java.sql.Timestamp;

@SuppressWarnings(value = "all")
public	class Student {

	private String address;
	private Integer class_id;
	private String name;
	private String description;
	private Integer id;


	public Student() {
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	public Integer getClass_id() {
		return class_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

}