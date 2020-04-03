
package cn.xgd.jdbc.connection;

import java.util.List;

/**
 * 数据库查询接口
 * 
 * @author xgd
 * @date 2020年3月31日
 */
public interface GDQuery {

	public void insert(Object obj);

	public void delete(Object obj);

	public int update(Object obj, String[] field);

	public int excuteDML(String sql, Object[] params);

	public List queryRows(String sql, Class clazz, Object[] params);

	public Object queryUniqueRows(String sql, Class clazz, Object[] params);

	public Object queryValue(String sql, Object[] params);

	public Number queryNumber(String sql, Object[] params);

}
