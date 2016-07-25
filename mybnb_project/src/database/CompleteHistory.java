package database;

import java.sql.*;

public class CompleteHistory {
	public void addLog(Connection conn, int userID, String logMessage) {
		try {
			Statement stmt = conn.createStatement();
			String addNewLog = "INSERT INTO " + 
					"CompleteHistory (u_id, log_reason) VALUES (\'" +
					userID + "\', \'" + logMessage + "\');";
			stmt.executeUpdate(addNewLog);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
}
