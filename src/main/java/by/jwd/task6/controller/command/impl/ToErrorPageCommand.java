package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;

public class ToErrorPageCommand implements Command {
    
    private static final String ERROR_PAGE = "jsp/error.jsp";   

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest req) {
        SimpleEntry<CommandReturnType, String> entry = new SimpleEntry<>(CommandReturnType.PATH, ERROR_PAGE);
        return entry;
    }

}
