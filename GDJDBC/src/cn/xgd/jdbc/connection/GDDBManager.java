package cn.xgd.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.xgd.jdbc.bean.GDConfiguration;


/**  
 * 
 * @author xgd  
 * @date 2020��4��1��  
 */
public class GDDBManager {
	private List<Connection> container;
	private boolean isLoadMoreConnection = false;
	/**
	 * ����˽�й�����
	 */
	private GDDBManager() {
		container = new ArrayList<Connection>();
		try {
			Class.forName(GDConfiguration.shareConfiguration().getDBDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static class InnerManagerClass{
		static GDDBManager manager = new GDDBManager();
	}
	
	/**  
	 * ������connection�����ĵ�����
	 * @return GDDBManager
	 */  
	public static GDDBManager defalutManager() {
		return InnerManagerClass.manager;
	}
	
	public Connection createCollection(){
		try {
			synchronized (this) {
				Connection co = DriverManager.
						getConnection(GDConfiguration.shareConfiguration().getDBLinker());
				container.add(co);
				return co;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Connection getConnection() {
		Integer min = GDConfiguration.shareConfiguration().getPoolMin();
		Integer max = GDConfiguration.shareConfiguration().getPoolMax();
		int poolSize = container.size();
		if (poolSize < min && isLoadMoreConnection == false){
			int addCount = (max + min) / 2 - poolSize + 1;
			loadMoreConnection(addCount);
		}
		if (poolSize == 0) {
			return createCollection();
		}
		return container.remove(poolSize - 1);
	}
	
	private void loadMoreConnection(Integer count){
		new Thread(){
			@Override
			public void run() {
				isLoadMoreConnection = true;
				for (int i = 0; i < count ; i++) {
					createCollection();
				}
				isLoadMoreConnection = false;
			};
		}.start();
	}
	
	public void DeprecateConnection(Connection co) {
		Integer max = GDConfiguration.shareConfiguration().getPoolMax();
		int poolSize = container.size();
		if (poolSize < max) {
			synchronized (this) {
				container.add(co);
			}
		}
	}
}