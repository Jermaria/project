package by.jwd.task6.service;

public class InvalidInputServiceException extends Exception{

    private static final long serialVersionUID = 1L;
    
    public InvalidInputServiceException() {}
    
    public InvalidInputServiceException(String message, Exception e) {        
        super(message, e);
    }
    
    public InvalidInputServiceException(String message) {     
        super(message);
    }   
    public InvalidInputServiceException(Exception e) {       
        super(e);
    }
}
