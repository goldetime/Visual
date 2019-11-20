package dental;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

  public DatabaseHelper() {};
  Connection connection = null;

  public void create() {
    try {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:dental.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("drop table if exists admin");
      statement.executeUpdate("create table admin  (fname string, password string)");

      statement.executeUpdate("insert into admin values('bat', 'bat')");
      statement.executeUpdate("insert into admin values('bold', 'bold')");

      ResultSet rs = statement.executeQuery("select * from admin");
      while (rs.next()) {
        System.out.println("fname = " + rs.getString("fname"));
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
  }

  public ObservableList<Admin> getCountries(String name) {
    ObservableList<Admin> sum = FXCollections.observableArrayList();
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);

      String q = "select * from admin where fname = " + "'" + name + "'";
      ResultSet rs = statement.executeQuery(q);

      while (rs.next()) {
        Admin t = new Admin();
        t.setFname(rs.getString("fname"));
        sum.add(t);
      }
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
    return sum;
  }
}