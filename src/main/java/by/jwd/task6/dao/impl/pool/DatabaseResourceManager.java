package by.jwd.task6.dao.impl.pool;

import java.util.ResourceBundle;

class DatabaseResourceManager {
    
    private static ResourceBundle bundle = ResourceBundle.getBundle("db");

    static String getValue(String key) {
        return bundle.getString(key);
    }

}
