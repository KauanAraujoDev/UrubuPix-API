package com.example.demo.utils;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUserConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/urubu_pix";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    public DBUserConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException ignored) {}
    }
    public Boolean createUser(String username, int age, String plan) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PreparedStatement ps = null;
        PreparedStatement balancePs = null;
        try {
            ps = connection.prepareStatement("INSERT INTO urubu_users (username, age, plan, timestamp) VALUES (?, ?, ?, ?)");
            balancePs = connection.prepareStatement("INSERT INTO urubu_balance (username) VALUES (?)");
            balancePs.setString(1, username);
            ps.setString(1, username);
            ps.setInt(2, age);
            ps.setString(3, plan);
            ps.setString(4, timestamp.toString());
            ps.executeUpdate();
            balancePs.executeUpdate();

            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public Boolean deleteUser(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM urubu_users WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();

            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public List<User> Users() throws SQLException {
        List<User> users = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM urubu_users");

            rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getInt("age"), rs.getString("plan"), rs.getString("timestamp")));
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert ps != null;
            assert rs != null;
            ps.close();
            rs.close();
        }

        return users;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM urubu_users");

            while (resultSet.next()) {
                sb.append(resultSet.getInt(1))
                        .append(" ")
                        .append(resultSet.getString(2))
                        .append(" ")
                        .append(resultSet.getString(3))
                        .append(" ")
                        .append(resultSet.getString(4))
                        .append(" ")
                        .append(resultSet.getString(5))
                        .append("\n");
            }

            connection.close();
        } catch (Exception e) {
            sb.append(e);
        }
        return sb.toString();
    }
}
