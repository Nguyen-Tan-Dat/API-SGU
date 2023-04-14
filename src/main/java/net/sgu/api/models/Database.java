package net.sgu.api.models;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

//    public static void connect() {
//        if (connection == null) {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                var host = "mysql-120008-0.cloudclusters.net";
//                var port = "10101";
//                var user = "admin";
//                var password = "d2s3MiGq            ";
//                var database = "Hoc_Phan";
//                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
//            } catch (SQLException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public static void connect() {
        if (connection != null) return;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", "Hoc_Phan"));
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", "api-382801:asia-southeast1:mysql");
        try {
            connection = new HikariDataSource(config).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clear(String table) {
        try {
            String sql = "DELETE FROM " + table;
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ignored) {
        }
    }

    public static Connection getConnect() {
        connect();
        return connection;
    }
}