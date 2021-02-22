package by.jwd.task6.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.jwd.task6.entity.Hotel;

public interface HotelService {
    
    Optional<Hotel> registerHotel(Map<String, String> data) throws ServiceException, InvalidInputServiceException;
    Optional<Hotel> findHotelByHotelierId(int hotelierId) throws ServiceException;
    public void insertHotelPhoto(String path, int hotelId) throws ServiceException;
    void insertMultipleTypeRooms(int hotelId, Map<String, String> rooms, Map<String, String> numberOfRooms) throws ServiceException;
    void insertMeals(int hotelId, Map<String, String> meals) throws ServiceException;
    Optional<List<Hotel>>findHotelsByUserSearch(Map<String, String> data) throws ServiceException;
    
    
}
