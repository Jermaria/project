package by.jwd.task6.controller.command;


import java.util.AbstractMap;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    
    AbstractMap.SimpleEntry<CommandReturnType, String>execute(HttpServletRequest request);
    
}
