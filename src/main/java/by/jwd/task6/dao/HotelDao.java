package by.jwd.task6.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.jwd.task6.entity.Hotel;
import by.jwd.task6.entity.MealType;
import by.jwd.task6.entity.RoomType;

public interface HotelDao {
    
    boolean registerHotel(Map<String, String> data) throws DaoException;
    Optional<Hotel> findHotelByHotelierId(int hotelierId) throws DaoException;
    void insertHotelPhoto(int hotelId, String path) throws DaoException;
    void insertMultipleTypeRooms(int hotelId, Map<String, String> rooms, Map<String, String> numberOfRooms) throws DaoException;
    void insertMealTypes(int hotelId, Map<String, String> mealData) throws DaoException;
    Optional<List<Hotel>> findHotels(String input, long from, long till)  throws DaoException;
    
    boolean updateRooms(int hotelId, RoomType type, int number, BigDecimal pricePerNight) throws DaoException;
    boolean updateMeals(int hotelId, MealType type, BigDecimal price) throws DaoException;
    boolean updateParking(int hotelId, int number, BigDecimal price) throws DaoException;
    

}
