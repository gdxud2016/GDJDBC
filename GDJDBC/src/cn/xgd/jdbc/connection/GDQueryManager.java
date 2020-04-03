package cn.xgd.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import cn.xgd.jdbc.bean.DBColumnKey;
import cn.xgd.jdbc.bean.DBTableContext;
import cn.xgd.jdbc.bean.DBTableInfo;
import cn.xgd.jdbc.utils.GDStringUtils;
import cn.xgd.jdbc.utils.ReflectUtils;


/**  
 * 管理数据的类
 * @author xgd  
 * @date 2020年3月31日  
 */  
public class GDQueryManager implements GDQuery{
	
	 
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

	@Override
	public List executeQueryTemplate(String sql, Object[] params, Class clazz) {
		return null;
	}

	@Override
	public int excuteDML(String sql, Object[] params) {
		// TODO Auto-generated method stub
		Connection connection = GDDBManager.defalutManager().getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			int index = 1;
			for (Object object : params) {
				ps.setObject(index ++,object);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}

	@Override
	public void insert(Object obj) {
		// TODO Auto-generated method stub
		// insert into table_name () values ()
		StringBuilder builder = new StringBuilder();
		DBTableInfo info = DBTableContext.poClassTableMap.get(obj.getClass());
		
		builder.append("insert into " + info.getTab_name() + " (" );
		List<Object> resultList  = new ArrayList<Object>();
		for (String key : info.getKeysSet().keySet()) {
			resultList.add(ReflectUtils.objectGetMethod(obj,"set" + GDStringUtils.uppercaseString(key)));
			builder.append(key +  ",");
		}
		builder.deleteCharAt(builder.length() -1);
		builder.append(" values (");
		
		Iterator<Object> iterator = resultList.iterator();
		while (iterator.next() != null) {
			builder.append("?,");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");
		excuteDML(builder.toString(),resultList.toArray());
	}
	

	@Override
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		//delete from table where id = ?
		StringBuilder builder = new StringBuilder();
		DBTableInfo info = DBTableContext.poClassTableMap.get(obj.getClass());
		DBColumnKey primaryKey = info.getPrimary_key();
		
		builder.append("delete from " + info.getTab_name() + " where " + primaryKey.getColumn_name() + " = ?");
		
		excuteDML(builder.toString(),new Object[] {ReflectUtils.objectGetMethod(objc, primaryKey.getColumn_name())});
	}

	@Override
	public int update(Object obj, String[] fieldNames) {
		// TODO Auto-generated method stub
		return 0{
	}

	@Override
	public List queryRows(String sql, Class clazz, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryUniqueRows(String sql, Class clazz, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryValue(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number queryNumber(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}
}