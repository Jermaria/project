package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;
import by.jwd.task6.entity.Hotel;
import by.jwd.task6.entity.User;
import by.jwd.task6.service.HotelService;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.impl.HotelServiceImpl;

public class RegisterMealsCommand implements Command {
    
    private static final String HOTEL_ATTRIBUTE = "hotel";
    private static final String FIRST_MEAL_TYPE = "first_meal_type";
    private static final String SECOND_MEAL_TYPE = "second_meal_type";
    private static final String THIRD_MEAL_TYPE = "third_meal_type";
    private static final String FOURTH_MEAL_TYPE = "fourth_meal_type";
    private static final String FIFTH_MEAL_TYPE = "fifth_meal_type";
    private static final String FIRST_MEAL_PRICE = "first_meal_price";
    private static final String SECOND_MEAL_PRICE = "second_meal_price";
    private static final String THIRD_MEAL_PRICE = "third_meal_price";
    private static final String FOURTH_MEAL_PRICE = "fourth_meal_price";
    private static final String FIFTH_MEAL_PRICE = "fifth_meal_price";
    
    private static final String USER_ATTRIBUTE = "user";
    
    private static final String MAIN_PAGE = "Controller?command=to_main_page";
    private static final String ERROR_PAGE = "Controller?command=to_error_page";
    
    private HotelService hotelService = new HotelServiceImpl();
    
    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {

        SimpleEntry<CommandReturnType, String> entry;
        Map<String, String> meals = new HashMap<>();
        //TODO добавить валидацию

        meals.put(request.getParameter(FIRST_MEAL_TYPE), request.getParameter(FIRST_MEAL_PRICE));
        meals.put(request.getParameter(SECOND_MEAL_TYPE), request.getParameter(SECOND_MEAL_PRICE));
        meals.put(request.getParameter(THIRD_MEAL_TYPE), request.getParameter(THIRD_MEAL_PRICE));
        meals.put(request.getParameter(FOURTH_MEAL_TYPE), request.getParameter(FOURTH_MEAL_PRICE));
        meals.put(request.getParameter(FIFTH_MEAL_TYPE), request.getParameter(FIFTH_MEAL_PRICE));

        int hotelId = ((Hotel)request.getSession().getAttribute(HOTEL_ATTRIBUTE)).getHotelId();
        
        try {
            hotelService.insertMeals(hotelId, meals);
            int userId = ((User)request.getSession().getAttribute(USER_ATTRIBUTE)).getUserId();
            Hotel hotel = hotelService.findHotelByHotelierId(userId).get();
            request.getSession().setAttribute(HOTEL_ATTRIBUTE, hotel);
            entry = new SimpleEntry<>(CommandReturnType.PATH, MAIN_PAGE);  //TODO временно, вернуть сообщение, что всё ок
        } catch (ServiceException e) {
            entry = new SimpleEntry<>(CommandReturnType.PATH, ERROR_PAGE);
        }
        return entry;
        
    }

}
