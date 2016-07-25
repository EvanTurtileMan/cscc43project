package database;

import java.sql.*;
import java.util.Scanner;

public class Hosters extends User {
	String isHost = "Yes";
	String isRenter = "No";
	boolean isDeleted = false;

	int userID;
	String name;
	String userLoginName;
	private String userPassword;
	String userAddress;
	private String userDOB;
	String userOccupation;
	String userPhoneNum;
	String userEmail;
	private String userSIN;
	String userCity;
	String userProvince;
	String userCountry;
	float userLongitude;
	float userLattitude;
	int userCancellations;

	public void loadData(User user) {
		userID = user.userID;
		name = user.name;
		userLoginName = user.userLoginName;
		userPassword = user.userPassword;
		userAddress = user.userAddress;
		userDOB = user.userDOB;
		userOccupation = user.userOccupation;
		userPhoneNum = user.userPhoneNum;
		userEmail = user.userEmail;
		userSIN = user.userSIN;
		userCity = user.userCity;
		userProvince = user.userProvince;
		userCountry = user.userCountry;
		userLongitude = user.userLongitude;
		userLattitude = user.userLattitude;
		userCancellations = user.userCancellations;
	}

	public int getUserID() {
		return userID;
	}

	public void addHost(Connection conn){
		System.out.println("Welcome. Please add your information.");
		Scanner newUserInput = new Scanner(System.in);

		System.out.print("Your name: ");
		String name = newUserInput.nextLine();
		System.out.print("Your desired username: ");
		String username = newUserInput.nextLine();
		System.out.print("Your desired password: ");
		String password = newUserInput.nextLine();
		System.out.print("Your address: ");
		String address = newUserInput.nextLine();
		System.out.print("Your date of birth: ");
		String dob = newUserInput.nextLine();
		System.out.print("Your current occupation: ");
		String occupation = newUserInput.nextLine();
		System.out.print("Your phone number: ");
		String phonenum = newUserInput.nextLine();
		System.out.print("Your email: ");
		String email = newUserInput.nextLine();
		System.out.print("Your Security Insurance Number: ");
		String sinNum = newUserInput.nextLine();
		System.out.print("Your credit card number: ");
		String creditCard = newUserInput.nextLine();
		System.out.print("The city you live in currently: ");
		String city = newUserInput.nextLine();
		System.out.print("The province you live in currently: ");
		String province = newUserInput.nextLine();
		System.out.print("The country you live in currently: ");
		String country = newUserInput.nextLine();
		System.out.print("The longitude you are currently located on: ");
		double longitude = newUserInput.nextFloat();
		System.out.print("The lattitude you are currently located on: ");
		double lattitude = newUserInput.nextFloat();

		try {
			Statement stmt = conn.createStatement();
			String userSQL = "INSERT INTO Users (" +
					"u_name, u_username, u_pwd," +
					"u_address, u_dob, u_occupation," +
					" u_phonenum, u_email, u_SIN, u_creditcard, u_renter, u_hoster, " + 
					"u_longitude, u_lattitude, u_city, u_province, u_country)" +
					" VALUES (\'" + name + "\', \'" + username + "\', \'" +
					password + "\', \'" + address + "\', \'" + dob + "\', \'" +
					occupation + "\', \'" + phonenum + "\', \'" +
					email + "\', \'" + sinNum + "\', \'" + creditCard + "\', \'" +
					isRenter + "\', \'" + isHost + "\', \'" + longitude + "\', \'" +
					lattitude + "\', \'" + city +  "\', \'" + province + "\', \'" + 
					country + "\');";
			stmt.executeUpdate(userSQL);
			System.out.println("Added user! Please go back to login screen!");
		return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIsBoth() {
		isRenter = "Yes";
		return;
	}

	public void addListing(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			Scanner listingInfo = new Scanner(System.in);

			System.out.println("If you wish to add a listing, " +
					"please enter the following information.");

			System.out.print("Address: ");
			String listingAddress = listingInfo.nextLine();
			System.out.print("Property Type: ");
			String propertyType = listingInfo.nextLine();
			System.out.print("Room Type: ");
			String roomType = listingInfo.nextLine();
			System.out.print("Availability (you can only put \"AVAILABLE\" or \"NOT AVAILABLE\"): ");
			String availability = listingInfo.nextLine();
			System.out.print("Date of Availability: ");
			String date = listingInfo.nextLine();
			System.out.print("Postal Code of Location: ");
			String postalCode = listingInfo.nextLine();
			System.out.print("Amenities Included: ");
			String amenities = listingInfo.nextLine();
			System.out.print("Price: ");
			float price = listingInfo.nextFloat();
			System.out.print("Number of Washrooms: ");
			int numOfWashrms = listingInfo.nextInt();
			System.out.print("Number of Bedrooms: ");
			int numOfBedrms = listingInfo.nextInt();
			System.out.print("Rating of Location (only numbers 1 to 5): ");
			int rating = listingInfo.nextInt();
			System.out.print("Longitude of Location: ");
			float longitude = listingInfo.nextFloat();
			System.out.print("Lattitude of Location: ");
			float lattitude = listingInfo.nextFloat();

			String userSQL = "INSERT INTO Listings (" +
							"l_address, l_propertyType, l_roomType, l_availability, " +
							"l_date, l_price, l_washrooms, l_bedrooms, " +
							"l_postalCode, l_rating, l_longitude, l_lattitude, " +
							"u_id, l_amenities) VALUES (\'" +
							listingAddress + "\', \'" + propertyType + "\', \'" +
							roomType + "\', \'"+availability + "\', \'" + date +
							"\', \'" + price + "\', \'" + numOfWashrms + "\', \'" +
							numOfBedrms + "\', \'" + postalCode + "\', \'" + rating +
							"\', \'" + longitude + "\', \'" + lattitude + "\', \'" +
							getUserID() + "\', \'" + amenities +
							"\');";
			CompleteHistory addToHistory = new CompleteHistory();
			addToHistory.addLog(conn, getUserID(), "Added new listing: listing for " +
					listingAddress);
			stmt.executeUpdate(userSQL);
			System.out.println("Added listing successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}

	public void updateListing(Connection conn) {
		Scanner getUpdatedInfo = new Scanner(System.in);
		try {
			Statement stmt = conn.createStatement();
			String userQuery = "SELECT * FROM Listings WHERE u_id=" + getUserID();

			ResultSet listings = stmt.executeQuery(userQuery);
			while(listings.next()) {
				int listingID = listings.getInt("l_id");
				System.out.println("Your listing ID #" + listingID + ":");
				String address = listings.getString("l_address");
				String availability = listings.getString("l_availability");
				float price = listings.getFloat("l_price");
				String amenities = listings.getString("l_amenities");

				System.out.println(address + "     " + availability + "     " +
						price + "     " + amenities);
			}

			System.out.println("Select any listing you created that you would like to update.");
			System.out.println("The fields shown here are those you are able to update.");
			System.out.println("(However, you cannot update the listing address.)");
			System.out.print("The listing you want to change: ");
			int inputID = getUpdatedInfo.nextInt();

			updateListingInfo(conn, inputID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateListingInfo(Connection conn, int listingID) {
		Scanner userInput = new Scanner(System.in);

		try {
			Statement stmt = conn.createStatement();
			System.out.print("Availability (\"AVAILABLE\" or \"NOT AVAILABLE\"): ");
			String availability = userInput.nextLine();
			System.out.print("Amenities: ");
			String amenities = userInput.nextLine();
			System.out.println("New price: ");
			float price = userInput.nextFloat();

			String updateListing = "UPDATE Listings SET l_availability=\'" + 
					availability + "\', l_price=" + price + ", l_amenities=\'" +
					amenities + "\' WHERE l_id=" + listingID;
			stmt.executeUpdate(updateListing);
			System.out.println("Updated listing!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void initRemoveListing(Connection conn) {
		try {
			int listingID = 0;
			String listingAddress = "";
			String propertyType;
			String availability = "AVAILABLE";
			String rentDate;
			float listingPrice;
			int listingNumOfWashrms;
			int listingNumOfBedrms;
			String listingPostalCode;
			float longitude;
			float lattitude;
			String amenities;

			Statement stmt = conn.createStatement();
			Scanner selectListing = new Scanner(System.in);

			String collectUserListings = "SELECT * FROM Listings WHERE u_id=" + userID;
			ResultSet userListings = stmt.executeQuery(collectUserListings);

			while(userListings.next()) {
				listingID = userListings.getInt("l_id");
				listingAddress = userListings.getString("l_address");
				propertyType = userListings.getString("l_propertyType");
				availability = userListings.getString("l_availability");
				rentDate = userListings.getString("l_date");
				listingPrice = userListings.getFloat("l_price");
				listingNumOfWashrms = userListings.getInt("l_washrooms");
				listingNumOfBedrms = userListings.getInt("l_bedrooms");
				listingPostalCode = userListings.getString("l_postalCode");
				longitude = userListings.getFloat("l_longitude");
				lattitude = userListings.getFloat("l_lattitude");
				amenities = userListings.getString("l_amenities");

				System.out.println("Listing #" + listingID + ":");
				System.out.println("" + listingAddress + ", " + listingPostalCode + ", " +
						availability + ", " + rentDate);
			}

			System.out.println("Please only type in the numerical Listing ID value you would " + 
					"like to remove from your listings.");
			int listingIDRemoval = selectListing.nextInt();

			removeListing(conn, listingIDRemoval, availability, listingAddress);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void removeListing(Connection conn, int listingID, String availability,
			String listingAddress) {
		try {
			Statement stmt = conn.createStatement();
			if(availability.equals("AVAILABLE")) {
				String findUserListing = "DELETE FROM Listings WHERE l_id=" + listingID;
				stmt.executeUpdate(findUserListing);

				CompleteHistory addToHistory = new CompleteHistory();
				addToHistory.addLog(conn, getUserID(), "Removed listing: listing for " +
						listingAddress + " with Listing ID of " + listingID);
				System.out.println("Removed listing.");
			} else {
				System.out.println("This hosted location cannot be deleted due to it being booked"
						+ "or that this listing is non-existant.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void deleteUser(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			Scanner userInput = new Scanner(System.in);

			System.out.println("Please enter your username and password before continuing.");
			System.out.print("Username: ");
			String usernameInput = userInput.nextLine();
			System.out.print("Password: ");
			String passwordInput = userInput.nextLine();

			if(userLoginName.equals(usernameInput) && userPassword.equals(passwordInput)) {
				String userSQL = "DELETE FROM Users WHERE (u_username=\'" +
						userLoginName + "\' AND u_pwd=\'" +
						userPassword + "\');";
				stmt.executeUpdate(userSQL);

				String logMessage = "User " + userLoginName + " with user ID " + getUserID() +
						" has been deleted.";
				String logMessageQuery = "INSERT INTO CompleteHistory (u_id, log_reason) VALUES (\'" +
						getUserID() + "\', \'" + logMessage + "\');";
				stmt.executeUpdate(logMessageQuery);

				System.out.println("Sorry to hear that you want to leave. Deletion complete.");
				System.out.println("Please visit us again one day soon!");
				isDeleted = true;
			} else {
				System.out.println("These are the wrong credentials. You will be logged off.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}
}
