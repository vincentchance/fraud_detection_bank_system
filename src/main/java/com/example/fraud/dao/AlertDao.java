package com.example.fraud.dao;

import com.example.fraud.model.Alert;
import com.example.fraud.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDao {

    public List<Alert> getAllAlerts() {
        String query = "SELECT * FROM alerts";
        List<Alert> alerts = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                alerts.add(new Alert(
                    resultSet.getInt("alert_id"),
                    resultSet.getString("transaction_id"),
                    resultSet.getDouble("risk_score"),
                    resultSet.getString("message"),
                    resultSet.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alerts;
    }
	
	public void ensureTableExist(){
		String query = "CREATE TABLE IF NOT EXISTS alerts (" +
					   "alert_id INT AUTO_INCREMENT PRIMARY KEY, " +
					   "transaction_id VARCHAR(255) NOT NULL, " +
					   "risk_score DOUBLE NOT NULL, " +
					   "message TEXT NOT NULL, " +
					   "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
					   ")";
		try (Connection connection = DatabaseUtils.getConnection();
			 Statement statement = connection.createStatement()) {

			statement.execute(query);
			System.out.println("Table 'alerts' is ensured to exist.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public void addAlert(Alert alert) {
        String query = "INSERT INTO alerts (transaction_id, risk_score, message) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, alert.getTransactionId());
            preparedStatement.setDouble(2, alert.getRiskScore());
            preparedStatement.setString(3, alert.getMessage());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}