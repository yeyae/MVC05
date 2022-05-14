package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	/*
	 * 	DB와 연결을 생성해주는 클래스
	 * 	URL, USERNAME, PASSWORD	
	 * 
	 */
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERNAME = "minseok";
	private static final String PASSWORD = "1234";
	
	// 연결 객체를 생성해서 주는 메소드
	// 이 메소드에서 만든 객체를 통해서 pstmt 사용
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 드라이버 클래스 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection(URL,USERNAME,PASSWORD);
	}
	
}
