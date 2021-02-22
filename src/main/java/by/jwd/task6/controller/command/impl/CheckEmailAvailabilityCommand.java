package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.UserService;
import by.jwd.task6.service.impl.UserServiceImpl;

public class CheckEmailAvailabilityCommand implements Command {
    
    private static final String EMAIL_PARAMETER = "email";
    private static final String ERROR_PAGE = "Controller?command=to_error_page";
    private UserService service = new UserServiceImpl();

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {
        CommandReturnType type;
        String result;
        String email = request.getParameter(EMAIL_PARAMETER);
        try {
            boolean isAvailable = service.isEmailAvailable(email);
            result = String.valueOf(isAvailable);
            type = CommandReturnType.CONTENT;
        } catch (ServiceException e) {
            type = CommandReturnType.PATH;
            result = ERROR_PAGE;
        }
        SimpleEntry<CommandReturnType, String> entry = new SimpleEntry<>(type, result);
        return entry;
    }

}
