package by.jwd.task6.dao.impl.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
    
    private static final Logger logger = LogManager.getLogger();
    private static ConnectionPool instance = new ConnectionPool();
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isInitialized = new AtomicBoolean();
    
    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> givenAwayConnections;
    
    private static final int DEFAULT_POOL_SIZE = 5;
    private int poolSize;
    
    private String driverName;
    private String url;
    private String user;
    private String password;
    
    private ConnectionPool() {
        initDriverProperties();
        initConnectionQueues();
    }
    
    private void initDriverProperties() {
        this.driverName = DatabaseResourceManager.getValue(DatabaseParameter.DB_DRIVER);
        this.url = DatabaseResourceManager.getValue(DatabaseParameter.DB_URL);
        this.user = DatabaseResourceManager.getValue(DatabaseParameter.DB_USER);
        this.password = DatabaseResourceManager.getValue(DatabaseParameter.DB_PASSWORD);
        try {
            this.poolSize  = Integer.parseInt(DatabaseResourceManager.getValue(DatabaseParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            poolSize = DEFAULT_POOL_SIZE;
        }
    }
    
    private void initConnectionQueues() {
        try {
            Class.forName(driverName);
            freeConnections = new LinkedBlockingDeque<>(poolSize);
            givenAwayConnections = new ArrayDeque<>(poolSize);
            
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            logger.log(Level.FATAL, e);
            throw new ExceptionInInitializerError(e);   // замапить на отдельную страницу (СВЕРСТААААТЬ!)
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static ConnectionPool getInstance() {
        if (isInitialized.compareAndSet(false, true)) {
            try {
                lock.lock();
                if (isInitialized.compareAndSet(false, true)) {
                    instance = new ConnectionPool();
                }
                isInitialized.set(true);
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            throw new ConnectionPoolException ("Error connecting to the data source.", e);
        }
        return connection;
    }
    
    void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (connection.getClass() != ProxyConnection.class) { 
            logger.log(Level.ERROR, "attempt to release alien connection");
            throw new ConnectionPoolException("attempt to release alien connection");
        } 
        givenAwayConnections.remove(connection);
        freeConnections.offer(connection);
    }
    
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                ((ProxyConnection)freeConnections.take()).reallyClose();   // будет ли каститься?
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e);
            }
        }
        deregisterDrivers();
    }
    
    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        });
    }
    
    public void closeConnection(Connection con) {  // позже убрать, всё есть в CloseResourceDao
        try {
            if (con != null) {
                con.close();
            }
        } catch(SQLException e) {
            logger.log(Level.ERROR, "Connection isn't returned to the pool.");
        }
    }

    
     

}
