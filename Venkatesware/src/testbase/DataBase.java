package testbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

	public static void main(String[] args) {
		 try (Connection conn = DriverManager.getConnection(
	                "jdbc:oracle:thin:@localhost:1521:orcl", "system", "Password123")) {

	            if (conn != null) {
	                System.out.println("Connected to the database!");
	            } else {
	                System.out.println("Failed to make connection!");
	            }

	        } catch (SQLException e) {
	            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }


	}

}
