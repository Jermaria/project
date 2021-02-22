package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;
import by.jwd.task6.entity.UserRole;
import by.jwd.task6.service.InvalidInputServiceException;
import by.jwd.task6.service.LoginAlreadyTakenServiceException;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.UserService;
import by.jwd.task6.service.impl.UserServiceImpl;

public class SignUpCommand implements Command {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email"; 
    private static final String PASSWORD = "password";
    private static final String ROLE_INDEX = "role";
    
    private static final String MAIN_PAGE = "Controller?command=to_main_page";
    private static final String ERROR_PAGE = "Controller?command=to_error_page";

    private static final String MSG = "message";
    private static final String INAVAILABLE_EMAIL_MSG_CONTENT = "     this email is already registered";  // добавить в локаль
    private static final String SIGN_UP_FAILURE_MSG_CONTENT = "invalid data input";
    
    private static final String OVERLAY = "overlay";      // временное решение, разобраться с ajax
    private static final String OVERLAY_STYLE = "block";  // 
    
    private UserService service = new UserServiceImpl();
    
    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {     // добавить json сообщение об ошибке
        String path;
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String email = request.getParameter(EMAIL);
        char[] password = request.getParameter(PASSWORD).toCharArray();
        int roleIndex = Integer.valueOf(request.getParameter(ROLE_INDEX));
        UserRole role;
        if (UserRole.getRoleByIndex(roleIndex).isPresent()) {
            role = UserRole.getRoleByIndex(roleIndex).get();
        } else {
            role = UserRole.TRAVELLER;
        }
        try {
            service.signUp(name, surname, email, password,  role);
            if (request.getSession().getAttribute(MSG) != null) {   // TODO УБРАТЬ ПОСЛЕ ДОРАБОТКИ JS
                request.getSession().removeAttribute(MSG);
                request.getSession().removeAttribute(OVERLAY);             
            }
            path = MAIN_PAGE;    
        } catch (ServiceException e) {          
            path = ERROR_PAGE;
        } catch (LoginAlreadyTakenServiceException e) {            
            request.getSession().setAttribute(MSG, INAVAILABLE_EMAIL_MSG_CONTENT);
            request.getSession().setAttribute(OVERLAY, OVERLAY_STYLE);
            path = MAIN_PAGE;            
        } catch (InvalidInputServiceException e) {
            request.getSession().setAttribute(OVERLAY, OVERLAY_STYLE);
            request.getSession().setAttribute(MSG, SIGN_UP_FAILURE_MSG_CONTENT);
            path = MAIN_PAGE;      
        }
        SimpleEntry<CommandReturnType, String> entry = new SimpleEntry<>(CommandReturnType.PATH, path);
        return entry;
    }

}
