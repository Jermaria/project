package by.jwd.task6.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import by.jwd.task6.entity.Hotel;
import by.jwd.task6.entity.RoomType;

public class HotelDataTag extends TagSupport {
    
    private static final String SEARCH_RESULT_ATTRIBUTE = "search_outcome"; 
    
    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        List<Hotel> hotels = (List<Hotel>) session.getAttribute(SEARCH_RESULT_ATTRIBUTE);
        if (hotels.size() > 0) {
            for (Hotel hotel : hotels) {
                JspWriter out = pageContext.getOut();
                
                try {
                    out.write("<div class=\"item-line\">");
                    out.write("<div class=\"options inline-bl\">");
                    out.write("<span class=\"item_title\">" + hotel.getName() + "</span>");
                    out.write("<p>" + hotel.getAddress().getCountry() + ", " + hotel.getAddress().getCity() + ", " 
                            + hotel.getAddress().getBuilding() + " " + hotel.getAddress().getStreet() + "</p>");
                    out.write("<img class=\"pic\" src=\"" + hotel.getPhotoPaths().get(0) + "\">");
                    out.write("<span class=\"extra\">Short description of this hotel stored only in html, not the database</span>");
                    out.write("</div>");
                    out.write("<div class=\"price item_price inline-bl\">" + hotel.getRoomFund().getRooms().get(RoomType.DOUBLE) + "</div>");
                    out.write("<form class=\"btn_brief_form inline-bl\" action=\"\">");
                    out.write("<input class=\"info_btn\" type=\"submit\" value=\"More Info\" />");
                    out.write("</form>");
                    out.write("</div>");
                    out.write("<hr />");
                } catch (IOException e) {  // TODO log
                    throw new JspException(e);
                }
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

}
