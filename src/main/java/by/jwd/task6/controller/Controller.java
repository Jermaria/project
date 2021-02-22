package by.jwd.task6.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.AbstractMap.SimpleEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandProvider;
import by.jwd.task6.controller.command.CommandReturnType;

@WebServlet(name = "Servlet", value = "/Controller")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String COMMAND_PARAMETER = "command";
    private static final String PREVIOUS_REQUEST_PARAMETER = "lastRequest";
    private static final String STRING_ENCODING = "UTF-8";
    private static final String ERROR_PAGE = "jsp/error.jsp"; 
    private static final String REQUEST_SOURCE = "source";
    private static final String AJAX_SOURCE = "ajax";
    
    public Controller() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        
        processRequest(request, response); 
        // не запоминать ajax запросы, т.к. они не перенаправляют на страницу
        if (request.getParameter(REQUEST_SOURCE) != null && !request.getParameter(REQUEST_SOURCE).equals(AJAX_SOURCE)) {
            request.getSession().setAttribute(PREVIOUS_REQUEST_PARAMETER, request.getRequestURI() + "?" + request.getQueryString());
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);     
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {      
        String commandName = request.getParameter(COMMAND_PARAMETER);
        Optional<Command> commandToExecute = CommandProvider.getInstance().getCommandOptional(commandName.toUpperCase());
        
        if (commandToExecute.isPresent()) {
            SimpleEntry<CommandReturnType, String> resultEntry = commandToExecute.get().execute(request);
            resolveCommandResult(request, response, resultEntry);   
        } else {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
    }
    
    private void resolveCommandResult(HttpServletRequest request, HttpServletResponse response, 
                                        SimpleEntry<CommandReturnType, String> resultEntry) throws ServletException, IOException {
        
        if (resultEntry.getKey() == CommandReturnType.PATH) {
            String pagePath = resultEntry.getValue();
            request.getRequestDispatcher(pagePath).forward(request, response);
        } else {
            String content = resultEntry.getValue();
        //    response.setCharacterEncoding(STRING_ENCODING);
            response.getWriter().write(content);
        }    
    }

}
