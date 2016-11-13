package ru.hladobor.skilltesttasks.webservice.db;

import ru.hladobor.skilltesttasks.webservice.db.impl.DBHandlerEmptyImpl;
import ru.hladobor.skilltesttasks.webservice.db.impl.DBHandlerMySQLImpl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс, позволяющий выби рать реализацию интерфейса {@link DBHandler} в зависимости от используемой БД
 * Created by sever on 04.11.2016.
 */
public class DBHandlerSelector {
    public static DBHandler getDBHandler() throws NamingException, SQLException {
        InitialContext initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/TestDB");
        DBHandler result = new DBHandlerEmptyImpl();
        try(Connection conn = ds.getConnection()){
            String dbName = conn.getMetaData().getDatabaseProductName();
            if(dbName.toLowerCase().contains("mysql")){
                result = new DBHandlerMySQLImpl();
            }
        }
        return result;
    }
}
