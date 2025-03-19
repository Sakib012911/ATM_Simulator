package atm.machine;

import java.sql.*;

public class temp {
  public  Connection connection;
  public  Statement  statement;
    public temp(){
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

