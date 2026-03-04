package com.moviebookingsystem.dao;

import com.moviebookingsystem.controller.DB;
import com.moviebookingsystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static User validation(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email=? AND password=?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUserName(rs.getString("user_name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addUser(String name, String email, String password) {
        String sql = "INSERT INTO Users(user_name, email, password) VALUES(?,?,?)";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return false;
    }

    public static User getUserById(int userId) {
        User user = null;

        String sql = "SELECT user_id, user_name, email, password, role FROM Users WHERE user_id = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean makeAdmin(int userId) {
        String sql = "UPDATE Users SET role = 'ADMIN' WHERE user_id = ?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean makeUser(int userId) {
        String sql = "UPDATE Users SET role = 'USER' WHERE user_id = ?";
        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
