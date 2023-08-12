package com.example.demo.utils;
import com.example.demo.model.Balance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBBalanceConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/urubu_pix";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private Connection connection;
    public DBBalanceConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException ignored) {}
    }
    public List<Balance> getBalance(int Id, String username) throws SQLException {
        List<Balance> balance = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM urubu_balance WHERE id = ? AND username = ?");
            ps.setInt(1, Id);
            ps.setString(2, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                balance.add(new Balance(rs.getInt("id"), rs.getString("username"), rs.getInt("balance")));
            }

            return balance;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert ps != null;
            assert rs != null;
            ps.close();
            rs.close();
        }

        return balance;
    }
    public Boolean addBalance(int id, int value) {
        try {
            PreparedStatement selectPs = connection.prepareStatement("SELECT * FROM urubu_balance WHERE id = ?");
            selectPs.setInt(1, id);
            ResultSet rs = selectPs.executeQuery();

            if (rs.next()) {
                int currentBalance = rs.getInt("balance");
                int addBalanceCalculation = currentBalance + value;
                selectPs.close();

                PreparedStatement updatePs = connection.prepareStatement("UPDATE urubu_balance SET balance = ? WHERE id = ?");
                updatePs.setInt(1, addBalanceCalculation);
                updatePs.setInt(2, id);
                updatePs.executeUpdate();

                updatePs.close();
                return true;
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public Boolean moneyWithdraw(int id, int value) throws SQLException {
        PreparedStatement selectPs = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            selectPs = connection.prepareStatement("SELECT * FROM urubu_balance WHERE id = ?");
            selectPs.setInt(1, id);
            rs = selectPs.executeQuery();

            if (rs.next() && rs.getInt(3) >= value) {
                int withdrawBalanceCalculation = rs.getInt(3) - value;
                selectPs.close();

                ps = connection.prepareStatement("UPDATE urubu_balance SET balance = ? WHERE id = ?");
                ps.setInt(1, withdrawBalanceCalculation);
                ps.setInt(2, id);
                ps.executeUpdate();
                ps.close();

                return true;
            }

            rs.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void multipleBalance() {
        PreparedStatement ps = null;
        PreparedStatement balancePs = null;
        PreparedStatement updatePs = null;
        ResultSet rs = null;
        ResultSet balanceRs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM urubu_users");
            rs = ps.executeQuery();

            while (rs.next()) {
                balancePs = connection.prepareStatement("SELECT * FROM urubu_balance WHERE id = ?");
                balancePs.setInt(1, rs.getInt("id"));
                balanceRs = balancePs.executeQuery();

                if (balanceRs.next()) {
                    int balanceCalculation = balanceRs.getInt("balance") + calculationPercentage(rs.getString("plan"), balanceRs.getInt("balance"));

                    updatePs = connection.prepareStatement("UPDATE urubu_balance SET balance = ? WHERE id = ?");
                    updatePs.setInt(1, balanceCalculation);
                    updatePs.setInt(2, balanceRs.getInt("id"));
                    updatePs.executeUpdate();
                }

                assert updatePs != null;
                balancePs.close();
                updatePs.close();
            }

            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int calculationPercentage(String plan, int value) {
        return switch (plan) {
            case "Premium" -> (value / 100) * 25;
            case "Owner" -> (value / 100) * 30;
            default -> 0;
        };
    }
}