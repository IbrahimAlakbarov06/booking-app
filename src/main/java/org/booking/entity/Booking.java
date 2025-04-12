package org.booking.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long flightId;
    private List<Passenger> passengers;
    private long bookingPassengerId;

    public Booking() {
    }

    public Booking(long id, long flightId, List<Passenger> passengers, long bookingPassengerId) {
        this.id = id;
        this.flightId = flightId;
        this.passengers = passengers;
        this.bookingPassengerId = bookingPassengerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public long getBookingPassengerId() {
        return bookingPassengerId;
    }

    public void setBookingPassengerId(long bookingPassengerId) {
        this.bookingPassengerId = bookingPassengerId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id && flightId == booking.flightId && bookingPassengerId == booking.bookingPassengerId && Objects.equals(passengers, booking.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightId, passengers, bookingPassengerId);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", passengers=" + passengers +
                ", bookingPassengerId=" + bookingPassengerId +
                '}';
    }
}