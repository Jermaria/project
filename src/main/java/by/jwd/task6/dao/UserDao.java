package by.jwd.task6.dao;

import java.util.Optional;

import by.jwd.task6.entity.User;
import by.jwd.task6.entity.UserRole;

public interface UserDao {
    
    public void signUp(String name, String surname, String email, char[] password, UserRole role)
            throws DaoException, EmailAlreadyTakenDaoException;
    
    public Optional<User> signIn(String email, char[] password) throws DaoException;
    
    public boolean isEmailAvailable(String email) throws DaoException;

}
