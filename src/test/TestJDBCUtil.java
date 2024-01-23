package test;

import java.sql.Connection;

import database.JDBCUtil;
import view.HoSoThueView;

public class TestJDBCUtil {
public static void main(String[] args) {
	Connection connection = JDBCUtil.getConnection();
	System.out.println(connection);
	new HoSoThueView();
}
}
