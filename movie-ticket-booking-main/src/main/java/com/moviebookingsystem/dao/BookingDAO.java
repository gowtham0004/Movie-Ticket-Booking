package com.moviebookingsystem.dao;

import com.moviebookingsystem.controller.DB;
import com.moviebookingsystem.model.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class BookingDAO {
    public static boolean addBooking(Booking booking) {
        String sql = "INSERT INTO Bookings (user_id, movie_name, seat_no, booked_on) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = DB.getConnection().prepareStatement(sql)) {

            pst.setInt(1, booking.getUserId());
            pst.setString(2, booking.getMovieName());
            pst.setString(3, booking.getSeatNo());
            pst.setTimestamp(4, Timestamp.from(Instant.now()));

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Booking> getBookingsByUser(int userId) {
        ArrayList<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE user_id = ? ORDER BY booked_on DESC";

        try (PreparedStatement pst = DB.getConnection().prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("booking_id"));
                b.setUserId(rs.getInt("user_id"));
                b.setMovieName(rs.getString("movie_name"));
                b.setSeatNo(rs.getString("seat_no"));
                Timestamp ts = rs.getTimestamp("booked_on");
                if (ts != null) {
                    b.setBookedOn(ts.toInstant());
                }

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
