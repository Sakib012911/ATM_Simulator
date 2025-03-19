package atm.machine;

import java.sql.*;

public class Conn {
  public  Connection connection;
  public  Statement  statement;
    public Conn(){
        try {
             connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/atmmachine","root","");
             statement= connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

