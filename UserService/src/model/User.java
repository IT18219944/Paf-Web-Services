package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/pafgadget?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertUser(String name, String address, String email, String date, String gender, String pno) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into user1(`uID`,`uName`,`uAddress`,`uEmail`,`uDate`,`uGender`,`pno`)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, gender);
			preparedStmt.setString(7, pno);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readUser() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the  table to be displayed
			output = "<table border=\"1\"><tr><th>User ID</th><th>User Name</th><th>Address</th><th>Email</th><th>Date</th><th>Gender</th><th>Phone No</th></tr>";
			String query = "select * from user1";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String uID = Integer.toString(rs.getInt("uID"));
				String uName = rs.getString("uName");
				String uAddress = rs.getString("uAddress");
				String uEmail = rs.getString("uEmail");
				String uDate = rs.getString("uDate");
				String uGender = rs.getString("uGender");
				String pno = rs.getString("pno");

				// Add into the  table
				output += "<tr><td>" + uID + "</td>";
				output += "<td>" + uName + "</td>";
				output += "<td>" + uAddress + "</td>";
				output += "<td>" + uEmail + "</td>";
				output += "<td>" + uDate + "</td>";
				output += "<td>" + uGender + "</td>";
				output += "<td>" + pno + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateUser(String ID, String name, String address, String email, String date, String gender,
			String pno) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE user1 SET uName=?,uAddress=?,uEmail=?,uDate=?,uGender=?,pno=?" + "WHERE uID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, date);
			preparedStmt.setString(5, gender);
			preparedStmt.setString(6, pno);
			preparedStmt.setInt(7, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the user.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteUser(String uID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from user1 where uID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(uID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the user.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
