package org.booking.dao;

import org.booking.entity.Flight;
import org.booking.exception.FlightBookingException;
import org.booking.util.FileUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        LocalDate date = LocalDate.now();
        LocalDate next24= date.plusDays(24);
        return flights;

    }

    @Override
    public List<Flight> searchFlights(String destination, LocalDate date, int passengers) {
        return List.of();
    }

    @Override
    public void updateFlight(Flight flight) {

    }
}
