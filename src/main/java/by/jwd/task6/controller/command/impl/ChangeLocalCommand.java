package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;

public class ChangeLocalCommand implements Command {
    
    private static final String MAIN_PAGE = "index.jsp";
    private static final String LOCALE_PARAMETER = "local";
    private static final String LAST_REQUEST = "lastRequest";

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {
        String newLocal;
        String lastRequest;
        String path;
        newLocal = request.getParameter(LOCALE_PARAMETER);
        lastRequest = (String) request.getSession().getAttribute(LAST_REQUEST);
        request.getSession().setAttribute(LOCALE_PARAMETER, newLocal);
        if (lastRequest == null) {         
            path = MAIN_PAGE;   
        } else {           
            path = lastRequest;
        }
        SimpleEntry<CommandReturnType, String> entry = new SimpleEntry<>(CommandReturnType.PATH, path);
        return entry;
    }
}
