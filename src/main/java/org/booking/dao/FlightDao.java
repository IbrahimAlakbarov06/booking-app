package org.booking.dao;

import org.booking.entity.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightDao {
    void loadFlights();
    void saveFlights();

    public List<Flight> getAllFlights();
    public Flight getFlightById(long id);
    public List<Flight> getFlightsInNext24Hours();
    public List<Flight> searchFlights(String destination, LocalDate date, int passengers);
    public void updateFlight(Flight flight);
}
