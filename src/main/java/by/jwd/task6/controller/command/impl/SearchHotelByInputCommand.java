package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;
import by.jwd.task6.entity.Hotel;
import by.jwd.task6.service.HotelService;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.impl.HotelServiceImpl;
import by.jwd.task6.util.DataMapKey;

public class SearchHotelByInputCommand implements Command {
    
    private static final String INPUT_PARAMETER = "searchbar_input";
    private static final String FROM_PARAMETER = "from";
    private static final String TILL_PARAMETER = "till";
    private static final String SEARCH_RESULT_ATTRIBUTE = "search_outcome";
    
    private static final String MAIN_PAGE = "Controller?command=to_main_page";
    private static final String ERROR_PAGE = "Controller?command=to_error_page";
    
    private HotelService service = new HotelServiceImpl();

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {
        
        SimpleEntry<CommandReturnType, String> entry;
        Map<String, String> data = new HashMap<>();
        data.put(DataMapKey.INPUT, request.getParameter(INPUT_PARAMETER));
        data.put(DataMapKey.FROM, request.getParameter(FROM_PARAMETER));
        data.put(DataMapKey.TILL, request.getParameter(TILL_PARAMETER));
        
        try {
            Optional<List<Hotel>> hotelsOptional = service.findHotelsByUserSearch(data);
            if (hotelsOptional.isPresent() && hotelsOptional.get().size() > 0) {
               request.getSession().setAttribute(SEARCH_RESULT_ATTRIBUTE , hotelsOptional.get());
                entry = new SimpleEntry<>(CommandReturnType.PATH, MAIN_PAGE);  //TODO заменить на ajax сообщения
            } else {
                entry = new SimpleEntry<>(CommandReturnType.CONTENT, String.valueOf(false)); // TODO сообщение, что найдено 0
            }
        } catch (ServiceException e) {
            entry = new SimpleEntry<>(CommandReturnType.PATH, ERROR_PAGE);
        }
        return entry;
    }

}
