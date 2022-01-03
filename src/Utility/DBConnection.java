/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *DB Connection. Allows connection to WGU Database. 
 * @author Koala
 */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc"; 
    private static final String vendorName = ":mysql:";
    private static final String serverName = "//wgudb.ucertify.com/WJ05CUj";
    public static Statement stmt = null; //added for testing delete if fails
    
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + serverName;
    
    //Driver and Connection Interface reference 
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    
    private static final String username = "U05CUj"; //username
    private static final String password = "53688462493"; //password
    
    /**
     *Connection Method. 
     * @return
     */
    public static Connection ConnectionObject() 
    {
        return conn;
    }

    /**
     *Start Connection Method. Starts the connection for the Database. 
     * @return
     */
    public static Connection startConnection() {
        if (conn != null) {
            return conn; 
        }
        try{
           Class.forName(MYSQLJDBCDriver);
           conn = DriverManager.getConnection(jdbcURL, username, password);
           System.out.println("Connection successful");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
             e.printStackTrace();
        }
        return conn;
    }
    
    /**
     *Close Connection Method. Closes the Database Connection. 
     */
    public static void closeConnection() {
        try{
        conn.close();
        System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
      
    }

    
}
