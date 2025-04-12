package org.booking.dao;

import org.booking.entity.Booking;

import java.util.List;

public interface BookingDao {
    public List<Booking> getAllBookings();

    public Booking getBookingById(long id);

    public List<Booking> getBookingsByPassengerName(String firstName, String lastName);

    public void addBooking(Booking booking);

    public void cancelBooking(long id);
}
