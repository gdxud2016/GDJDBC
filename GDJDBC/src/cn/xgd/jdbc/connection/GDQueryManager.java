package cn.xgd.jdbc.connection;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
 * 
 * @author xgd
 * @date 2020年3月31日
 */
public class GDQueryManager implements GDQuery {

	@Override
	public int excuteDML(String sql, Object[] params) {
		System.out.println("##################\r\n" + sql + params);
		// TODO Auto-generated method stub
		Connection connection = GDDBManager.defalutManager().getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			int index = 1;
			for (Object object : params) {
				ps.setObject(index++, object);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if (connection != null) {
				GDDBManager.defalutManager().DeprecateConnection(connection);
			}
		}
	}

	@Override
	public void insert(Object obj) {
		// TODO Auto-generated method stub
		// insert into table_name () values ()
		StringBuilder builder = new StringBuilder();
		DBTableInfo info = DBTableContext.poClassTableMap.get(obj.getClass());

		builder.append("insert into " + info.getTab_name() + " (");
		List<Object> resultList = new ArrayList<Object>();
		for (String key : info.getKeysSet().keySet()) {
			Object valueOB = ReflectUtils.objectGetMethod(obj,key);
			if (valueOB == null) {
				continue;
			}
			resultList.add(valueOB);
			builder.append(key + ",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(") value (");

		Iterator<Object> iterator = resultList.iterator();
		System.err.println(resultList);
		while (iterator.hasNext()) {
			builder.append("?,");
			iterator.next();
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");
		excuteDML(builder.toString(), resultList.toArray());
	}

	@Override
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		// delete from table where id = ?
		StringBuilder builder = new StringBuilder();
		DBTableInfo info = DBTableContext.poClassTableMap.get(obj.getClass());
		DBColumnKey primaryKey = info.getPrimary_key();

		builder.append("delete from " + info.getTab_name() + " where " + primaryKey.getColumn_name() + " = ?");

		excuteDML(builder.toString(), new Object[] { ReflectUtils.objectGetMethod(obj, primaryKey.getColumn_name()) });
	}

	@Override
	public int update(Object obj, String[] field) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		DBTableInfo info = DBTableContext.poClassTableMap.get(obj.getClass());
		DBColumnKey primaryKey = info.getPrimary_key();

		builder.append("update " + info.getTab_name() + " set ");
		List<Object> resultList = new ArrayList<Object>();
		for (String key : field) {
			resultList.add(ReflectUtils.objectGetMethod(obj,key));
			builder.append(key + "=?,");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(" where " + primaryKey.getColumn_name() + "=?");
		resultList.add(ReflectUtils.objectGetMethod(obj, primaryKey.getColumn_name()));

		return excuteDML(builder.toString(), resultList.toArray());
	}

	@Override
	public List queryRows(String sql, Class clazz, Object[] params) {
		Connection collecton = GDDBManager.defalutManager().getConnection();
		PreparedStatement ps = null;
		ResultSet set = null;
		try {
			ps = collecton.prepareStatement(sql);
			int i = 1;
			for (Object object : params) {
				ps.setObject(i++, object);
			}
			set = ps.executeQuery();
			ResultSetMetaData meta = set.getMetaData();
			List<Object> list = new ArrayList<Object>();
			while (set.next()) {
				Constructor<?> cs = clazz.getConstructor();
				Object objc = cs.newInstance();
				for (int j = 1; j <= meta.getColumnCount(); j++) {
					String column_name = meta.getColumnLabel(j);
					ReflectUtils.objectSetMethod(objc, column_name, set.getObject(column_name));
				}
				list.add(objc);
			}
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (set != null) {
				try {
					set.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			if (collecton != null) {
				GDDBManager.defalutManager().DeprecateConnection(collecton);
			}
		}
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