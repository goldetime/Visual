package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample {
  public static void main(String[] args) {
		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists country");
			statement.executeUpdate("create table country  (id integer, tiv string, name string, area int, pop int, capital string, code string)");
			
			statement.executeUpdate("insert into country values(1, 'Asia', 'Yemen', 527970, 1801257, 'Sanaa', 'ye')");
			statement.executeUpdate("insert into country values(2, 'Asia', 'South Korea', 98480, 48324, 'Seoul', 'kr')");
			statement.executeUpdate("insert into country values(3, 'Asia', 'Lebanon', 10400, 3677780, 'Beirut', 'lb')");
				
			ResultSet rs = statement.executeQuery("select * from country");
			while(rs.next()) {
				System.out.println("name = " + rs.getString("name"));
				System.out.println("id = " + rs.getInt("id"));
				System.out.println("area = " + rs.getInt("area"));
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}