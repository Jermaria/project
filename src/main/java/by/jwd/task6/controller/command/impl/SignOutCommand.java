package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;

public class SignOutCommand implements Command {
    
    private static final String MAIN_PAGE = "Controller?command=to_main_page";

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {     
        request.getSession(true).invalidate();
        SimpleEntry<CommandReturnType, String> entry = new SimpleEntry<>(CommandReturnType.PATH, MAIN_PAGE);
        return entry;       
    }
}
