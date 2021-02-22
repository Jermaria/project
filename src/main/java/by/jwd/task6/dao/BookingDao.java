package by.jwd.task6.dao;

import java.util.Set;

import by.jwd.task6.entity.Booking;
import by.jwd.task6.entity.MealType;
import by.jwd.task6.entity.RoomType;

public interface BookingDao {
    
    Booking bookHotel(int hotelId, int guestsNumber, MealType meal, RoomType type, long from, long till);
    boolean cancelBooking(int bookingId);
    Set<Booking> findBookings(int userId);

}
