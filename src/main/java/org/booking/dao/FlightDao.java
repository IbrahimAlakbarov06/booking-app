package org.booking.dao;

import org.booking.entity.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightDao {
    void loadFlights();

    void saveFlights();

    List<Flight> getAllFlights();

    Flight getFlightById(long id);

    List<Flight> getFlightsInNext24Hours();

    List<Flight> searchFlights(String destination, LocalDate date, int passengers);

    void addFlight(Flight flight);

    void updateFlight(Flight flight);

    void removeFlight(long flightId);
}
