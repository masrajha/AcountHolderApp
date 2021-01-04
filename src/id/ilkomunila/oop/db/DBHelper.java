/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ilkomunila.oop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author didik
 */
public class DBHelper {

    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DB = "accholder";
    private static final String MYCONN = "jdbc:mysql://localhost/" + DB;
    private static final String SQCONN = "jdbc:sqlite:AccHolder.sqlite";

    public static Connection getConnection(String driver) throws SQLException {
        Connection conn = null;
        switch (driver) {
            case "SQLITE": {
                try {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection(SQCONN);
                    createTable(conn, driver);
                } catch (ClassNotFoundException ex) {
                    System.out.println("Librari tidak ada");
                    Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "MYSQL": {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(MYCONN, USER, PASSWORD);
                    createTable(conn, driver);
                } catch (ClassNotFoundException ex) {
                    System.out.println("Librari tidak ada");
                    Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }

        return conn;
    }

    public static void createTable(Connection conn, String driver) throws SQLException {
        String sqlCreate = "";
        switch (driver) {
            case "MYSQL": {
                sqlCreate = "CREATE TABLE IF NOT EXISTS `account_holder` ("
                        + "  `holder_id` int(10) NOT NULL,"
                        + "  `name` varchar(100) DEFAULT NULL,"
                        + "  `address` varchar(100) DEFAULT NULL,"
                        + "  PRIMARY KEY (`holder_id`)"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                        + "CREATE TABLE IF NOT EXISTS `account` ("
                        + "  `acc_number` int(10) NOT NULL,"
                        + "  `balance` double(16,2) DEFAULT NULL,"
                        + "  `holder_id` int(10) DEFAULT NULL,"
                        + "  PRIMARY KEY (`acc_number`),"
                        + "  KEY `holder_id` (`holder_id`),"
                        + "  FOREIGN KEY (`holder_id`) REFERENCES `account_holder` (`holder_id`) ON UPDATE CASCADE"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                        + "CREATE TABLE IF NOT EXISTS `corporate_holder` ("
                        + "  `holder_id` int(10) NOT NULL,"
                        + "  `contact` varchar(100) DEFAULT NULL,"
                        + "  PRIMARY KEY (`holder_id`),"
                        + "  FOREIGN KEY (`holder_id`) REFERENCES `account_holder` (`holder_id`) ON UPDATE CASCADE"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                        + "CREATE TABLE IF NOT EXISTS `individual_holder` ("
                        + "  `holder_id` int(10) NOT NULL,"
                        + "  `gender` varchar(10) DEFAULT NULL,"
                        + "  `birthdate` date DEFAULT NULL,"
                        + "  PRIMARY KEY (`holder_id`),"
                        + "  FOREIGN KEY (`holder_id`) REFERENCES `account_holder` (`holder_id`) ON UPDATE CASCADE"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
                break;
            }
            case "SQLITE": {
                sqlCreate = "CREATE TABLE IF NOT EXISTS  account_holder ("
                        + "    holder_id INT (10)      PRIMARY KEY,"
                        + "    name      VARCHAR (100),"
                        + "    address   VARCHAR (100) "
                        + ");"
                        + "CREATE TABLE IF NOT EXISTS account ("
                        + "    acc_number INT (10)       PRIMARY KEY,"
                        + "    balance    DOUBLE (16, 2),"
                        + "    holder_id  INT (10)       REFERENCES account_holder (holder_id) ON DELETE RESTRICT"
                        + "                                                                    ON UPDATE CASCADE"
                        + ");"
                        + "CREATE TABLE IF NOT EXISTS corporate_holder ("
                        + "    holder_id INT (10)      PRIMARY KEY"
                        + "                            REFERENCES account_holder (holder_id) ON DELETE RESTRICT"
                        + "                                                                  ON UPDATE CASCADE,"
                        + "    contact   VARCHAR (100) "
                        + ");"
                        + "CREATE TABLE IF NOT EXISTS  individual_holder ("
                        + "    holder_id INT (10)     PRIMARY KEY"
                        + "                           REFERENCES account_holder (holder_id) ON DELETE RESTRICT"
                        + "                                                                 ON UPDATE CASCADE,"
                        + "    gender    VARCHAR (10),"
                        + "    birthdate DATE"
                        + ");";
                break;
            }
        }
        String sqls[] = sqlCreate.split(";");
        for (String sql : sqls) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        }
    }
}
