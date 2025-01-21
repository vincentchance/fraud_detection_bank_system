package com.example.fraud.dao;

import com.example.fraud.model.Transaction;
import com.example.fraud.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
	
	//mendapatkan semua transaksi
	public List<Transaction> getAllTransactions(){
		String query = "SELECT * FROM transactions";
		List<Transaction> transactions = new ArrayList<>();
		
		 try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                transactions.add(new Transaction(
                    resultSet.getString("transaction_id"),
                    resultSet.getString("account_id"),
                    resultSet.getDouble("amount"),
                    resultSet.getTimestamp("transaction_date"),
                    resultSet.getString("location"),
                    resultSet.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
	}
	
	// Mendapatkan transaksi berdasarkan ID
    public Transaction getTransactionById(String transactionId) {
        String query = "SELECT * FROM transactions WHERE transaction_id = ?";
        Transaction transaction = null;

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, transactionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    transaction = new Transaction(
                        resultSet.getString("transaction_id"),
                        resultSet.getString("account_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("transaction_date"),
                        resultSet.getString("location"),
                        resultSet.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    // Menambahkan transaksi baru
    public void addTransaction(Transaction transaction) {
        String query = "INSERT INTO transactions (transaction_id, account_id, amount, transaction_date, location, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				 
			preparedStatement.setString(1, transaction.getTransactionId());
            preparedStatement.setString(2, transaction.getAccountId());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, new Timestamp(transaction.getTransactionDate().getTime()));
            preparedStatement.setString(5, transaction.getLocation());
            preparedStatement.setString(6, transaction.getStatus());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Memperbarui status transaksi
    public void updateTransactionStatus(String transactionId, String status) {
        String query = "UPDATE transactions SET status = ? WHERE transaction_id = ?";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status);
            preparedStatement.setString(2, transactionId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mendapatkan transaksi dengan jumlah melebihi batas tertentu (untuk deteksi fraud)
    public List<Transaction> findTransactionsAboveThreshold(double threshold) {
        String query = "SELECT * FROM transactions WHERE amount > ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, threshold);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(new Transaction(
                        resultSet.getString("transaction_id"),
                        resultSet.getString("account_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("transaction_date"),
                        resultSet.getString("location"),
                        resultSet.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
	
	public void ensureTableExist() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS transactions (
                    transaction_id VARCHAR(255) PRIMARY KEY,
                    account_id VARCHAR(255) NOT NULL,
                    amount DOUBLE NOT NULL,
                    transaction_date DATETIME NOT NULL,
                    location VARCHAR(255),
                    status VARCHAR(50) DEFAULT 'normal'
                );
                """;

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Checked and ensured 'transactions' table exists.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error ensuring 'transactions' table exists: " + e.getMessage());
        }
    }

    // Menandai transaksi sebagai fraud
    public void flagTransaction(String transactionId) {
        updateTransactionStatus(transactionId, "flagged");
    }
}
