package com.moviebookingsystem.model;

import java.time.Instant;
import java.time.LocalDateTime;

public class Booking {
    int bookingId;
    int userId;
    String movieName;
    String seatNo;
    Instant bookedOn;

    public Booking() {}

    public Booking(int userId, String movieName, String seatNo, Instant bookedOn) {
        this.userId = userId;
        this.movieName = movieName;
        this.seatNo = seatNo;
        this.bookedOn = bookedOn;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public Instant getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(Instant bookedOn) {
        this.bookedOn = bookedOn;
    }
}
