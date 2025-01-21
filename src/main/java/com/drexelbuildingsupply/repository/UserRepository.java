package com.drexelbuildingsupply.repository;

import com.drexelbuildingsupply.DatabaseConnection;
import com.drexelbuildingsupply.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {

    // Save a new user into the database with a hashed password and verification code
    public void saveUser(User user) {
        String query = "INSERT INTO users (username, email, password, verified, verification_code) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashPassword(user.getPassword())); // Hash the password
            ps.setBoolean(4, user.isVerified());
            ps.setString(5, user.getVerificationCode());
            ps.executeUpdate();
            System.out.println("User saved successfully with hashed password and verification code!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Error: A user with this username or email already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch a user by email
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Fetch a user by username
    public Optional<User> findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Verify user credentials
    public Optional<User> verifyCredentials(String input, String password) {
        String query = "SELECT * FROM users WHERE email = ? OR username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, input);
            ps.setString(2, input);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = mapToUser(rs);
                if (checkPassword(password, user.getPassword())) { // Verify hashed password
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Update a user's verified status by verification code
    public boolean verifyUser(String verificationCode) {
        String query = "UPDATE users SET verified = true WHERE verification_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, verificationCode);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hash a password using BCrypt
    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    // Check if a password matches a hashed password
    private boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    // Map a ResultSet row to a User object
    private User mapToUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getLong("id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getBoolean("verified"),
            rs.getString("verification_code") // Include verification code
        );
    }
}
