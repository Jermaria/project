package by.jwd.task6.service;

public class LoginAlreadyTakenServiceException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public LoginAlreadyTakenServiceException() {}
    
    public LoginAlreadyTakenServiceException(String message, Exception e) {
        super(message, e);
    }
    
    public LoginAlreadyTakenServiceException(String message) {      
        super(message);
    }
    
    public LoginAlreadyTakenServiceException(Exception e) {     
        super(e);
    }
    
    

}
