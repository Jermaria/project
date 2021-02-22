package by.jwd.task6.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.task6.dao.CloseDaoResource;
import by.jwd.task6.dao.DaoException;
import by.jwd.task6.dao.HotelDao;
import by.jwd.task6.dao.StatementGeneratable;
import by.jwd.task6.dao.impl.pool.ConnectionPool;
import by.jwd.task6.dao.impl.pool.ConnectionPoolException;
import by.jwd.task6.entity.Address;
import by.jwd.task6.entity.Hotel;
import by.jwd.task6.entity.MealType;
import by.jwd.task6.entity.RoomFund;
import by.jwd.task6.entity.RoomType;
import by.jwd.task6.util.DataMapKey;

public class HotelDaoImpl implements HotelDao, CloseDaoResource, StatementGeneratable {
    
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool pool = ConnectionPool.getInstance();
    
    private static final String INSERT_ADDRESS_SQL = "INSERT INTO addresses (country, city, street, building) " +
                                                       " VALUES (?,?,?,?)";
    private static final String INSERT_HOTEL_SQL = "INSERT INTO hotels (name, wifi, stars_id, users_id, addresses_id) " 
                                                       + "VALUES(?,?,?,?,?)";
    private static final String FIND_HOTEL_BY_HOTELIER_SQL = "SELECT hotels.id, name, wifi, stars_id, country, city,"
                                                                + " street, building FROM hotels, addresses "
                                                                + "WHERE addresses_id = addresses.id AND users_id = ?";  
    private static final String INSERT_HOTEL_PHOTO_SQL = "INSERT INTO hotel_photos (path, hotels_id) VALUES (?,?)";
    private static final String INSERT_SINGLE_ROOM_SQL = "INSERT INTO rooms (price_per_room, room_types_id, hotels_id) VALUES(?,?,?)";
    private static final String INSERT_MEAL_SQL = "INSERT INTO meals (hotels_id, meal_types_id, price) VALUES (?,?,?)";
    private static final String FIND_ROOMS_BY_HOTEL_ID = "SELECT room_types_id, price_per_room FROM rooms WHERE hotels_id = ?";
    private static final String FIND_MEALS_BY_HOTEL_ID = "SELECT meal_types_id, price FROM meals WHERE hotels_id = ?";
    private static final String FIND_HOTEL_TRAVELLER_SEARCH_SQL = "SELECT DISTINCT hotels.users_id "
                                                                + "FROM hotels, addresses, free_slots "
                                                                + "WHERE hotels.addresses_id = addresses.id "
                                                                + "AND hotels.id = free_slots.rooms_hotels_id "
                                                                + "AND free_slots.from_date <= ? "
                                                                + "AND free_slots.till_date >= ? "
                                                                + "AND MATCH (addresses.country, addresses.city) "
                                                                + "AGAINST(?)";
    private static final String FIND_HOTEL_PHOTOS_SQL = "SELECT path FROM hotel_photos WHERE hotels_id = ?";
    private static final String INSERT_INITIAL_FREE_SLOTS_SQL = "INSERT INTO free_slots (from_date, till_date, rooms_id, "
                                                                                    + "rooms_room_types_id, rooms_hotels_id) "
                                                                                    + "VALUES(?,?,?,?,?)";
    @Override 
    public boolean registerHotel(Map<String, String> data) throws DaoException {
        boolean wasRegistered = false;
        Connection connection = null;
        try{
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            // PreparedStatement will be initialized with Object[] values in a loop, see the Generatable interface
            Object[] addressValues = new String[] {data.get(DataMapKey.COUNTRY), data.get(DataMapKey.CITY), 
                                                   data.get(DataMapKey.STREET), data.get(DataMapKey.BUILDING)};
            int addressId = registerAddress(connection, addressValues);
            if (addressId > 0) {
                Object[] hotelValues = new Object[] {data.get(DataMapKey.NAME), Boolean.valueOf(data.get(DataMapKey.WIFI)), 
                                                data.get(DataMapKey.STARS), data.get(DataMapKey.USER_ID), String.valueOf(addressId)}; 
                wasRegistered = insertHotel(connection, hotelValues);
                if (wasRegistered) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            rollBack(connection);    
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return wasRegistered;
    }
    // или просто логировать?
    private void rollBack(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
                throw new DaoException(e);
            }
        }
    }

    private int registerAddress(Connection connection, Object[] address) throws SQLException  {
        int addressId = 0;
        try (PreparedStatement statement = generatePreparedStatement(connection, INSERT_ADDRESS_SQL, address); 
            ResultSet resultSet = getGeneratedKeysAfterUpdate(statement)) {
            if (resultSet.next()) {
                addressId = resultSet.getInt(1);
            }
        }
        return addressId;
    }
    
    private boolean insertHotel(Connection connection, Object[] values) throws SQLException {
        boolean wasInserted = false;
        try (PreparedStatement statement = generatePreparedStatement(connection, INSERT_HOTEL_SQL, values)) {
            statement.executeUpdate();
            wasInserted = true;
        }
        return wasInserted;
    }
    
    @Override
    public Optional<Hotel> findHotelByHotelierId(int hotelierId) throws DaoException {
        Optional<Hotel> hotelOptional = Optional.empty();
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            hotelOptional = findHotel(connection, hotelierId);
            if (hotelOptional.isPresent()) {
                Hotel hotel = hotelOptional.get();
                RoomFund rooms = findRoomsByHotelId(connection, hotel.getHotelId());
                Map<MealType, BigDecimal> meals = findMealsByHotelId(connection, hotel.getHotelId());
                List<String> photoPaths = findHotelPhotos(connection, hotel.getHotelId());
                hotel.setMeals(meals);
                hotel.setRoomFund(rooms);
                hotel.setPhotoPaths(photoPaths);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            rollBack(connection); 
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return hotelOptional;
    }
    
    private Optional<Hotel> findHotel(Connection connection, int hotelierId) throws SQLException {
        Optional<Hotel> hotelOptional = Optional.empty();
        try (PreparedStatement statement = generatePreparedStatement(connection, FIND_HOTEL_BY_HOTELIER_SQL, hotelierId);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int hotelId = resultSet.getInt(SQLColumnTitle.ID);
                String name = resultSet.getString(SQLColumnTitle.NAME);
                boolean wifi = resultSet.getBoolean(SQLColumnTitle.WIFI);
                int starsNumber = resultSet.getInt(SQLColumnTitle.STARS_ID);
                String country = resultSet.getString(SQLColumnTitle.COUNTRY);
                String city = resultSet.getString(SQLColumnTitle.CITY);
                String street = resultSet.getString(SQLColumnTitle.STREET);
                String building = resultSet.getString(SQLColumnTitle.BUILDING);
                
                Address address = new Address(country, city, street, building);
                Hotel hotel = new Hotel(hotelId, name, address, starsNumber, wifi);
                hotelOptional = Optional.of(hotel);
            }
        }
        return hotelOptional;
    }
    
    private RoomFund findRoomsByHotelId(Connection connection, int hotelId) throws SQLException {     
        RoomFund roomFund = new RoomFund();
        try (PreparedStatement statement = generatePreparedStatement(connection, FIND_ROOMS_BY_HOTEL_ID, hotelId);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                RoomType type = RoomType.getRoomTypeByIndex(resultSet.getInt(SQLColumnTitle.ROOM_TYPES_ID)).get();
                // if room of such type exists in room fund, increment number of such rooms by 1
                if (roomFund.getRooms().containsKey(type)) {
                    int numberOfRooms = roomFund.getNumberOfRooms().get(type);
                    roomFund.getNumberOfRooms().put(type, numberOfRooms+1);
                } else {
                    BigDecimal price = resultSet.getBigDecimal(SQLColumnTitle.PRICE_PER_ROOM);
                    roomFund.getRooms().put(type, price);
                    roomFund.getNumberOfRooms().put(type, 1);
                }
            }
        }
        return roomFund;
    }
    
    public Map<MealType, BigDecimal> findMealsByHotelId(Connection connection, int hotelId) throws SQLException {
        Map<MealType, BigDecimal> meals = new HashMap<>();
        try (PreparedStatement statement = generatePreparedStatement(connection, FIND_MEALS_BY_HOTEL_ID, hotelId);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                MealType type = MealType.getMealTypeByIndex(resultSet.getInt(1)).get();
                BigDecimal price = resultSet.getBigDecimal(2);
                meals.put(type, price);
            }
        }
        return meals;
    }
    
    @Override
    public void insertHotelPhoto(int hotelId, String path) throws DaoException {
        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = generatePreparedStatement(connection, INSERT_HOTEL_PHOTO_SQL, path, hotelId)) {
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException(e);
        }
    }
    
    private List<String> findHotelPhotos(Connection connection, int hotelId) throws SQLException {
        List<String> paths = new ArrayList<>();
        try(PreparedStatement statement = generatePreparedStatement(connection, FIND_HOTEL_PHOTOS_SQL, hotelId);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String path = resultSet.getString(1); 
                paths.add(path);
            }
        }
        return paths;
    }
    
 // TODO REFACTOR ALL METHODS WITH ROOMS!!!
    @Override
    public void insertMultipleTypeRooms(int hotelId, Map<String, String> rooms, Map<String, String> numberOfRooms) throws DaoException {
        String[][] roomData = rooms.entrySet().stream()
                             .map(e -> new String[]{e.getValue(), e.getKey(), String.valueOf(hotelId), numberOfRooms.get(e.getKey())})
                             .toArray(String[][]::new);

        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            for (String[] roomDetails : roomData) {
                System.out.println(Arrays.toString(roomDetails));
                insertSingleTypeRooms(connection, roomDetails);
            }
            connection.setAutoCommit(true);  
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            rollBack(connection);
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }
    
    public void insertSingleTypeRooms(Connection connection, String[] details) throws DaoException {
        int numberOfRooms = Integer.valueOf(details[3]); //TODO constant
        try {
            while(numberOfRooms > 0) {
                insertSingleRoom(connection, details[0], details[1], details[2]);
                numberOfRooms--;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException();
        }
    }
    
    private void insertSingleRoom(Connection connection, String price, String roomType, String hotelId) throws SQLException {
        try(PreparedStatement statement = generatePreparedStatement(connection, INSERT_SINGLE_ROOM_SQL, price, roomType, hotelId);
            ResultSet resultSet = getGeneratedKeysAfterUpdate(statement)) {
            if (resultSet.next()) {
                int roomIndex = resultSet.getInt(1);
                insertInitialFreeSlots(connection, roomIndex, roomType, hotelId);
            }
        }
    }
 
    
    private void insertInitialFreeSlots(Connection connection, int roomId, String roomType, String hotelId) throws SQLException {
        LocalDate today = LocalDate.now();
        long from = today.toEpochDay();
        LocalDate till = today.plusYears(2); //TODO в константы
        long tillLong = till.toEpochDay(); 
        try(PreparedStatement statement = generatePreparedStatement(connection, INSERT_INITIAL_FREE_SLOTS_SQL, 
                                          from, tillLong, roomId, roomType, hotelId)) {
            statement.executeUpdate();
        }
    }
    
    @Override
    public void insertMealTypes(int hotelId, Map<String, String> mealData) throws DaoException {
        // divide all meal data to small arrays containing SINGLE MEAL DETAILS (Meal type and price)
        // small arrays are used to generate Prepared Statement in a loop
        String[][] mealDetails =  mealData.entrySet().stream()
                                  .map(e -> new String[]{String.valueOf(hotelId), e.getKey(), e.getValue()})
                                  .toArray(String[][]::new); 
        Connection connection = null;
        try {
            connection = pool.takeConnection();
            connection.setAutoCommit(false);
            for (Object[] mealInfo : mealDetails) {
                insertMeal(connection, mealInfo);
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            rollBack(connection);
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        
    }
    
    private void insertMeal(Connection connection, Object[] mealInfo) throws SQLException {
        try (PreparedStatement statement = generatePreparedStatement(connection, INSERT_MEAL_SQL, mealInfo)) {
            statement.executeUpdate();
        }
    }
    
    @Override
    public Optional<List<Hotel>> findHotels(String input, long from, long till) throws DaoException {
        Optional<List<Hotel>> hotelsOptional = Optional.empty();
        Connection connection = null;
        try {
            connection = pool.takeConnection(); 
            connection.setAutoCommit(false);
            List<Integer> hotelierIds = findHoteliers(connection, input, from, till);
            if (hotelierIds.size() > 0) {
                List<Hotel> hotels = new ArrayList<>();
                for (int id : hotelierIds) {
                    Optional<Hotel> hotelOptional = findHotelByHotelierId(id);
                    if (hotelOptional.isPresent()) {
                        hotels.add(hotelOptional.get());
                    }
                }
                hotelsOptional = Optional.of(hotels);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            rollBack(connection);
            throw new DaoException(e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
        return hotelsOptional;
    }
    // TODO позже доработать guest number
    private List<Integer> findHoteliers(Connection connection, String input, long from, long till) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (PreparedStatement statement = generatePreparedStatement(connection, FIND_HOTEL_TRAVELLER_SEARCH_SQL, from, till, input);
            ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                ids.add(id);
            }
        }      
        return ids;
    }


    @Override
    public boolean updateRooms(int hotelId, RoomType type, int number, BigDecimal pricePerNight) throws DaoException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateMeals(int hotelId, MealType type, BigDecimal price) throws DaoException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateParking(int hotelId, int number, BigDecimal price) throws DaoException {
        // TODO Auto-generated method stub
        return false;
    }




}


