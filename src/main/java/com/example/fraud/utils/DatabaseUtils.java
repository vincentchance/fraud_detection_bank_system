package com.example.fraud.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {
    private static final String DB_URL = "jdbc:mysql://localhost:3310/fraud_detection";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Membuka koneksi ke database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Menjalankan query INSERT, UPDATE, DELETE menggunakan PreparedStatement untuk keamanan
    public static int executeUpdate(String query, Object... params) throws SQLException {
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set parameter untuk PreparedStatement
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        }
    }

    // Menutup koneksi dan resource lainnya menggunakan try-with-resources
    public static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}