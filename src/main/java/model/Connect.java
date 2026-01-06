package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	public Connection getConnection()throws Exception 
	{
		Connection cn = null;
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String url="jdbc:sqlserver://DESKTOP-6O6QNFK;databaseName=TrainTicketBooking;user=sa; password=123";
			cn= DriverManager.getConnection(url);
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
            System.out.println("Kết nối thất bại!");
		}
		
		return cn;
	}

}
