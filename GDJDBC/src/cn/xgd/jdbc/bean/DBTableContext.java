/**
 * 
 */
package cn.xgd.jdbc.bean;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import cn.xgd.jdbc.connection.GDDBManager;

/**  
 * 
 * @author xgd  
 * @date 2020��3��31��  
 */
public class DBTableContext {
	
	//������е�tableInfoҲ�Ƕ����һ��������˼�����ܹ���¼�����ű��������map���ܼ�¼����database
    public static  Map<String,DBTableInfo> tables=new HashMap<>();     

    /**
     * ��po��class����ͱ����
     */
    public static Map<Class, DBTableInfo> poClassTableMap=new HashMap<>();  
    
    private DBTableContext(){};
                                                                                       //�޲ι������Ҫ

    static{
        try {
            Connection con = GDDBManager.defalutManager().getConnection(); 
          //����ԭ��Ƶ��û��˵������� ���Դ�������������ȡһЩԪ���ݣ�%����������
            DatabaseMetaData dbmd=con.getMetaData();
          //TABLE�Ǿ���Ҫ��ѯ�ľ��Ǳ��ˣ�����������������⣬������������˴������ʱ��
            ResultSet tableRet=dbmd.getTables(null, "%", "%",new String[]{"TABLE"});
          //��¼���Ǹ��������˰���Ҳû����� Ȼ��֮���Ƿŵ���һ���������
            System.out.println(tableRet);                               
            while(tableRet.next()){//�������ҳ����Ľ��
            	 //��ñ���
                String tableName=(String)tableRet.getObject("TABLE_NAME"); 
            //  Ȼ��ѱ����Ȱ���˵�Ŀ��Լ�¼���������new����������ĺ�����˵
                DBTableInfo ti= new DBTableInfo();
                ti.setTab_name(tableName);
                tables.put(tableName, ti); //�ŵ���¼�������ݿ���Ǹ�����
                
                ResultSet set=dbmd.getColumns(null, "%", tableName ,"%");      //������ݱ�����ȡ�ֶμ���
                while(set.next()){
                	DBColumnKey ci= new DBColumnKey(set.getString("COLUMN_NAME"),set.getString("TYPE_NAME"));//���Կ��������ȡ���ֶε����ֺ�����
                    ti.getKeysSet().put(set.getString("COLUMN_NAME"),ci);               //�����Ƿŵ���ӳ�䣬���ر���ֶ�
                }
                
                ResultSet set2=dbmd.getPrimaryKeys(null, "%", tableName);
                while(set2.next()){                                                               
                    DBColumnKey ci2 = (DBColumnKey)ti.getKeysSet().get(set2.getObject("COLUMN_NAME"));
                    ci2.setType_name("1");
                    ti.getPrimaryKeysSet().add(ci2);
                }
                if(ti.getKeysSet().size()>0){
                    ti.setPrimary_key(ti.getPrimaryKeysSet().get(0));
                } 
            }
            
           //����Ϳ������Ϊ������java�н�����һ����񣬲�����ȫ������ݵñ��ֻ�ǵ�������ÿ�������������ĸ����ǲ��������ñ�ʾ
           //���������ʶ�Ժ������query���ҳ����ݿ�Ҫ���������ĸ����ٴ�query�в������ݿ⡣
        } catch (Exception e) {
            // TODO: handle exception
        }
      //��Ϊ����Ҫ�����ݿ��в��������������ǲ�֪������е�ÿһ���ʲô���־�û�취�������ݿɶ�����࣬���������������ݿ�    
        updataJavaPOFile();
      //�������ǾͿ��Ը������ݿ��л�ȡ�ı��ܣ���ȡ������֣������ֶ����ͣ�����������ݿ��java��                                                              
        //Ȼ����ÿ�μ���������ʱ����¡�����������������������������������������
        loadPOTables();                                            
    }
                                                                
    public static void updataJavaPOFile(){
    	//�����ͨ�����tables��Ȼ���ú����һ��java��ʵ��������Ŀ�й���java��
        Map<String,DBTableInfo> map= DBTableContext.tables;           
        for(DBTableInfo t:map.values()){
            JavaFileUtils.createJavaPOFile(t, new MysqlTypeConventor());
        }    
    }
                                                                   
    public static void loadPOTables(){                
    	//��������÷���������Ͳ������ĸ��������һ������ں���Ĳ�������
        for(DBTableInfo tableInfo:tables.values()){
            try{
                Class c=Class.forName(GDConfiguration.shareConfiguration().getPackageName()+
                        "."+StringUTils.firstChar2UpCase(tableInfo.getTab_name()));
                poClassTableMap.put(c, tableInfo);
            }catch(Exception e){
                e.printStackTrace();
                
            }
        }    
        
    }
}
