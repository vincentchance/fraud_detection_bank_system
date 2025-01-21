package com.example.fraud.dao;

import com.example.fraud.model.User;
import com.example.fraud.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
	public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection connection = DatabaseUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                users.add(new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("account_id"),
                    resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
	
	public void ensureTableExist() {
		String query = "CREATE TABLE IF NOT EXISTS users (" +
					   "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
					   "name VARCHAR(255) NOT NULL, " +
					   "account_id VARCHAR(255) UNIQUE NOT NULL, " +
					   "email VARCHAR(255) UNIQUE NOT NULL, " +
					   "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
					   ")";
		try (Connection connection = DatabaseUtils.getConnection();
			 Statement statement = connection.createStatement()) {

			statement.execute(query);
			System.out.println("Table 'users' is ensured to exist.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}