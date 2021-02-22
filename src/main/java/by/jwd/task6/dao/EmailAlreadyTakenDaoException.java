package by.jwd.task6.dao;

public class EmailAlreadyTakenDaoException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public EmailAlreadyTakenDaoException() {}
    
    public EmailAlreadyTakenDaoException(String message, Exception e) {
        super(message, e);
    }
    
    public EmailAlreadyTakenDaoException(String message) {      
        super(message);
    }
    
    public EmailAlreadyTakenDaoException(Exception e) {     
        super(e);
    }
    
    

}
