package Interface;

import Entities.Bookings;

public interface BookingsDao {
    void saveBooking(Bookings booking);
    void deleteBooking(int bookingID);
    void updateBooking(Bookings booking);
    Bookings getBookingById(int bookingId);
}