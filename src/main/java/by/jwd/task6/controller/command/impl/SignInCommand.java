package by.jwd.task6.controller.command.impl;

import java.util.Optional;
import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;
import by.jwd.task6.entity.Hotel;
import by.jwd.task6.entity.User;
import by.jwd.task6.entity.UserRole;
import by.jwd.task6.service.HotelService;
import by.jwd.task6.service.InvalidInputServiceException;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.UserService;
import by.jwd.task6.service.impl.HotelServiceImpl;
import by.jwd.task6.service.impl.UserServiceImpl;

public class SignInCommand implements Command {
    
    private static final String EMAIL = "email"; 
    private static final String PASSWORD = "password";
    
    private static final String MAIN_PAGE =  "Controller?command=to_main_page";
    private static final String ERROR_PAGE = "Controller?command=to_error_page";
    
    private static final String USER_ATTRIBUTE = "user";
    private static final String HOTEL_ATTRIBUTE = "hotel";
    private static final String SIGN_IN_FAILURE_MSG = "sign_in_msg";   //временное
    private static final String SIGN_IN_FAILURE_MSG_CONTENT = "wrong email or password";  // временное
    
    private UserService userService = new UserServiceImpl();
    private HotelService hotelService = new HotelServiceImpl();

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {         // добить AJAX (рег-ция прошла успешно)
        String path;                                                                            //    + предложить залогиниться
        String email = request.getParameter(EMAIL);
        char[] password = request.getParameter(PASSWORD).toCharArray();

        try {
            Optional<User> userOptional = userService.signIn(email, password);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                request.getSession().setAttribute(USER_ATTRIBUTE, user);                
                removeMistakeMessageIfExists(request);
                
                if (user.getRole() == UserRole.HOTELIER)                        
                    request.getSession().setAttribute("hotelier", "hotelier");
                    Optional<Hotel> hotelOptional = hotelService.findHotelByHotelierId(user.getUserId());
                    if (hotelOptional.isPresent()) {
                        Hotel hotel = hotelOptional.get();
                        request.getSession().setAttribute(HOTEL_ATTRIBUTE, hotel);
                    }
            } else {
                request.getSession().setAttribute(SIGN_IN_FAILURE_MSG, SIGN_IN_FAILURE_MSG_CONTENT);
            }
            
            path = MAIN_PAGE;   
        } catch (ServiceException e) {          
            path = ERROR_PAGE;
        } catch (InvalidInputServiceException e) {
            request.getSession().setAttribute(SIGN_IN_FAILURE_MSG, SIGN_IN_FAILURE_MSG_CONTENT);
            path = MAIN_PAGE;    
        }
        SimpleEntry<CommandReturnType, String> entry = new SimpleEntry<>(CommandReturnType.PATH, path);
        return entry;
    }
    private void removeMistakeMessageIfExists(HttpServletRequest request) {
       if (request.getSession().getAttribute(SIGN_IN_FAILURE_MSG) != null) {   // Убрать доработав js
            request.getSession().removeAttribute(SIGN_IN_FAILURE_MSG);
        }
    }
}
