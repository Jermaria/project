package by.jwd.task6.service.impl;

import java.util.Optional;

import by.jwd.task6.dao.DaoException;
import by.jwd.task6.dao.EmailAlreadyTakenDaoException;
import by.jwd.task6.dao.UserDao;
import by.jwd.task6.dao.impl.UserDaoImpl;
import by.jwd.task6.entity.User;
import by.jwd.task6.entity.UserRole;
import by.jwd.task6.service.InvalidInputServiceException;
import by.jwd.task6.service.LoginAlreadyTakenServiceException;
import by.jwd.task6.service.ServiceException;
import by.jwd.task6.service.UserService;
import by.jwd.task6.validator.UserValidator;

public class UserServiceImpl implements UserService {
    
    private UserDao userDao = new UserDaoImpl();

    @Override 
    public void signUp(String name, String surname, String email, char[] password, UserRole role)
            throws ServiceException, LoginAlreadyTakenServiceException, InvalidInputServiceException {
        if (!UserValidator.isValidForSignUp(name, surname, email, password)) {
            throw new InvalidInputServiceException("invalid input data in fields name, surname, email or password ");
        }
        try {
            userDao.signUp(name, surname, email, password,  role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (EmailAlreadyTakenDaoException e) {
            throw new LoginAlreadyTakenServiceException(e);
        }
    }

    @Override
    public Optional<User> signIn(String email, char[] password) throws ServiceException, InvalidInputServiceException {
        Optional<User> userOptional = Optional.empty();
        if (!UserValidator.isValidForSignIn(email, password)) {
            throw new InvalidInputServiceException("invalid input data in field email or password ");
        }
        try {
            userOptional = userDao.signIn(email, password);   
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userOptional;
    }

    @Override
    public boolean isEmailAvailable(String email) throws ServiceException {
        try {
            boolean isAvailable = userDao.isEmailAvailable(email);
            return isAvailable;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
