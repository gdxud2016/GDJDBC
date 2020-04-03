package cn.xgd.jdbc.connection;
 
public class MySQLTypeConvertor implements TypeConvertor {
	
	@Override
	public String SQLTypeConvertorToObjc(String sqlType) {
		if("varchar".equalsIgnoreCase(sqlType)||"char".equalsIgnoreCase(sqlType)){
            return "String";
        }else if("int".equalsIgnoreCase(sqlType)
                ||"tinyint".equalsIgnoreCase(sqlType)){
            return "Integer";
        }else if("double".equalsIgnoreCase(sqlType)){
            return "double";
            
        }else if("timestamp".equalsIgnoreCase(sqlType)){
            return "Timestamp";
        }
		return null;
	}
}
