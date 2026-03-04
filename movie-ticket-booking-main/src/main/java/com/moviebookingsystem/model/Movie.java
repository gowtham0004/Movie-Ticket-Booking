package com.moviebookingsystem.model;

import java.io.InputStream;

public class Movie {
    private int movieId;
    private String movieName;
    private int ticketPrice;
    private String poster;
    private int availableSeats;

    public Movie(int movieId, String movieName, int ticketPrice, String poster, int availableSeats) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.ticketPrice = ticketPrice;
        this.poster = poster;
        this.availableSeats = availableSeats;
    }

    public Movie() {

    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
