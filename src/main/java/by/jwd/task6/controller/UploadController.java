package by.jwd.task6.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.jwd.task6.entity.Hotel;
import by.jwd.task6.service.HotelService;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.impl.HotelServiceImpl;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = { "/upload/*" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private HotelService hotelService = new HotelServiceImpl();

    private static final String HOTEL_ATTRIBUTE = "hotel";
    private static final String MAIN_PAGE = "Controller?command=to_main_page";
    private static final String UPLOAD_FILE_DIR = "/hotels" + File.separator; // see context in server.xml
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        File fileSaveDir = new File(UPLOAD_FILE_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        int hotelId = Integer.valueOf(((Hotel) request.getSession().getAttribute(HOTEL_ATTRIBUTE)).getHotelId());
        request.getParts().stream().forEach(part -> {
            try {
                String path = UPLOAD_FILE_DIR + part.getSubmittedFileName();
                part.write(path);
                hotelService.insertHotelPhoto(path, hotelId);
            } catch (IOException e) {
               // TODO заменить на ajax сообщение
                e.printStackTrace();
            } catch (ServiceException e) {
                //TODO
                e.printStackTrace();
            }
        });
        request.getRequestDispatcher(MAIN_PAGE).forward(request, response); // TODO заменить на ajax сообщение
    }
}
