package com.tatianomnom.choozorro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TODO add description
 * TODO remove
 */
public class DBTest {
    public static void main(String[] a) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        try(Connection conn = DriverManager.
                getConnection("jdbc:h2:mem:testdb", "sa", "")) {

            String stmt = "CREATE TABLE PUBLIC.FOO\n" +
                    "(\n" +
                    "    id INT PRIMARY KEY NOT NULL,\n" +
                    "    name VARCHAR2  NOT NULL\n" +
                    ");";
            // add application code here
            conn.prepareStatement(stmt).execute();
        } catch (SQLException e){
            System.out.println("omfg!! " + e);
        }
    }
}
