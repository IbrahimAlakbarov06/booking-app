package org.booking.dao;

import org.booking.entity.Flight;
import org.booking.exception.FlightBookingException;
import org.booking.util.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightDaoImpl implements FlightDao {
    private List<Flight> flights;
    private String filePath;

    public FlightDaoImpl(String filePath) {
        this.filePath = filePath;
        this.flights = new ArrayList<>();
        loadFlights();
    }

    @Override
    public void loadFlights() {
        try {
            this.flights = FileUtil.loadFromFile(filePath);
            if (this.flights == null) {
                this.flights = new ArrayList<>();
            }
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
        return flights.stream()
                .filter(flight -> flight.getId() == id)
                .findFirst().
                orElseThrow(() -> new FlightBookingException("Flight with id " + id + " not found"));
    }

    @Override
    public List<Flight> getFlightsInNext24Hours() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);

        return flights.stream()
                .filter(flight -> {
                    LocalDateTime flightDateTime = LocalDateTime.of(flight.getDate(), flight.getTime());
                    return flightDateTime.isAfter(now) && flightDateTime.isBefore(next24Hours) &&
                            "Kiev".equalsIgnoreCase(flight.getDepartureCity());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> searchFlights(String destination, LocalDate date, int passengers) {
        return flights.stream()
                .filter(flight -> flight.getDestinationCity().equals(destination) &&
                        flight.getDate() == date &&
                        flight.getAvailableSeats() >= passengers)
                .collect(Collectors.toList());
    }

    @Override
    public void addFlight(Flight flight){
        flights.add(flight);
        saveFlights();
    }

    @Override
    public void updateFlight(Flight flight) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getId() == flight.getId()) {
                flights.set(i, flight);
                saveFlights();
                return;
            }
        }
        throw new FlightBookingException("Flight with id " + flight.getId() + " not found");
    }

    @Override
    public void removeFlight(long flightId){
        boolean found = flights.removeIf(flight -> flight.getId() == flightId);
        if (!found) {
            throw new FlightBookingException("Flight with id " + flightId + " not found");
        } else {
            saveFlights();
        }

    }
}
