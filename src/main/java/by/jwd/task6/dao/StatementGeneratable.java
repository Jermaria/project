package by.jwd.task6.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface StatementGeneratable {
    
    default PreparedStatement generatePreparedStatement(Connection connection, String sql,  Object...values) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        while(i < values.length) {
            statement.setObject(i+1, values[i]);  
            i++;
        }
        return statement;
    }
    
    // allows us to assign ResultSet variable in try-with-resources
    default ResultSet getGeneratedKeysAfterUpdate(PreparedStatement statement) throws SQLException {
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        return resultSet;
    }


}
