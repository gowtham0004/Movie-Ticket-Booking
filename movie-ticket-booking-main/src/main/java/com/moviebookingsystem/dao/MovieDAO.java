package com.moviebookingsystem.dao;

import com.moviebookingsystem.controller.DB;
import com.moviebookingsystem.model.Movie;

import java.sql.*;
import java.util.ArrayList;

public class MovieDAO {
    public static ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
        try (Statement st = DB.getConnection().createStatement()) {
            String query = "SELECT * FROM Movies";
            ResultSet res = st.executeQuery(query);
            while (res.next()) {
                int movieId = res.getInt("movie_id");
                String movieName = res.getString("movie_name");
                int ticketPrice =  res.getInt("ticket_price");
                String posterPath = res.getString("poster");
                int availableSeats = res.getInt("available_seats");
                movies.add(new Movie(movieId, movieName, ticketPrice, posterPath,availableSeats));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movies;
    }

    public static Movie getMovieById(int movieId) {
        Movie movie = null;

        String sql = "SELECT movie_id, movie_name, ticket_price, poster, available_seats FROM Movies WHERE movie_id = ?";

        try (PreparedStatement ps = DB.getConnection().prepareStatement(sql)) {

            ps.setInt(1, movieId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                movie = new Movie();
                movie.setMovieId(rs.getInt("movie_id"));
                movie.setMovieName(rs.getString("movie_name"));
                movie.setTicketPrice(rs.getInt("ticket_price"));
                movie.setPoster(rs.getString("poster"));
                movie.setAvailableSeats(rs.getInt("available_seats"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    public static void reduceAvailability(int movie_id, int seatCount) {
        String query = "UPDATE Movies SET available_seats = available_seats - ? WHERE movie_id = ?";
        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, seatCount);
            ps.setInt(2, movie_id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addMovieWithSeats(String name, int price, String posterPath, int totalSeats) {
        final int SEATS_PER_ROW = 5; // 5 seats per row
        String insertMovieSql = "INSERT INTO Movies(movie_name, ticket_price, poster, available_seats) VALUES (?, ?, ?, ?)";
        String insertSeatSql  = "INSERT INTO Seats(movie_id, seat_no) VALUES (?, ?)";

        Connection con = null;
        try {
            con = DB.getConnection();
            con.setAutoCommit(false);

            int movieId;
            try (PreparedStatement psMovie = con.prepareStatement(insertMovieSql, Statement.RETURN_GENERATED_KEYS)) {
                psMovie.setString(1, name);
                psMovie.setInt(2, price);
                psMovie.setString(3, posterPath);
                psMovie.setInt(4, totalSeats);
                if (psMovie.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
                try (ResultSet rs = psMovie.getGeneratedKeys()) {
                    if (rs.next()) movieId = rs.getInt(1);
                    else {
                        con.rollback();
                        return false;
                    }
                }
            }

            try (PreparedStatement psSeat = con.prepareStatement(insertSeatSql)) {
                for (int i = 0; i < totalSeats; i++) {
                    char rowChar = (char) ('A' + (i / SEATS_PER_ROW));
                    int seatNum = (i % SEATS_PER_ROW) + 1;
                    String seatNo = "" + rowChar + seatNum;
                    psSeat.setInt(1, movieId);
                    psSeat.setString(2, seatNo);
                    psSeat.addBatch();
                }
                psSeat.executeBatch();
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (Exception ignored) {}
        }
    }

    public static boolean refreshSeats(int movieId) {
        String resetSeatsSql = "UPDATE Seats SET is_booked = 0, booked_by = NULL WHERE movie_id = ?";
        String countSeatsSql = "SELECT COUNT(*) FROM Seats WHERE movie_id = ?";
        String updateMovieSql = "UPDATE Movies SET available_seats = ? WHERE movie_id = ?";

        try (Connection con = DB.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(resetSeatsSql)) {
                ps1.setInt(1, movieId);
                ps1.executeUpdate();
            }

            int totalSeats = 0;
            try (PreparedStatement ps2 = con.prepareStatement(countSeatsSql)) {
                ps2.setInt(1, movieId);
                try (ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) totalSeats = rs.getInt(1);
                }
            }

            try (PreparedStatement ps3 = con.prepareStatement(updateMovieSql)) {
                ps3.setInt(1, totalSeats);
                ps3.setInt(2, movieId);
                ps3.executeUpdate();
            }
            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteMovie(int movieId, String realPosterPath) {
        String deleteSeatsSql = "DELETE FROM Seats WHERE movie_id = ?";
        String deleteMovieSql = "DELETE FROM Movies WHERE movie_id = ?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(deleteSeatsSql)) {
                ps.setInt(1, movieId);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(deleteMovieSql)) {
                ps.setInt(1, movieId);
                ps.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Delete poster file
        if (realPosterPath != null) {
            try {
                java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(realPosterPath));
//                System.out.println("Deleted poster: " + realPosterPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String getPosterPath(int movieId) {
        String sql = "SELECT poster FROM Movies WHERE movie_id = ?";
        try (Connection conn = DB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("poster");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
