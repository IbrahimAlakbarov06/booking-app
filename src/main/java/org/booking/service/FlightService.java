package org.booking.service;

import org.booking.dao.FlightDao;
import org.booking.entity.Flight;
import org.booking.exception.FlightBookingException;

import java.time.LocalDate;
import java.util.List;

public class FlightService {
    private final FlightDao flightDao;
    private long flightIdCounter;

    public FlightService(FlightDao flightDao) {
        this.flightDao = flightDao;

        long maxId = flightDao.getAllFlights().stream()
                .mapToLong(Flight::getId)
                .max()
                .orElse(0);
        this.flightIdCounter = maxId + 1;
    }

    public List<Flight> getAllFlights() {
        return flightDao.getAllFlights();
    }

    public Flight getFlightById(long id) {
        validatePositiveId(id, "flight");
        return flightDao.getFlightById(id);
    }

    public List<Flight> getFlightsInNext24Hours() {
        return flightDao.getFlightsInNext24Hours();
    }

    public List<Flight> searchFlights(String destination, LocalDate date, int passengers) {
        validateSearchParameters(destination, date, passengers);
        return flightDao.searchFlights(destination, date, passengers);
    }

    public synchronized Flight createFlight(String departureCity, String destinationCity,
                                            LocalDate date,
                                            java.time.LocalTime time,
                                            int totalSeats) {
        validateFlightCreationParameters(departureCity, destinationCity, date, time, totalSeats);

        long flightId = flightIdCounter++;

        Flight flight = new Flight(
                flightId,
                departureCity,
                destinationCity,
                date,
                time,
                totalSeats,
                totalSeats
        );

        flightDao.addFlight(flight);
        return flight;
    }

    public void updateFlight(Flight flight) {
        validateFlight(flight);
        flightDao.updateFlight(flight);
    }

    public void removeFlight(long flightId) {
        validatePositiveId(flightId, "flight");
        flightDao.removeFlight(flightId);
    }

    public void decreaseAvailableSeats(long flightId, int seats) {
        validatePositiveId(flightId, "flight");
        if (seats <= 0) {
            throw new FlightBookingException("Number of seats must be positive");
        }

        Flight flight = flightDao.getFlightById(flightId);

        if (flight.getAvailableSeats() < seats) {
            throw new FlightBookingException("Not enough available seats on this flight");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - seats);
        flightDao.updateFlight(flight);
    }

    public void increaseAvailableSeats(long flightId, int seats) {
        validatePositiveId(flightId, "flight");
        if (seats <= 0) {
            throw new FlightBookingException("Number of seats must be positive");
        }

        Flight flight = flightDao.getFlightById(flightId);

        int newAvailableSeats = flight.getAvailableSeats() + seats;
        if (newAvailableSeats > flight.getTotalSeats()) {
            throw new FlightBookingException("Cannot exceed total seats capacity");
        }

        flight.setAvailableSeats(newAvailableSeats);
        flightDao.updateFlight(flight);
    }

    private void validatePositiveId(long id, String entityName) {
        if (id <= 0) {
            throw new FlightBookingException("Invalid " + entityName + " ID: must be positive");
        }
    }

    private void validateSearchParameters(String destination, LocalDate date, int passengers) {
        if (destination == null || destination.trim().isEmpty()) {
            throw new FlightBookingException("Destination cannot be empty");
        }

        if (date == null) {
            throw new FlightBookingException("Date cannot be null");
        }

        if (passengers <= 0) {
            throw new FlightBookingException("Number of passengers must be positive");
        }
    }

    private void validateFlightCreationParameters(String departureCity, String destinationCity,
                                                  LocalDate date, java.time.LocalTime time, int totalSeats) {
        if (departureCity == null || departureCity.trim().isEmpty()) {
            throw new FlightBookingException("Departure city cannot be empty");
        }

        if (destinationCity == null || destinationCity.trim().isEmpty()) {
            throw new FlightBookingException("Destination city cannot be empty");
        }

        if (date == null) {
            throw new FlightBookingException("Date cannot be null");
        }

        if (time == null) {
            throw new FlightBookingException("Time cannot be null");
        }

        if (totalSeats <= 0) {
            throw new FlightBookingException("Total seats must be positive");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new FlightBookingException("Flight date cannot be in the past");
        }

        if (departureCity.equalsIgnoreCase(destinationCity)) {
            throw new FlightBookingException("Departure and destination cities cannot be the same");
        }
    }

    private void validateFlight(Flight flight) {
        if (flight == null) {
            throw new FlightBookingException("Flight cannot be null");
        }

        validatePositiveId(flight.getId(), "flight");

        if (flight.getDepartureCity() == null || flight.getDepartureCity().trim().isEmpty()) {
            throw new FlightBookingException("Departure city cannot be empty");
        }

        if (flight.getDestinationCity() == null || flight.getDestinationCity().trim().isEmpty()) {
            throw new FlightBookingException("Destination city cannot be empty");
        }

        if (flight.getDate() == null) {
            throw new FlightBookingException("Date cannot be null");
        }

        if (flight.getTime() == null) {
            throw new FlightBookingException("Time cannot be null");
        }

        if (flight.getTotalSeats() <= 0) {
            throw new FlightBookingException("Total seats must be positive");
        }

        if (flight.getAvailableSeats() < 0 || flight.getAvailableSeats() > flight.getTotalSeats()) {
            throw new FlightBookingException("Available seats must be between 0 and total seats");
        }
    }
}