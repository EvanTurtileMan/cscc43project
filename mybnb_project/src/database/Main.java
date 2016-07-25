package database;

import java.sql.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		getConnection();
	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/mybnb";
			final String USERNAME = "gamingturtile";
			final String PASSWORD = "password";
			Class.forName(driver);

			Connection mybnbConnect = DriverManager.getConnection(url, USERNAME, PASSWORD);
			//System.out.println("Connected user to database myBnB. Welcome.");

			User user = new User();
			//int loginAttempts = 0;
			//boolean hasLoggedIn = false;

			/*while(loginAttempts < 3 && !hasLoggedIn){
				hasLoggedIn = user.userSignIn(mybnbConnect);
				if(hasLoggedIn == false) {
					System.out.println("Username or Password are incorrect. Input again.");
				}
				loginAttempts++;
			}*/

			System.out.println("Would you like to \"Sign In\" or \"Add User\"?");
			System.out.print("Input: ");
			Scanner userInput = new Scanner(System.in);
			String userChoice = userInput.nextLine();

			if(userChoice.equals("Sign In")) {
				boolean hasLoggedIn = user.userSignIn(mybnbConnect);
				System.out.println(user.host());
				if(hasLoggedIn) {
					System.out.println("Welcome user.");
					MainHub userScreen = new MainHub();

					if(user.host()) {
						Hosters hoster = new Hosters();
						hoster.loadData(user);
						userScreen.hostOptions(mybnbConnect, hoster);
					} else if(user.renter()) {
						Renters renter = new Renters();
						renter.loadData(user);
						userScreen.renterOptions(mybnbConnect, renter);
					}

					//userScreen.mainScreen(mybnbConnect, user);
				} else {
					System.out.println("You are locked out. Please try again.");
				}
			} else if(userChoice.equals("Add User")) {
				System.out.println("Will you be a \"Renter\", a \"Hoster\", or \"Both\"?");
				System.out.print("Your input (it has to be exact): ");
				String isRenterHoster = userInput.nextLine();
				
				if(isRenterHoster.equals("Renter")) {
					Renters newRenter = new Renters();
					newRenter.addRenter(mybnbConnect);
				} else if(isRenterHoster.equals("Hoster")) {
					Hosters newHost = new Hosters();
					newHost.addHost(mybnbConnect);
				} else if(isRenterHoster.equals("Both")) {
					Hosters newHost = new Hosters();
					newHost.setIsBoth();
					newHost.addHost(mybnbConnect);
				} else {
					System.out.println("That was an invalid answer. Please try again later.");
				}
			} else {
				System.out.println("Wrong input. Closing application...");
			}

			System.out.println("See you next time!");
			mybnbConnect.close();
			return mybnbConnect;
		} catch(SQLException e){System.out.println(e);}

		return null;
	}

}
