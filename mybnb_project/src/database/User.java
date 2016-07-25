package database;

import java.sql.*;
import java.util.Scanner;

public class User {
	int userID;
	String name;
	String userLoginName;
	String userPassword;
	String userAddress;
	String userDOB;
	String userOccupation;
	String userPhoneNum;
	String userEmail;
	String userSIN;
	String isHost;
	String isRenter;
	String userCity;
	String userProvince;
	String userCountry;
	float userLongitude;
	float userLattitude;
	int userCancellations;
	boolean isDeleted = false;
	boolean isActuallyHost = false;
	boolean isActuallyRenter = false;

	public boolean userSignIn(Connection conn) {
		System.out.println("Welcome. Please sign in using your username and password.");
		Scanner userInput = new Scanner(System.in);

		System.out.print("Username: ");
		String username = userInput.next();
		System.out.print("Password: ");
		String password = userInput.next();

		//System.out.println("Preparing a statement...");
		try {
			Statement stmt = conn.createStatement();
			String userSQL = "SELECT * FROM Users;";
			ResultSet rs = stmt.executeQuery(userSQL);

			while(rs.next()) {
				userID = rs.getInt("u_id");
				name = rs.getString("u_name");
				userAddress = rs.getString("u_address");
				userDOB = rs.getString("u_dob");
				userOccupation = rs.getString("u_occupation");
				userPhoneNum = rs.getString("u_phonenum");
				userEmail = rs.getString("u_email");
				userSIN = rs.getString("u_SIN");
				isRenter = rs.getString("u_renter");
				isHost = rs.getString("u_hoster");
				userCity = rs.getString("u_city");
				userProvince = rs.getString("u_province");
				userCountry = rs.getString("u_country");
				userLongitude = rs.getFloat("u_longitude");
				userLattitude = rs.getFloat("u_lattitude");
				userCancellations = rs.getInt("u_cancellations");

				String unameResult = rs.getString("u_username");
				userLoginName = unameResult;
				String upwdResult = rs.getString("u_pwd");
				userPassword = upwdResult;

				if(unameResult.equals(username) && upwdResult.equals(password)){
					if(isRenter.equals("Yes")) {
						isActuallyRenter = true;
					}
					
					if(isHost.equals("Yes")) {
						isActuallyHost = true;
					}

					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean host() {
		return isActuallyHost;
	}
	
	public boolean renter() {
		return isActuallyRenter;
	}

	public void addUser(Connection conn){
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
							" u_phonenum, u_email, u_SIN, u_creditcard, u_longitude, " + 
							"u_lattitude, u_city, u_province, u_country)" +
							" VALUES (\'" + name + "\', \'" + username + "\', \'" +
							password + "\', \'" + address + "\', \'" + dob + "\', \'" +
							occupation + "\', \'" + phonenum + "\', \'" +
							email + "\', \'" + sinNum + "\', \'" + creditCard + "\', \'" +
							longitude + "\', \'" + lattitude + "\', \'" + city + 
							"\', \'" + province + "\', \'" + country + "\');";
			stmt.executeUpdate(userSQL);
			System.out.println("Added user!");
		return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getAddress() {
		return userAddress;
	}
}