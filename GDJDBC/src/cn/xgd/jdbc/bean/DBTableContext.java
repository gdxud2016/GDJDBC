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
 * @date 2020年3月31日  
 */
public class DBTableContext {
	
	//这个表中的tableInfo也是定义的一个类大概意思就是能够记录下整张表，这里这个map就能记录整个database
    public static  Map<String,DBTableInfo> tables=new HashMap<>();     

    /**
     * 将po的class对象和表关联
     */
    public static Map<Class, DBTableInfo> poClassTableMap=new HashMap<>();  
    
    private DBTableContext(){};
                                                                                       //无参构造很重要

    static{
        try {
            Connection con = GDDBManager.defalutManager().getConnection(); 
          //这里原视频中没有说，大概是 可以从连接中搜索获取一些元数据，%是类似所有
            DatabaseMetaData dbmd=con.getMetaData();
          //TABLE那就是要查询的就是表了，但是这里出现了问题，意外的搜索出了创建表的时间
            ResultSet tableRet=dbmd.getTables(null, "%", "%",new String[]{"TABLE"});
          //记录的那个表，捯饬了半天也没解决。 然后之后是放到了一个结果集里
            System.out.println(tableRet);                               
            while(tableRet.next()){//遍历查找出来的结果
            	 //获得表名
                String tableName=(String)tableRet.getObject("TABLE_NAME"); 
            //  然后把表名先把上说的可以记录下整个表的new出来，具体的后面再说
                DBTableInfo ti= new DBTableInfo();
                ti.setTab_name(tableName);
                tables.put(tableName, ti); //放到记录整个数据库的那个表里
                
                ResultSet set=dbmd.getColumns(null, "%", tableName ,"%");      //这里根据表名获取字段集，
                while(set.next()){
                	DBColumnKey ci= new DBColumnKey(set.getString("COLUMN_NAME"),set.getString("TYPE_NAME"));//可以看出这里获取了字段的名字和类型
                    ti.getKeysSet().put(set.getString("COLUMN_NAME"),ci);               //这里是放到表映射，加载表的字段
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
            
           //这里就可以理解为，你在java中建立了一个表格，不是完全填好数据得表格，只是单纯得有每个数据类型在哪个表是不是主键得表示
           //有了这个标识以后可以在query中找出数据库要操作得是哪个，再从query中操作数据库。
        } catch (Exception e) {
            // TODO: handle exception
        }
      //因为我们要再数据库中操作，程序事先是不知道你表中得每一项叫什么名字就没办法根据数据可定义好类，在用类来操作数据库    
        updataJavaPOFile();
      //这里我们就可以根据数据库中获取的表框架，获取表的名字，表中字段类型，生成这个数据库的java类                                                              
        //然后是每次加载这个类的时候更新　　　　　　　　　　　　　　　　　　　　　
        loadPOTables();                                            
    }
                                                                
    public static void updataJavaPOFile(){
    	//这里就通过这个tables表然后用后面的一个java类实现了在项目中构造java类
        Map<String,DBTableInfo> map= DBTableContext.tables;           
        for(DBTableInfo t:map.values()){
            JavaFileUtils.createJavaPOFile(t, new MysqlTypeConventor());
        }    
    }
                                                                   
    public static void loadPOTables(){                
    	//这里就是用反射把这个类和产生自哪个表放在了一个表里，在后面的操作有用
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
