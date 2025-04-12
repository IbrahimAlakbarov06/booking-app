package org.booking.service;

import org.booking.dao.BookingDao;

public class BookingService {
    private BookingDao bookingDao;

    public BookingService(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }
}
