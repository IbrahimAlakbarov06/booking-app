package org.booking.dao;

import org.booking.entity.Booking;
import org.booking.entity.Passenger;
import org.booking.exception.FlightBookingException;
import org.booking.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingDaoImpl implements BookingDao {
    private List<Booking> bookings;
    private String filePath;

    public BookingDaoImpl(String filePath) {
        this.filePath = filePath;
        this.bookings = new ArrayList<>();
        loadBookings();
    }

    public void loadBookings() {
        try {
            this.bookings = FileUtil.loadFromFile(filePath);
            if (this.bookings == null) {
                this.bookings = new ArrayList<>();
            }
        } catch (Exception e) {
            throw new FlightBookingException("Error loading bookings from file: " + e.getMessage());
        }
    }

    public void saveBookings() {
        try {
            FileUtil.saveToFile(filePath, bookings);
        } catch (Exception e) {
            throw new FlightBookingException("Error saving bookings to file: " + e.getMessage());
        }
    }
    @Override
    public List<Booking> getAllBookings() {
        return List.of();
    }

    @Override
    public Booking getBookingById(long id) {
        return bookings.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst()
                .orElseThrow(()-> new FlightBookingException("Booking with id " + id + " not found"));
    }

    @Override
    public List<Booking> getBookingsByPassengerName(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;

        return bookings.stream()
                .filter(booking -> booking.getPassengers().stream()
                        .anyMatch(passenger -> passenger.getFullName().equals(fullName)))
                .collect(Collectors.toList());
    }

    @Override
    public void addBooking(Booking booking) {
        bookings.add(booking);
        saveBookings();
    }

    @Override
    public void cancelBooking(long id) {
        boolean removed = bookings.removeIf(booking -> booking.getId() == id);

        if (removed) {
            saveBookings();
        } else {
            throw new FlightBookingException("Booking with id " + id + " not found");
        }

    }
}
