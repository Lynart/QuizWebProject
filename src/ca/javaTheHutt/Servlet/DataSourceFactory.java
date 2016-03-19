package ca.javaTheHutt.Servlet;

import java.sql.SQLException;
import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceFactory {

	public static DataSource getMySQLDataSource(){
		MysqlDataSource mysqlDS = null;
		mysqlDS = new MysqlDataSource();
		mysqlDS.setURL("jdbc:mysql://zenit.senecac.on.ca/dps904_161a11");
		mysqlDS.setUser("dps904_161a11");
		mysqlDS.setPassword("jmQL4399");
		return mysqlDS;
	}
	
}
