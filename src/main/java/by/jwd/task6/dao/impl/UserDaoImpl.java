package by.jwd.task6.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.task6.dao.DaoException;
import by.jwd.task6.dao.EmailAlreadyTakenDaoException;
import by.jwd.task6.dao.StatementGeneratable;
import by.jwd.task6.dao.UserDao;
import by.jwd.task6.dao.impl.pool.ConnectionPool;
import by.jwd.task6.dao.impl.pool.ConnectionPoolException;
import by.jwd.task6.entity.User;
import by.jwd.task6.entity.UserRole;
import by.jwd.task6.util.PasswordManager;



public class UserDaoImpl implements UserDao, StatementGeneratable {

    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool pool = ConnectionPool.getInstance();
    
    private static final String ADD_USER_SQL = "INSERT INTO users (name, surname, email, password, salt, roles_id) "
                                            + "VALUES (?,?,?,?,?,?)";
    private static final String FIND_USER_BY_EMAIL_SQL = "SELECT id, name, surname, email, password, salt, isActive, roles_id FROM users WHERE email = ?";
    private static final String FIND_EMAIL_SQL = "SELECT email FROM users WHERE email = ?";

    @Override
    public void signUp(String name, String surname, String email, char[] password, UserRole role) throws DaoException, EmailAlreadyTakenDaoException {
        if(!isEmailAvailable(email)) {
            throw new EmailAlreadyTakenDaoException();
        }
        int roleIndex = role.getRoleIndex();
            
        try (Connection connection = pool.takeConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_USER_SQL);) {
            String salt = PasswordManager.generateSalt();
            Optional<String> saltedPasswordOptional = PasswordManager.generateSaltedPassword(password, salt);
            String saltedPassword = getSaltedPassword(saltedPasswordOptional);
            statement.setString(1, name);
            statement.setString(2, surname);      
            statement.setString(3, email);
            statement.setString(4, saltedPassword);
            statement.setString(5, salt);
            statement.setInt(6, roleIndex);
            statement.executeUpdate();
        } catch (ConnectionPoolException e) {
            throw new DaoException ("Failed to take connection from pool", e);
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException ("failed to create prepared statement or resultset", e);  
        }  
    }

    @Override
    public Optional<User> signIn(String email, char[] password) throws DaoException {
        Optional<User> userOptional = Optional.empty();
        try(Connection connection = pool.takeConnection();
            PreparedStatement statement = generatePreparedStatement(connection, FIND_USER_BY_EMAIL_SQL, email);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String salt = resultSet.getString(SQLColumnTitle.SALT);            
                String userPassword = resultSet.getString(SQLColumnTitle.SALTED_PASSWORD);
                Optional<String> enteredPasswordEncryptedOptional = PasswordManager.generateSaltedPassword(password, salt);
                String enteredPasswordEncrypted = getSaltedPassword(enteredPasswordEncryptedOptional);                
                if (enteredPasswordEncrypted.equals(userPassword)) {
                    User user = new User(resultSet.getInt(SQLColumnTitle.ID), resultSet.getString(SQLColumnTitle.NAME), 
                                         resultSet.getString(SQLColumnTitle.SURNAME), resultSet.getString(SQLColumnTitle.EMAIL), 
                                         UserRole.getRoleByIndex(resultSet.getInt(SQLColumnTitle.ROLES_ID)).get());
                    userOptional = Optional.of(user);
                    break;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException();
        } catch (ConnectionPoolException e1) {
            throw new DaoException();
        }
        return userOptional;
    }
    
    // опрадан ли здесь выброс DaoException из приватного метода?
    private String getSaltedPassword(Optional<String> password) throws DaoException {  // to avoid throwing from try block
        if (password.isEmpty()) {
            logger.log(Level.ERROR, "empty password optional");
            throw new DaoException("failed to generate salted password");
        }
        return password.get();
    }
    
    public boolean isEmailAvailable(String email) throws DaoException {
        boolean isAvailable;
        try (Connection connection = pool.takeConnection();
                    PreparedStatement statement = generatePreparedStatement(connection, FIND_EMAIL_SQL, email);
                    ResultSet resultSet = statement.executeQuery();) {
            isAvailable = !(resultSet.next());
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new DaoException();
        } catch (ConnectionPoolException e1) {
            throw new DaoException();
        }
        return isAvailable;
    }

}
