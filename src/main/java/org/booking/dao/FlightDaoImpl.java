package org.booking.dao;

import org.booking.entity.Flight;
import org.booking.exception.FlightBookingException;
import org.booking.util.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightDaoImpl implements FlightDao {
    private List<Flight> flights;
    private final String filePath;

    public FlightDaoImpl(String filePath) {
        this.filePath = filePath;
        this.flights = new ArrayList<>();
        loadFlights();
    }

    @Override
    public void loadFlights() {
        try {
            List<Flight> loadedFlights = FileUtil.loadFromFile(filePath);
            this.flights = loadedFlights != null ? loadedFlights : new ArrayList<>();
        } catch (Exception e) {
            throw new FlightBookingException("Error loading flights from file: " + e.getMessage());
        }
    }

    @Override
    public void saveFlights() {
        try {
            FileUtil.saveToFile(filePath, flights);
        } catch (Exception e) {
            throw new FlightBookingException("Error saving flights to file: " + e.getMessage());
        }
    }

    @Override
    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    @Override
    public Flight getFlightById(long id) {
        Optional<Flight> flightOptional = flights.stream()
                .filter(flight -> flight.getId() == id)
                .findFirst();

        if (flightOptional.isPresent()) {
            return flightOptional.get();
        } else {
            throw new FlightBookingException("Flight with id " + id + " not found");
        }
    }

    @Override
    public List<Flight> getFlightsInNext24Hours() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);

        return flights.stream()
                .filter(flight -> {
                    LocalDateTime flightDateTime = LocalDateTime.of(flight.getDate(), flight.getTime());
                    return flightDateTime.isAfter(now) &&
                            flightDateTime.isBefore(next24Hours) &&
                            "Kiev".equalsIgnoreCase(flight.getDepartureCity());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> searchFlights(String destination, LocalDate date, int passengers) {
        return flights.stream()
                .filter(flight -> flight.getDestinationCity().equalsIgnoreCase(destination) &&
                        flight.getDate().equals(date) &&
                        flight.getAvailableSeats() >= passengers)
                .collect(Collectors.toList());
    }

    @Override
    public void addFlight(Flight flight) {
        flights.add(flight);
        saveFlights();
    }

    @Override
    public void updateFlight(Flight flight) {
        boolean found = false;

        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getId() == flight.getId()) {
                flights.set(i, flight);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new FlightBookingException("Flight with id " + flight.getId() + " not found for update");
        }

        saveFlights();
    }

    @Override
    public void removeFlight(long flightId) {
        boolean removed = flights.removeIf(flight -> flight.getId() == flightId);

        if (!removed) {
            throw new FlightBookingException("Flight with id " + flightId + " not found for removal");
        }

        saveFlights();
    }
}