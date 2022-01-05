package Pedidos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
	
	
	
	
	public Connection conexionDeli() throws Exception {
		String url = "jdbc:sqlserver://172.31.1.14:1433;databaseName=MisRecetas";
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String userName = "sstreitenberger";
		String password = ".4ls344pp5";
		Class.forName(driver).newInstance();
		return DriverManager.getConnection(url, userName, password);
	}

}


