package org.booking.dao;

import org.booking.entity.Booking;

import java.util.List;

public class BookingDaoImpl implements BookingDao {
    @Override
    public List<Booking> getAllBookings() {
        return List.of();
    }

    @Override
    public Booking getBookingById(String id) {
        return null;
    }

    @Override
    public List<Booking> getBookingsByPassengerName(String firstName, String lastName) {
        return List.of();
    }

    @Override
    public void addBooking(Booking booking) {

    }

    @Override
    public void cancelBooking(String id) {

    }
}
