package by.jwd.task6.service;

import java.util.Optional;

import by.jwd.task6.entity.User;
import by.jwd.task6.entity.UserRole;

public interface UserService {
    
    void signUp(String name, String surname, String email, char[] password, UserRole role)
            throws ServiceException, LoginAlreadyTakenServiceException, InvalidInputServiceException;
    Optional<User> signIn(String email, char[] password) throws ServiceException, InvalidInputServiceException; 
    boolean isEmailAvailable(String email) throws ServiceException;

}
