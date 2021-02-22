package by.jwd.task6.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface CloseDaoResource {
    
    default void close(Connection connection, Statement statement, ResultSet... resultSet)  {
        Logger logger = LogManager.getLogger();
        try {
            if (resultSet.length > 0 && resultSet[0] != null) {
                resultSet[0].close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }
    
    default void close(Statement statement, ResultSet...resultSet) {
        Logger logger = LogManager.getLogger();
        try {
            if (resultSet.length > 0 && resultSet[0] != null) {
                resultSet[0].close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
        }
    }
    
    default void close(Connection connection) {
        Logger logger = LogManager.getLogger();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }
}
