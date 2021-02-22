package by.jwd.task6.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.jwd.task6.dao.DaoException;
import by.jwd.task6.dao.HotelDao;
import by.jwd.task6.dao.impl.HotelDaoImpl;
import by.jwd.task6.entity.Hotel;
import by.jwd.task6.service.HotelService;
import by.jwd.task6.service.InvalidInputServiceException;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.util.DataMapKey;

public class HotelServiceImpl implements HotelService {
    
    private HotelDao hotelDao = new HotelDaoImpl();

    @Override       
    public Optional<Hotel> registerHotel(Map<String, String> data) throws ServiceException, InvalidInputServiceException{
        Optional<Hotel> hotelOptional = Optional.empty();
        // TODO new validation

        try {
            hotelDao.registerHotel(data);
            hotelOptional = findHotelByHotelierId(Integer.valueOf(data.get(DataMapKey.USER_ID)));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return hotelOptional;
    }

    @Override
    public Optional<Hotel> findHotelByHotelierId(int hotelierId) throws ServiceException {
        Optional<Hotel> hotelOptional = Optional.empty();
        try {
            hotelOptional = hotelDao.findHotelByHotelierId(hotelierId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return hotelOptional;
    }

    @Override
    public void insertHotelPhoto(String path, int hotelId) throws ServiceException {
        try {
            hotelDao.insertHotelPhoto(hotelId, path);
        } catch (DaoException e) {
            throw new ServiceException();
        }
        
    }

    @Override
    public void insertMultipleTypeRooms(int hotelId, Map<String, String> rooms, Map<String, String> numberOfRooms) 
                                                                                            throws ServiceException {
        // TODO валидация
        
        try {
            hotelDao.insertMultipleTypeRooms(hotelId, rooms, numberOfRooms);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
    
    @Override
    public void insertMeals(int hotelId, Map<String, String> meals) throws ServiceException {
        //TODO validation
        try {
            hotelDao.insertMealTypes(hotelId, meals);
        } catch (DaoException e) {
            throw new ServiceException(e);
            
        }
    }

    @Override
    public Optional<List<Hotel>> findHotelsByUserSearch(Map<String, String> data) throws ServiceException {
        // TODO validation
        try {
            String input = data.get(DataMapKey.INPUT);
            LocalDate localDate = LocalDate.parse(data.get(DataMapKey.FROM));
            long from = localDate.toEpochDay();
            localDate = LocalDate.parse(data.get(DataMapKey.TILL));
            long till = localDate.toEpochDay();

            Optional<List<Hotel>> hotelsOptional = hotelDao.findHotels(input, from, till);
            return hotelsOptional;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
