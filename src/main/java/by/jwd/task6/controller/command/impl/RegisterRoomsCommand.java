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

public class RegisterRoomsCommand implements Command {
    
    private static final String FIRST_ROOM_TYPE_PARAMETER = "first_room_type";
    private static final String FIRST_ROOM_NUMBER_PARAMETER = "first_room_number";
    private static final String FIRST_ROOM_PRICE_PARAMETER = "first_room_price";
    private static final String SECOND_ROOM_TYPE_PARAMETER = "second_room_type";
    private static final String SECOND_ROOM_NUMBER_PARAMETER = "second_room_number";
    private static final String SECOND_ROOM_PRICE_PARAMETER = "second_room_price";
    private static final String THIRD_ROOM_TYPE_PARAMETER = "third_room_type";
    private static final String THIRD_ROOM_NUMBER_PARAMETER = "third_room_number";
    private static final String THIRD_ROOM_PRICE_PARAMETER = "third_room_price";
    private static final String HOTEL_ATTRIBUTE = "hotel";
    private static final String USER_ATTRIBUTE = "user";
    
    private static final String MAIN_PAGE = "Controller?command=to_main_page";
    private static final String ERROR_PAGE = "Controller?command=to_error_page";
    
    private HotelService hotelService = new HotelServiceImpl();

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {
        SimpleEntry<CommandReturnType, String> entry;
        //TODO проверки
        Map<String, String> rooms = new HashMap<>();
        Map<String, String> roomsNumber = new HashMap<>();
        
        rooms.put(request.getParameter(FIRST_ROOM_TYPE_PARAMETER), request.getParameter(FIRST_ROOM_PRICE_PARAMETER));
        rooms.put(request.getParameter(SECOND_ROOM_TYPE_PARAMETER), request.getParameter(SECOND_ROOM_PRICE_PARAMETER));
        rooms.put(request.getParameter(THIRD_ROOM_TYPE_PARAMETER), request.getParameter(THIRD_ROOM_PRICE_PARAMETER));
        
        roomsNumber.put(request.getParameter(FIRST_ROOM_TYPE_PARAMETER) ,request.getParameter(FIRST_ROOM_NUMBER_PARAMETER));
        roomsNumber.put(request.getParameter(SECOND_ROOM_TYPE_PARAMETER), request.getParameter(SECOND_ROOM_NUMBER_PARAMETER));
        roomsNumber.put(request.getParameter(THIRD_ROOM_TYPE_PARAMETER), request.getParameter(THIRD_ROOM_NUMBER_PARAMETER));

        int hotelId = ((Hotel)request.getSession().getAttribute(HOTEL_ATTRIBUTE)).getHotelId();
        
        try {
            hotelService.insertMultipleTypeRooms(hotelId, rooms, roomsNumber);
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
