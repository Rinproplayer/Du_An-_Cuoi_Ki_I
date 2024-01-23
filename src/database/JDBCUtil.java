package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

	public static Connection getConnection() {
		Connection c = null;

		try {

			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			String url = "jdbc:mySQl://localhost:3306/qltnd";
			String username = "root";
			String password = "Rin12345@a";

			c = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return c;
	}

	public static void closeConnection(Connection c) {

	}

}
