package by.jwd.task6.controller.command.impl;

import java.util.AbstractMap.SimpleEntry;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import by.jwd.task6.controller.command.Command;
import by.jwd.task6.controller.command.CommandReturnType;
import by.jwd.task6.util.MailSender;

public class ContactUsCommand implements Command {
    
    private static final String MAIN_PAGE =  "Controller?command=to_main_page";
    
    private static final String MAIL_TO = "kablariya@gmail.com";  // либо найти в базе email админа (но их мб несколько)
    private static final String SUBJECT = "Contact via welcome page form";
    private static final String TEXT_PARAMETER = "text";

    @Override
    public SimpleEntry<CommandReturnType, String> execute(HttpServletRequest request) {
        SimpleEntry<CommandReturnType, String> entry;
        String text = request.getParameter(TEXT_PARAMETER);
        
        if (text != null && !text.isBlank()) {
            boolean isSuccessful;
            try {
                MailSender.send(MAIL_TO, SUBJECT, text);
                isSuccessful = true;
            } catch (MessagingException e) {
                // TODO выводим сообщение об ошибке отправки ( + бандл "{$messge_successfull}"...)
                isSuccessful = false;
            }
            entry = new SimpleEntry<>(CommandReturnType.CONTENT, String.valueOf(isSuccessful));
        } else {
            entry = new SimpleEntry<>(CommandReturnType.PATH, MAIN_PAGE);
        }
        return entry;
    }
    
}
