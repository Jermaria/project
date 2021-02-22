package by.jwd.task6.controller.command;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import by.jwd.task6.controller.command.impl.ChangeLocalCommand;
import by.jwd.task6.controller.command.impl.CheckEmailAvailabilityCommand;
import by.jwd.task6.controller.command.impl.ContactUsCommand;
import by.jwd.task6.controller.command.impl.ToErrorPageCommand;
import by.jwd.task6.controller.command.impl.ToMainPageCommand;
import by.jwd.task6.controller.command.impl.RegisterHotelCommand;
import by.jwd.task6.controller.command.impl.RegisterMealsCommand;
import by.jwd.task6.controller.command.impl.RegisterRoomsCommand;
import by.jwd.task6.controller.command.impl.SearchHotelByInputCommand;
import by.jwd.task6.controller.command.impl.SignInCommand;
import by.jwd.task6.controller.command.impl.SignOutCommand;
import by.jwd.task6.controller.command.impl.SignUpCommand;

public class CommandProvider {
    
    private static final CommandProvider instance = new CommandProvider();
    private Map<CommandName, Command> commands = new EnumMap<>(CommandName.class);
    
    private CommandProvider() {     
        commands.put(CommandName.SIGN_IN, new SignInCommand());
        commands.put(CommandName.SIGN_UP, new SignUpCommand());
        commands.put(CommandName.TO_MAIN_PAGE, new ToMainPageCommand());
        commands.put(CommandName.TO_ERROR_PAGE, new ToErrorPageCommand());
        commands.put(CommandName.LOCALIZATION, new ChangeLocalCommand());
        commands.put(CommandName.SIGN_OUT, new SignOutCommand());
        commands.put(CommandName.CHECK_EMAIL_AVAILABILITY, new CheckEmailAvailabilityCommand());
        commands.put(CommandName.CONTACT_US, new ContactUsCommand());
        commands.put(CommandName.REGISTER_HOTEL, new RegisterHotelCommand());
        commands.put(CommandName.REGISTER_ROOMS, new RegisterRoomsCommand());
        commands.put(CommandName.REGISTER_MEALS, new RegisterMealsCommand());
        commands.put(CommandName.SEARCH_HOTELS_BY_INPUT, new SearchHotelByInputCommand());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Optional<Command> getCommandOptional(String name) {      
        CommandName commandName = CommandName.valueOf(name.toUpperCase());
        Optional<Command> commandOptional = Optional.of(commands.get(commandName));
        return commandOptional;
    }

}
