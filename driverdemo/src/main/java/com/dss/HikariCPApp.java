package com.dss;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class HikariCPApp {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.yugabyte.Driver");
        HikariDataSource ds = configureHikari("jdbc:yugabytedb://127.0.0.1:5433/yugabyte?load-balance=true");

        System.out.println("Configured the HikariDataSource");

        // Verify connection with a DDL and an INSERT statement
        Connection connection = ds.getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute("DROP TABLE IF EXISTS employee");
        stmt.execute("CREATE TABLE employee" +
                "  (id int primary key, name varchar, age int, language text)");
        System.out.println("Created table employee");

        String insertStr = "INSERT INTO employee VALUES (1, 'John', 35, 'Java')";
        stmt.execute(insertStr);
        System.out.println("Inserted a row");
        connection.close();

        System.out.println("You can now bring down nodes in steps and verify connection counts");
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        System.out.print("Press enter or return key to exit: ");
        Scanner in = new Scanner(System.in);
        in.nextLine();
        ds.close();
    }

    private static HikariDataSource configureHikari(String url) {
        String ds_yb = "com.yugabyte.ysql.YBClusterAwareDataSource";
        Properties poolProperties = new Properties();
        poolProperties.setProperty("poolName", "yugabytedb_demo");
        poolProperties.setProperty("dataSourceClassName", ds_yb);
        poolProperties.setProperty("dataSource.url", url);
        poolProperties.setProperty("dataSource.topologyKeys", "aws.us-east-2.us-east-2a,aws.us-east-2.us-east-2b,aws.us-east-2.us-east-2c");
        poolProperties.setProperty("maximumPoolSize", "12");
        poolProperties.setProperty("maxLifetime", "0"); // default
        poolProperties.setProperty("keepaliveTime", "10000");
        poolProperties.setProperty("dataSource.user", "yugabyte");

        HikariConfig hikariConfig = new HikariConfig(poolProperties);
        hikariConfig.validate();
        return new HikariDataSource(hikariConfig);
    }
}