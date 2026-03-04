package com.moviebookingsystem.model;

import java.time.LocalDateTime;

public class Seat {
    int seatId;
    int movieId;
    String seatNo;
    boolean isBooked;
    int bookedBy;

    public Seat(int seatId, int movieId, String seatNo, boolean isBooked) {
        this.seatId = seatId;
        this.movieId = movieId;
        this.seatNo = seatNo;
        this.isBooked = isBooked;
    }

    public Seat(int seatId, int movieId, String seatNo, boolean isBooked, int bookedBy) {
        this.seatId = seatId;
        this.movieId = movieId;
        this.seatNo = seatNo;
        this.isBooked = isBooked;
        this.bookedBy = bookedBy;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }
}
