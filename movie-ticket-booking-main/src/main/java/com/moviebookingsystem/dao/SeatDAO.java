package com.moviebookingsystem.dao;

import com.moviebookingsystem.controller.DB;
import com.moviebookingsystem.model.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeatDAO {
    public static ArrayList<Seat> getAvailableSeats(int movieId) {
        ArrayList<Seat> seats = new ArrayList<>();

        String query = "SELECT * FROM Seats WHERE movie_id = ?;";

        try (PreparedStatement ps = DB.getConnection().prepareStatement(query)) {
            ps.setInt(1, movieId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seats.add(new Seat(
                        rs.getInt("seat_id"),
                        rs.getInt("movie_id"),
                        rs.getString("seat_no"),
                        rs.getBoolean("is_booked")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seats;
    }

    public static boolean areSeatsAvailable(int movieId, List<String> seats) throws SQLException {
        String sql;

        String inSql = seats.stream().map(s -> "?").collect(Collectors.joining(","));
        sql = "SELECT COUNT(*) AS count FROM Seats WHERE movie_id=? AND seat_no IN (" + inSql + ") AND is_booked=1";

        PreparedStatement ps = DB.getConnection().prepareStatement(sql);
        ps.setInt(1, movieId);

        int index = 2;
        for (String seat : seats) {
            ps.setString(index++, seat);
        }

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("count") == 0;
        }
        return false;
    }

    public static int bookSeats(int movieId, List<String> seats, int userId) throws Exception {
        Connection con = DB.getConnection();
        con.setAutoCommit(false); // start transaction

        String inSql = seats.stream().map(s -> "?").collect(Collectors.joining(","));
        String sql = "UPDATE Seats SET is_booked=1, booked_by=? WHERE movie_id=? AND seat_no IN (" + inSql + ") AND is_booked=0";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, movieId);

        int index = 3;
        for (String seat : seats) {
            ps.setString(index++, seat);
        }

        int updated = ps.executeUpdate();

        if (updated == seats.size()) {
            con.commit(); // All seats booked correctly
            return updated;
        } else {
            con.rollback(); // Conflict happened
            return 0;
        }
    }

}
