package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;

public class ToMainPageCommand implements Command {
    
    private static final String MAIN_PAGE = "index.jsp";

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) { 
        SimpleEntry<CommandReturnType, String> entry = new SimpleEntry<>(CommandReturnType.PATH, MAIN_PAGE);
        return entry;
    }

}
