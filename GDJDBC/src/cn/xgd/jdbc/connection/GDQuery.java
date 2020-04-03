
package cn.xgd.jdbc.connection;

import java.util.List;

/**
 * ���ݿ��ѯ�ӿ�
 * 
 * @author xgd
 * @date 2020��3��31��
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
