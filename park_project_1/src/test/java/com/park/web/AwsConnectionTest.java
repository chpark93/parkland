package com.park.web;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class AwsConnectionTest {
	
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String URL = "jdbc:mysql://chpark-service.couuzbeqhvyi.ap-northeast-2.rds.amazonaws.com:3306/chpark";
	private final String USER = "chpark";
	private final String PW = "choi3492";

	
	@Test
	public void test() {
		
		try {
			Class.forName(DRIVER);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PW)) {
			System.out.println("connection" + connection);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
