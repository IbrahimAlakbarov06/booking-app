package org.booking.service;

import org.booking.dao.FlightDao;
import org.booking.entity.Flight;
import org.booking.exception.FlightBookingException;

import java.time.LocalDate;
import java.util.List;

public class FlightService {
    private final FlightDao flightDAO;

    public FlightService(FlightDao flightDAO) {
        this.flightDAO = flightDAO;
    }

    public List<Flight> getAllFlights() {
        return flightDAO.getAllFlights();
    }

    public Flight getFlightById(int id) {
        if (id <= 0) {
            throw new FlightBookingException("Invalid flight ID");
        }
        return flightDAO.getFlightById(id);
    }

    public List<Flight> getFlightsInNext24Hours() {
        return flightDAO.getFlightsInNext24Hours();
    }

    public List<Flight> searchFlights(String destination, LocalDate date, int passengers){
        if (destination == null || destination.isEmpty()) {
            throw new FlightBookingException("Destination cannot be empty");
        }

        if (date == null) {
            throw new FlightBookingException("Date cannot be empty");
        }

        if(passengers <= 0) {
            throw new FlightBookingException("Number of passengers must be greater than zero");
        }
        return flightDAO.searchFlights(destination, date, passengers);
    }

    public void addFlight(Flight flight) {
        if (flight == null) {
            throw new FlightBookingException("Flight cannot be null");
        }

        flightDAO.addFlight(flight);
    }

    public void updateFlight(Flight flight) {
        if (flight == null) {
            throw new FlightBookingException("Flight cannot be null");
        }

        if (flight.getId() <= 0) {
            throw new FlightBookingException("Invalid flight ID");
        }
        flightDAO.updateFlight(flight);
    }

    public void removeFlight(long flightId) {
        if (flightId <= 0) {
            throw new FlightBookingException("Invalid flight ID");
        }
        flightDAO.removeFlight(flightId);
    }
}
