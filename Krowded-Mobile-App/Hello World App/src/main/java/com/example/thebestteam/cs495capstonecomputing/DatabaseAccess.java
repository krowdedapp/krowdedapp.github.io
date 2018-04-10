package com.example.thebestteam.cs495capstonecomputing;

import java.sql.*;

/**
 * Created by Liam on 30/03/18.
 */

public class DatabaseAccess {
    public static void main(String[] args) {
            try (
                    // Step 1: Allocate a database 'Connection' object
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://krowdeddb.cvnoof9d93qc.us-east-2.rds.amazonaws.com:3306/krowded", "krowded", "krowded4pp");
                    // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

                    // Step 2: Allocate a 'Statement' object in the Connection
                    Statement stmt = conn.createStatement();
            ) {
                // Step 3: Execute a SQL SELECT query, the query result
                //  is returned in a 'ResultSet' object.
                String strSelect = "SELECT email, password, age FROM user";
                System.out.println("The SQL query is: " + strSelect); // Echo For debugging
                System.out.println();

                ResultSet rset = stmt.executeQuery(strSelect);

                // Step 4: Process the ResultSet by scrolling the cursor forward via next().
                //  For each row, retrieve the contents of the cells with getXxx(columnName).
                System.out.println("The records selected are:");
                int rowCount = 0;
                while(rset.next()) {   // Move the cursor to the next row, return false if no more row
                    String title = rset.getString("title");
                    String password = rset.getString("password");
                    int    age   = rset.getInt("age");
                    System.out.println(title + ", " + password + ", " + age);
                    ++rowCount;
                }
                System.out.println("Total number of records = " + rowCount);

            } catch(SQLException ex) {
                ex.printStackTrace();
            }
            // Step 5: Close the resources - Done automatically by try-with-resources
        }
}