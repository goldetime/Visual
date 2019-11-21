package dental;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHelper {

  public DatabaseHelper() {};
  Connection connection = null;

  public Admin login(String name) {
    Admin t = null;

    try {
      connection = DriverManager.getConnection("jdbc:sqlite:dental.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      //String q = "select * from admin where fname = " + "'" + name + "'" + " and password = " + "'" + pass + "'";
      String q = "select * from admin where fname = " + "'" + name + "'";
      ResultSet rs = statement.executeQuery(q);
      while (rs.next()) {
        t = new Admin();
        t.setFname(rs.getString("fname"));
        t.setPassword(rs.getString("password"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (connection != null)
          connection.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return t;
  }

  public boolean check(String name) {
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:dental.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      String q = "select * from admin where fname = " + "'" + name + "'";
      ResultSet rs = statement.executeQuery(q);
      while (rs.next()) {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (connection != null)
          connection.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return true;
  }

  public void sign(String name, String pass) {
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:dental.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      String q = "insert into admin values(" + "'" + name + "'" + ", " + "'" + pass + "')";
      System.out.println(q);
      statement.executeUpdate(q);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (connection != null)
          connection.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  public void create() {
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:dental.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);
      //statement.executeUpdate("drop table if exists admin");
      statement.executeUpdate("create table admin  (fname string, password string)");
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

  public ObservableList<Admin> getLists(String name) {
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