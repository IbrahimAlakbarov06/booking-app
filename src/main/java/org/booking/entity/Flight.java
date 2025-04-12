package org.booking.entity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Flight {
    private long id;
    private String departureCity;
    private String destinationCity;
    private LocalDate date;
    private LocalTime time;
    private int availableSeats;
    private int totalSeats;

    public Flight() {
    }

    public Flight(long id, String departureCity, String destinationCity, LocalDate date, LocalTime time, int availableSeats, int totalSeats) {
        this.id = id;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id && availableSeats == flight.availableSeats && totalSeats == flight.totalSeats && Objects.equals(departureCity, flight.departureCity) && Objects.equals(destinationCity, flight.destinationCity) && Objects.equals(date, flight.date) && Objects.equals(time, flight.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureCity, destinationCity, date, time, availableSeats, totalSeats);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureCity='" + departureCity + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", availableSeats=" + availableSeats +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
