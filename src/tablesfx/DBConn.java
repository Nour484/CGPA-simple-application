/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tablesfx;

import java.sql.*;
import java.util.logging.*;



/**
 *
 * @author 
 */
public class DBConn {
     public static Connection DBConnection() {
        
         Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");  
             //cmd :SQLPLUS / AS SYSDBA
             // ALTER USER HR ACCOUNT UNLOCK;
            conn = DriverManager.getConnection("jdbc:oracle:thin:Tabel_sec/123@localhost:1521/XE");

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
     
        return conn;
    
}
}

