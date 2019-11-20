package midterm;

import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class TreeViewHelper {

	public TreeViewHelper() {}
	Connection connection = null;

	public ObservableList<Country> getCountries(String name) {
		ObservableList<Country> sum = FXCollections.observableArrayList();
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			String q = "select * from country where tiv = " + "'" + name + "'";
			ResultSet rs = statement.executeQuery(q);

			while (rs.next()) {
				Country t = new Country();
				t.setCapital(rs.getString("capital"));
				t.setCode(rs.getString("code"));
				t.setPopulation(rs.getInt("pop"));
				t.setArea(rs.getInt("area"));
				t.setName(rs.getString("name"));
				sum.add(t);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return sum;
	}

	public ArrayList<TreeItem> getProducts() {
		ArrayList<TreeItem> products = new ArrayList<TreeItem>();

		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			statement.executeUpdate("drop table if exists country");
			statement.executeUpdate("create table country  (id integer, tiv string, name string, area int, pop int, capital string, code string)");

			statement.executeUpdate("insert into country values(1, 'America', 'Yemen', 527970, 1801257, 'Sanaa', 'ye')");
			statement.executeUpdate("insert into country values(2, 'Africa', 'South Korea', 98480, 48324, 'Seoul', 'kr')");
			statement.executeUpdate("insert into country values(3, 'Europe', 'Lebanon', 10400, 3677780, 'Beirut', 'lb')");
			statement.executeUpdate("insert into country values(4, 'Asia', 'Mongolia', 1500000, 3000000, 'Ulaanbaatar', 'mn')");
			statement.executeUpdate("insert into country values(4, 'Asia', 'China', 9600000, 100000000, 'Beijing', 'ch')");
			statement.executeUpdate("insert into country values(4, 'Asia', 'Korea', 474500, 14012104, 'Souel', 'kr')");
			statement.executeUpdate("insert into country values(4, 'Asia', 'Japan', 464400, 14012104, 'Tokyo', 'jp')");
			statement.executeUpdate("insert into country values(5, 'America', 'Brazil', 427970, 1331257, 'Brasilia', 'br')");

			ResultSet rs = statement.executeQuery("select id, tiv from country group by tiv order by tiv");
//			rs.getString("name")); // i.g: .getInt("id")
			while (rs.next()) {
				list.add(rs.getString("tiv"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}

		list.stream().forEach(i -> {
			products.add(new TreeItem(i));
		});
		return products;
	}
}