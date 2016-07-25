package database;

import java.sql.*;

public class RentHistory {
	public void addToHistory(Connection conn, String addRent) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(addRent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}

	public void viewHistory(Connection conn, int userID) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM BookingsHistory WHERE u_id=" + userID;
			ResultSet historicListings = stmt.executeQuery(query);
			System.out.println("Here are the generated rented places for the user with ID " +
					userID);
			while(historicListings.next()) {
				int listingID = historicListings.getInt("l_id");
				String date = historicListings.getString("b_date");
				String addr = historicListings.getString("l_address");
				System.out.println("Rent ID: " + listingID + "       " + date +
						"       " + addr);
				System.out.println("------------------------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public int getHosterID(Connection conn, int listingID) {
		int hosterID = 0;
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Listings WHERE l_id=" + listingID;
			ResultSet results = stmt.executeQuery(query);

			while(results.next()) {
				hosterID = results.getInt("u_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hosterID;
	}
}
