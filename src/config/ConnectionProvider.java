package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	/*
	 * 	DB�� ������ �������ִ� Ŭ����
	 * 	URL, USERNAME, PASSWORD	
	 * 
	 */
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERNAME = "minseok";
	private static final String PASSWORD = "1234";
	
	// ���� ��ü�� �����ؼ� �ִ� �޼ҵ�
	// �� �޼ҵ忡�� ���� ��ü�� ���ؼ� pstmt ���
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// ����̹� Ŭ���� �ε�
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection(URL,USERNAME,PASSWORD);
	}
	
}
