package database;

import java.sql.*;
import java.util.Scanner;

public class Search {
	public void searchListings(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			Scanner userInput = new Scanner(System.in);

			System.out.println("You are currently using Search mode.");
			System.out.println("How would you like to search for listings?");
			System.out.println("Would you like to search by \"PRICE\", \"POSTALCODE\", " +
					" or \"ADDRESS\"?");
			System.out.print("Input: ");
			String input = userInput.nextLine();

			if(input.equals("PRICE")) {
				System.out.println("What price range would you like? (Note: " +
						"the listings will reflect the statement <= PRICE_VAL.)");
				float priceVal = userInput.nextFloat();
				String query = "SELECT * FROM Listings WHERE l_price<=" + priceVal;

				ResultSet listingResults = stmt.executeQuery(query);
				while(listingResults.next()) {
					int listingID = listingResults.getInt("l_id");
					String listingAddress = listingResults.getString("l_address");
					String postalCode = listingResults.getString("l_postalCode");
					String availability = listingResults.getString("l_availability");
					double listingPrice = listingResults.getFloat("l_price");
					String amenities = listingResults.getString("l_amenities");
					double longitude = listingResults.getFloat("l_longitude");
					double lattitude = listingResults.getFloat("l_lattitude");

					double total = (longitude * longitude) + (lattitude * lattitude);
					double distance = (Math.sqrt(total));
					System.out.println("Listing ID #" + listingID);
					System.out.println(listingAddress + "      " + postalCode + "      " +
							availability + "      " + listingPrice + "      " + amenities);
					System.out.println("Distance: " + distance + " where Longitude=" + longitude +
							" and Latitude=" + lattitude + ".");
					System.out.println("----------------------------------------");
				}
			} else if(input.equals("POSTALCODE")) {
				System.out.println("What is the postal code you would like to search for?");
				String searchPostalCode = userInput.nextLine();
				String query = "SELECT * FROM Listings WHERE l_postalCode LIKE \"%" +
						searchPostalCode + "%\"";

				ResultSet listingResults = stmt.executeQuery(query);
				while(listingResults.next()) {
					int listingID = listingResults.getInt("l_id");
					String listingAddress = listingResults.getString("l_address");
					String postalCode = listingResults.getString("l_postalCode");
					String availability = listingResults.getString("l_availability");
					double listingPrice = listingResults.getFloat("l_price");
					String amenities = listingResults.getString("l_amenities");
					double longitude = listingResults.getFloat("l_longitude");
					double lattitude = listingResults.getFloat("l_lattitude");

					double total = (longitude * longitude) + (lattitude * lattitude);
					double distance = (Math.sqrt(total));
					System.out.println("Listing ID #" + listingID);
					System.out.println(listingAddress + "      " + postalCode + "      " +
							availability + "      " + listingPrice + "      " + amenities);
					System.out.println("Distance: " + distance + " where Longitude=" + longitude +
							" and Latitude=" + lattitude + ".");
					System.out.println("----------------------------------------");
				}
			} else if(input.equals("ADDRESS")) {
				System.out.println("What is the address you would like to search for?");
				String searchAddress = userInput.nextLine();
				String query = "SELECT * FROM Listings WHERE l_address LIKE \"%" +
						searchAddress + "%\"";

				ResultSet listingResults = stmt.executeQuery(query);
				while(listingResults.next()) {
					int listingID = listingResults.getInt("l_id");
					String listingAddress = listingResults.getString("l_address");
					String postalCode = listingResults.getString("l_postalCode");
					String availability = listingResults.getString("l_availability");
					double listingPrice = listingResults.getFloat("l_price");
					String amenities = listingResults.getString("l_amenities");
					double longitude = listingResults.getFloat("l_longitude");
					double lattitude = listingResults.getFloat("l_lattitude");

					double total = (longitude * longitude) + (lattitude * lattitude);
					double distance = (Math.sqrt(total));
					System.out.println("Listing ID #" + listingID);
					System.out.println(listingAddress + "      " + postalCode + "      " +
							availability + "      " + listingPrice + "      " + amenities);
					System.out.println("Distance: %.4f%n" + distance + " where Longitude=" + longitude +
							" and Latitude=" + lattitude + ".");
					System.out.println("----------------------------------------");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	public void descendingListings(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Listings ORDER BY l_price DESC";

			ResultSet listingResults = stmt.executeQuery(query);
			while(listingResults.next()) {
				int listingID = listingResults.getInt("l_id");
				String listingAddress = listingResults.getString("l_address");
				String postalCode = listingResults.getString("l_postalCode");
				String availability = listingResults.getString("l_availability");
				double listingPrice = listingResults.getFloat("l_price");
				String amenities = listingResults.getString("l_amenities");
				double longitude = listingResults.getFloat("l_longitude");
				double lattitude = listingResults.getFloat("l_lattitude");

				double total = (longitude * longitude) + (lattitude * lattitude);
				double distance = (Math.sqrt(total));
				System.out.println("Listing ID #" + listingID);
				System.out.println(listingAddress + "      " + postalCode + "      " +
						availability + "      " + listingPrice + "      " + amenities);
				System.out.println("Distance: %.4f%n" + distance + " where Longitude=" + longitude +
						" and Latitude=" + lattitude + ".");
				System.out.println("----------------------------------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
