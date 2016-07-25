package database;

import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Scanner;

public class Renters extends User {
	String isHost = "No";
	String isRenter = "Yes";
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
	
	public void addRenter(Connection conn){
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
		float longitude = newUserInput.nextFloat();
		System.out.print("The lattitude you are currently located on: ");
		float lattitude = newUserInput.nextFloat();

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

	public void rentPlace(Connection conn) {
		try {
			int listingID;
			DateFormat dateFormat = new SimpleDateFormat("MM-DD-yyyy HH:mm:ss");
			Date date = new Date();

			System.out.println("------------------------------------");
			System.out.println("Which places would you like to rent?");
			System.out.println("Please ONLY enter in the listing ID value.");

			Scanner idInput = new Scanner(System.in);
			System.out.print("Enter in ID value: ");
			int inputID = idInput.nextInt();

			Statement stmt = conn.createStatement();
			String findPlace = "SELECT * FROM Listings WHERE l_id=" + inputID;
			ResultSet place = stmt.executeQuery(findPlace);

			while(place.next()) {
				if(place.getString("l_availability").equals("AVAILABLE")) {
					listingID = place.getInt("l_id");
					String address = place.getString("l_address");
					String bookDate = dateFormat.format(date);
					String addRentedPlace = "INSERT INTO Bookings " +
							"(u_id, l_id, b_date) VALUES (\'" + getUserID() + "\', \'" +
							listingID + "\', \'" + bookDate + "\');";
					String addRentHistory = "INSERT INTO BookingsHistory " +
							"(u_id, l_id, b_date, l_address) VALUES (\'" + getUserID() + "\', \'" +
							listingID + "\', \'" + bookDate + "\', \'" + address + "\');";
					String updateAvailability = "UPDATE Listings SET " + 
							"l_availability=\"NOT AVAILABLE\" WHERE l_id=" + listingID;
					stmt.executeUpdate(updateAvailability);
					stmt.executeUpdate(addRentedPlace);

					String logMessage = "User with ID " + getUserID() +
							"booked place listing ID " + listingID + ".";
					CompleteHistory historyLog = new CompleteHistory();
					historyLog.addLog(conn, getUserID(), logMessage);
					System.out.println("Added new rented place!");

					RentHistory addRent = new RentHistory();
					addRent.addToHistory(conn, addRentHistory);
				} else {
					System.out.println("This booking cannot be completed since it is not " +
							"available.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public void cancelRent(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			Scanner inputID = new Scanner(System.in);
			int listingID = 0;

			String query = "SELECT * FROM Bookings WHERE u_id=" + getUserID();
			ResultSet bookings = stmt.executeQuery(query);

			while(bookings.next()) {
				int bookingID = bookings.getInt("b_id");
				listingID = bookings.getInt("l_id");
				String date = bookings.getString("b_date");

				System.out.println("You have booking ID #" + bookingID + " in which you " +
						"booked listing ID #" + listingID + " for " + date);
			}
			System.out.println("Which booking ID do you wish to cancel?");
			int id = inputID.nextInt();
			String removeBooking = "DELETE FROM Bookings WHERE b_id=" + id;
			stmt.executeUpdate(removeBooking);

			String updateAvailability = "UPDATE Listings SET " + 
					"l_availability=\"AVAILABLE\" WHERE l_id=" + listingID;
			stmt.executeUpdate(updateAvailability);

			String logMessage = "User with ID " + getUserID() +
					"cancelled booking with listing ID " + listingID + ".";
			CompleteHistory historyLog = new CompleteHistory();
			historyLog.addLog(conn, getUserID(), logMessage);
			System.out.println("Cancelled rented place!");
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
