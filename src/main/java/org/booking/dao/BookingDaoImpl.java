package org.booking.dao;

import org.booking.entity.Booking;
import org.booking.exception.FlightBookingException;
import org.booking.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingDaoImpl implements BookingDao {
    private List<Booking> bookings;
    private final String filePath;

    public BookingDaoImpl(String filePath) {
        this.filePath = filePath;
        this.bookings = new ArrayList<>();
        loadBookings();
    }

    public void loadBookings() {
        try {
            List<Booking> loadedBookings = FileUtil.loadFromFile(filePath);
            this.bookings = loadedBookings != null ? loadedBookings : new ArrayList<>();
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
        return new ArrayList<>(bookings);
    }

    @Override
    public Booking getBookingById(long id) {
        Optional<Booking> bookingOptional = bookings.stream()
                .filter(booking -> booking.getId() == id)
                .findFirst();

        if (bookingOptional.isPresent()) {
            return bookingOptional.get();
        } else {
            throw new FlightBookingException("Booking with id " + id + " not found");
        }
    }

    @Override
    public List<Booking> getBookingsByPassengerName(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;

        return bookings.stream()
                .filter(booking -> booking.getPassengers().stream()
                        .anyMatch(passenger -> passenger.getFullName().equalsIgnoreCase(fullName)))
                .collect(Collectors.toList());
    }

    @Override
    public void addBooking(Booking booking) {
        bookings.add(booking);
        saveBookings();
    }

    @Override
    public void cancelBooking(long id) {
        int initialSize = bookings.size();
        bookings.removeIf(booking -> booking.getId() == id);

        if (bookings.size() == initialSize) {
            throw new FlightBookingException("Booking with id " + id + " not found for cancellation");
        }

        saveBookings();
    }
}