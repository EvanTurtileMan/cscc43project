package database;

import java.sql.*;
import java.util.Scanner;

public class MainHub {
	/*public void mainScreen(Connection conn, User user) {
		Scanner userCmd = new Scanner(System.in);
		String userCommand = "";
		
		try {
			Statement stmt = conn.createStatement();
			String statement = "";

			while(!userCommand.equals("QUIT") && !user.isDeleted){
				System.out.println("You can either type in \"QUIT\" " + 
							"to logout and exit the system.");
				System.out.println("Or, you can type in \"OPTIONS\" " + 
							"to load your mybnb options.");

				userCommand = userCmd.nextLine();

				if(userCommand.equals("OPTIONS")) {
					moreOptions(conn, user);
				} else if(userCommand.equals("QUIT")) {
					System.out.println("You are now logging out. Good bye.");
				} else {
					System.out.println("That is not a valid command.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void moreOptions(Connection conn, User user) {
		System.out.println("You have a few options.");
		System.out.println("Please only TYPE IN THE NUMERICAL VALUE of the option " +
					"you wish to explore.");
		Hosters hoster = new Hosters();
		hoster.loadData(user);
		Renters renter = new Renters();
		renter.loadData(user);

		if(user.isHost.equals("Yes") && user.isRenter.equals("No")) {
			hostOptions(conn, hoster);
		} else if(user.isHost.equals("No") && user.isRenter.equals("Yes")) {
			renterOptions(conn, renter);
		} else if(user.isHost.equals("Yes") && user.isRenter.equals("Yes")) {
			hostAndRenterOptions(conn, hoster, renter);
		}

		return;
	}*/

	public void hostOptions(Connection conn, Hosters user) {
		Scanner userInputCmd = new Scanner(System.in);
		int userInput = 0;

		while((userInput != 7) && (!user.isDeleted)) {
			System.out.println("1) Comment on a renter.");
			System.out.println("2) Rate a renter.");
			System.out.println("3) Host a new location.");
			System.out.println("4) Delete hosted location.");
			System.out.println("5) Show all your uploaded listings.");
			System.out.println("6) Update listing information.");
			System.out.println("7) Delete account.");
			System.out.println("8) Leave application.");

			System.out.print("Your command: ");
			userInput = userInputCmd.nextInt();
			Comments comment = new Comments();

			if(userInput == 1) {
				Statement stmt;
				try {
					stmt = conn.createStatement();

					String query = "SELECT * FROM Listings WHERE u_id=" + user.getUserID();
					ResultSet uploadedListings = stmt.executeQuery(query);

					while(uploadedListings.next()) {
						int listingID = uploadedListings.getInt("l_id");
						String listingAddress = uploadedListings.getString("l_address");
						String postalCode = uploadedListings.getString("l_postalCode");
						String availability = uploadedListings.getString("l_availability");
						float listingPrice = uploadedListings.getFloat("l_price");
						String amenities = uploadedListings.getString("l_amenities");

						System.out.println("Listing ID #" + listingID);
						System.out.println(listingAddress + "      " + postalCode + "      " +
								availability + "      " + listingPrice + "      " + amenities);
						System.out.println("----------------------------------------");
					}
					Scanner inputID = new Scanner(System.in);
					System.out.println("Which listing ID (numerically) would you " + 
							"like to comment on?");
					System.out.print("Your input: ");
					int inputListingID = inputID.nextInt();
					comment.hosterAddComment(conn, user.getUserID(), inputListingID);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(userInput == 2) {
				comment.rateRenter(conn);
			} else if(userInput == 3) {
				user.addListing(conn);
			} else if(userInput == 4) {
				user.initRemoveListing(conn);
			} else if(userInput == 5) {
				try {
					Statement stmt = conn.createStatement();

					String query = "SELECT * FROM Listings WHERE u_id=" + user.getUserID();
					ResultSet uploadedListings = stmt.executeQuery(query);

					while(uploadedListings.next()) {
						int listingID = uploadedListings.getInt("l_id");
						String listingAddress = uploadedListings.getString("l_address");
						String postalCode = uploadedListings.getString("l_postalCode");
						String availability = uploadedListings.getString("l_availability");
						float listingPrice = uploadedListings.getFloat("l_price");
						String amenities = uploadedListings.getString("l_amenities");

						System.out.println("Listing ID #" + listingID);
						System.out.println(listingAddress + "      " + postalCode + "      " +
								availability + "      " + listingPrice + "      " + amenities);
						System.out.println("----------------------------------------");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(userInput == 6) {
				user.updateListing(conn);
			} else if(userInput == 7) {
				user.deleteUser(conn);
			} else if(userInput == 8) {
				return;
			}	
		}
	}

	public void renterOptions(Connection conn, Renters user) {
		Scanner userInputCmd = new Scanner(System.in);
		int userInput = 0;

		while((userInput != 10) && (!user.isDeleted)) {
			System.out.println("1) Comment on place rented.");
			System.out.println("2) Rent out a place.");
			System.out.println("3) Cancel booking for rented location.");
			System.out.println("4) Update billing information.");
			System.out.println("5) View booking history.");
			System.out.println("6) View all listings.");
			System.out.println("7) Search through all listings.");
			System.out.println("8) View all listings in descending order by price.");
			System.out.println("9) Delete account.");
			System.out.println("10) Leave application.");

			System.out.print("Your command: ");
			userInput = userInputCmd.nextInt();
			Comments comment = new Comments();

			if(userInput == 1) {
				System.out.println("Which listing would you like to comment on?");
				System.out.println("ONLY type in the listing ID value.");
				RentHistory history = new RentHistory();
				history.viewHistory(conn, user.getUserID());

				Scanner inputID = new Scanner(System.in);
				System.out.print("The listing ID you want to comment on: ");
				int listingID = inputID.nextInt();
				int hosterID = history.getHosterID(conn, listingID);

				comment.renterAddComment(conn, user.getUserID(), hosterID, listingID);
			} else if(userInput == 2) {
				Statement stmt;
				try {
					stmt = conn.createStatement();
					String showAllPlaces = "SELECT * FROM Listings";
					ResultSet listings = stmt.executeQuery(showAllPlaces);

					while(listings.next()) {
						int listingID = listings.getInt("l_id");
						String listingAddress = listings.getString("l_address");
						String postalCode = listings.getString("l_postalCode");
						String availability = listings.getString("l_availability");
						double listingPrice = listings.getFloat("l_price");
						String amenities = listings.getString("l_amenities");
						double longitude = listings.getFloat("l_longitude");
						double lattitude = listings.getFloat("l_lattitude");

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

				user.rentPlace(conn);
			} else if(userInput == 3) {
				user.cancelRent(conn);
			} else if(userInput == 4) {
				
			} else if(userInput == 5) {
				RentHistory history = new RentHistory();
				history.viewHistory(conn, user.getUserID());
			} else if(userInput == 6) {
				try {
					Statement stmt = conn.createStatement();
					String query = "SELECT * FROM Listings";
					ResultSet listings = stmt.executeQuery(query);

					while(listings.next()) {
						int listingID = listings.getInt("l_id");
						String listingAddress = listings.getString("l_address");
						String postalCode = listings.getString("l_postalCode");
						String availability = listings.getString("l_availability");
						double listingPrice = listings.getFloat("l_price");
						String amenities = listings.getString("l_amenities");
						double longitude = listings.getFloat("l_longitude");
						double lattitude = listings.getFloat("l_lattitude");

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
			} else if(userInput == 7) {
				Search querySearch = new Search();
				querySearch.searchListings(conn);
			} else if(userInput == 8) {
				Search descSearch = new Search();
				descSearch.descendingListings(conn);
			} else if(userInput == 9) {
				user.deleteUser(conn);
			} else if(userInput == 10) {
				return;
			}	
		}
	}

	/*public void hostAndRenterOptions(Connection conn, Hosters hoster, Renters renter) {
		System.out.println("1) Comment on a renter.");
		System.out.println("2) Comment on place rented.");
		System.out.println("3) Rate a renter.");
		System.out.println("4) Host a new location.");
		System.out.println("5) Delete hosted location.");
		System.out.println("6) Update prices.");
		System.out.println("7) Rent out a place.");
		System.out.println("8) Cancel booking for rented location.");
		System.out.println("9) Update billing information.");
		System.out.println("10) Delete account.");
		System.out.println("11) Leave application.");

		Scanner userInputCmd = new Scanner(System.in);

		System.out.print("Your command: ");
		int userInput = userInputCmd.nextInt();
		if(userInput == 4) {
			hoster.addListing(conn);
		} else if(userInput == 5) {
			hoster.deleteUser(conn);
		} else if(userInput == 11) {
			return;
		}
	}*/
}
