package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;
import by.jwd.task6.entity.Hotel;
import by.jwd.task6.entity.User;
import by.jwd.task6.service.HotelService;
import by.jwd.task6.service.InvalidInputServiceException;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.impl.HotelServiceImpl;
import by.jwd.task6.util.DataMapKey;
//TODO добавить аннотацию с ролью
public class RegisterHotelCommand implements Command {
    
    private HotelService service = new HotelServiceImpl(); 
    
    private static final String USER_ATTRIBUTE = "user";
    private static final String NAME_PARAMETER = "name";
    private static final String COUNTRY_PARAMETER = "country";
    private static final String CITY_PARAMETER = "city";
    private static final String STREET_PARAMETER = "street";
    private static final String BUILDING_PARAMETER = "building";
    private static final String STARS_PARAMETER = "stars";
    private static final String WIFI_PARAMETER = "wifi";
    private static final String HOTEL_ATTRIBUTE = "hotel";
    
    private static final String MAIN_PAGE = "Controller?command=to_main_page";
    private static final String ERROR_PAGE = "Controller?command=to_error_page";

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {
        SimpleEntry<CommandReturnType, String> entry;
        Map<String, String> data = new HashMap<>();
        User user = (User)request.getSession().getAttribute(USER_ATTRIBUTE);
        int userId = user.getUserId();
        data.put(DataMapKey.USER_ID, String.valueOf(userId));
        data.put(DataMapKey.NAME, request.getParameter(NAME_PARAMETER));
        data.put(DataMapKey.COUNTRY, request.getParameter(COUNTRY_PARAMETER));
        data.put(DataMapKey.CITY, request.getParameter(CITY_PARAMETER));
        data.put(DataMapKey.STREET, request.getParameter(STREET_PARAMETER));
        data.put(DataMapKey.BUILDING, request.getParameter(BUILDING_PARAMETER));
        data.put(DataMapKey.STARS, request.getParameter(STARS_PARAMETER));
        data.put(DataMapKey.WIFI, request.getParameter(WIFI_PARAMETER));
        /*
        String name = request.getParameter(NAME_PARAMETER);
        String country = request.getParameter(COUNTRY_PARAMETER);
        String city = request.getParameter(CITY_PARAMETER);
        String street = request.getParameter(STREET_PARAMETER);
        String building = request.getParameter(BUILDING_PARAMETER);
        int stars = Integer.valueOf(request.getParameter(STARS_PARAMETER));
        boolean wifi = Boolean.valueOf(request.getParameter(WIFI_PARAMETER));
        */
        try {
            Optional<Hotel> hotelOptional = service.registerHotel(data);
            if (hotelOptional.isPresent()) {
                Hotel hotel = hotelOptional.get();
                request.getSession().setAttribute(HOTEL_ATTRIBUTE, hotel);
                entry = new SimpleEntry<>(CommandReturnType.PATH, MAIN_PAGE);
            } else {
                String failedToFindHotelMessage = "failed to find hotel";
                entry = new SimpleEntry<>(CommandReturnType.CONTENT, failedToFindHotelMessage);
            }
        }  catch (ServiceException e) {
            entry = new SimpleEntry<>(CommandReturnType.PATH, ERROR_PAGE);
        } catch (InvalidInputServiceException e) {
            String invalidInputMessage = "invalid input";
            entry = new SimpleEntry<>(CommandReturnType.CONTENT, invalidInputMessage);
        }
        return entry;
    }

}
