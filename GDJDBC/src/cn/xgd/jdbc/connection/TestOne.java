package cn.xgd.jdbc.connection;

import java.util.List;

import cn.xgd.jdbc.bean.DBTableContext;
import cn.xgd.jdbc.buildClass.Student;

/**
 * 
 * @author xgd
 * @date 2020��4��3��
 */
public class TestOne {
	public static void main(String[] args) {
//		DBTableContext.updataJavaPOFile();
//		DBTableContext.loadPOTables();
		GDQueryManager manager = new GDQueryManager();
		Student stu = new Student();
		stu.setId(5);
		stu.setName("qianba");
		stu.setAddress("��³ľ��11");
		stu.setClass_id(1);
		stu.setDescription("ʮ����");
//		manager.update(stu,new String[]{"name","description","class_id","address"});
		List<Student> list =  manager.queryRows("select * from student where id > ?", Student.class,new Object[]{4} );
		for (Student student : list) {
			System.out.println(student.getName() + student.getAddress() + student.getId());
		}
	}
}
