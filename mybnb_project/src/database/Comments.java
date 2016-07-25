package database;

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Comments {
	public void renterAddComment(Connection conn, int renterID, int hosterID, int listingID) {
		try {
			Statement stmt = conn.createStatement();
			DateFormat dateFormat = new SimpleDateFormat("MM-DD-yyyy HH:mm:ss");
			Date date = new Date();
			Scanner commenting = new Scanner(System.in);
			String commentDate = dateFormat.format(date);

			System.out.println("Please place your comment here.");
			System.out.print("Comment:> ");
			String userComments = commenting.nextLine();
			String comments = "User ID " + renterID + " wrote:  " + userComments;
			String nouns = "";

			System.out.println("Would you like to add any descriptions? You CAN reply " +
					"using \"NO\" as your typed answer. Each time you type \"YES\" " +
					"the description will be added");
			System.out.println("to the comments. Note that comments cannot be deleted.");
			System.out.print("Is it a good house? ");
			String goodHouse = commenting.nextLine();
			System.out.print("Is it a bad house? ");
			String badHouse = commenting.nextLine();
			System.out.print("Was the host nice? ");
			String niceHost = commenting.nextLine();
			System.out.print("Can you call this a relaxing getaway? ");
			String relaxingGetaway = commenting.nextLine();
			System.out.print("Do you think this is the best place ever? ");
			String bestPlaceEver = commenting.nextLine();

			if(goodHouse.equals("YES")) {
				nouns = nouns + "Good House|";
			}
			if(badHouse.equals("YES")) {
				nouns = nouns + "Bad House|";
			}
			if(niceHost.equals("YES")) {
				nouns = nouns + "Nice Host|";
			}
			if(relaxingGetaway.equals("YES")) {
				nouns = nouns + "Relaxing Getaway|";
			}
			if(bestPlaceEver.equals("YES")) {
				nouns = nouns + "Best Place Ever|";
			}

			String addComment = "INSERT INTO Comments (l_id, u_renterid, " + 
					"u_hosterid, b_date, c_comment, c_commentnouns) VALUES " +
					"(\'" + listingID + "\', \'" + renterID + "\', \'" + hosterID + "\', \'" +
					commentDate + "\', \'" + comments + "\', \'" + nouns + "\');";
			stmt.executeUpdate(addComment);
			System.out.println("Successfully posted comment!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void hosterAddComment(Connection conn, int hosterID, int listingID) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT * FROM Comments WHERE l_id=" + listingID;
			DateFormat dateFormat = new SimpleDateFormat("MM-DD-yyyy HH:mm:ss");
			Date date = new Date();
			String commentDate = dateFormat.format(date);
			int recentRenterID = 0;

			ResultSet commentListings = stmt.executeQuery(query);
			if(commentListings == null) {
				System.out.println("There are no comments to display.");
				System.out.println("You cannot comment at the moment.");
			} else {
				while(commentListings.next()) {
					String olderComments = commentListings.getString("c_comment");
					recentRenterID = commentListings.getInt("u_renterid");
					System.out.println(olderComments);
				}

				Scanner hosterComment = new Scanner(System.in);
				System.out.println("Please type in a comment to reply.");
				System.out.print("Comment:> ");
				String userComment = hosterComment.nextLine();
				String comment = "Host User with ID " + hosterID + " wrote:   " +
						userComment;

				String nouns = "";
				System.out.println("Would you like to add any descriptions? You CAN reply " +
						"using \"NO\" as your typed answer. Each time you type \"YES\" " +
						"the description will be added");
				System.out.println("to the comments. Note that comments cannot be deleted.");

				System.out.print("Do you think the renter is good? ");
				String goodRenter = hosterComment.nextLine();
				System.out.print("Do you think the renter is bad? ");
				String badRenter = hosterComment.nextLine();
				System.out.print("Do you think the renter is nice? ");
				String niceRenter = hosterComment.nextLine();
				System.out.print("Do you think the renter is trustworthy? ");
				String trustworthyRenter = hosterComment.nextLine();
				System.out.print("Would you like to have the renter again? ");
				String wouldLikeToHaveRenterAgain = hosterComment.nextLine();

				if(goodRenter.equals("YES")) {
					nouns = nouns + "Good Renter|";
				}
				if(badRenter.equals("YES")) {
					nouns = nouns + "Bad Renter|";
				}
				if(niceRenter.equals("YES")) {
					nouns = nouns + "Nice Renter|";
				}
				if(trustworthyRenter.equals("YES")) {
					nouns = nouns + "Trustworthy Renter|";
				}
				if(wouldLikeToHaveRenterAgain.equals("YES")) {
					nouns = nouns + "Would Love To Have Renter Again|";
				}

				String addComment = "INSERT INTO Comments (l_id, u_renterid, " + 
						"u_hosterid, b_date, c_comment, c_commentnouns) VALUES " +
						"(\'" + listingID + "\', \'" + recentRenterID + "\', \'" + 
						hosterID + "\', \'" + commentDate + "\', \'" + comment +
						"\', \'" + nouns + "\');";
				stmt.executeUpdate(addComment);
				System.out.println("Successfully posted comment!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public void rateRenter(Connection conn) {
		
	}
}
