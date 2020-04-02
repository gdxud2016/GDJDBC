package cn.xgd.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**  
 * 管理数据的类
 * @author xgd  
 * @date 2020年3月31日  
 */  
public class GDQueryManager {
	
	 
	public static void main(String[] args) {
		Connection  coll = null;
		PreparedStatement ps = null;
		ResultSet set = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			coll = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2?serverTimezone=UTC&"
					+ "characterEncoding=utf-8","root","xgd");
			ps = (PreparedStatement) coll.prepareStatement("select * from student");
			set = ps.executeQuery();
			while (set.next()) {
				System.out.println(set.getInt("id") + set.getString("name"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (set != null) {
					set.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			try {
				if (coll != null) {
					coll.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
}