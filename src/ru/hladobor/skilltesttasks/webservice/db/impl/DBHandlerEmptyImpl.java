package ru.hladobor.skilltesttasks.webservice.db.impl;

import ru.hladobor.skilltesttasks.webservice.db.DBHandler;

import javax.naming.NamingException;
import java.sql.SQLException;

/**
 * реализация интерфейса {@link DBHandler} в случае, когда в контексте используется неподдерживаемая БД
 * Created by sever on 04.11.2016.
 */
public class DBHandlerEmptyImpl implements DBHandler {
    @Override
    public String getCreateTableDDL(String schemaName, String tableName) throws SQLException, NamingException {
        throw new SQLException("База данных не поддерживается");
    }

    @Override
    public String getSelectDDL(String schemaName, String tableName) throws SQLException, NamingException {
        throw new SQLException("База данных не поддерживается");
    }

    @Override
    public String getUpdateDDL(String schemaName, String tableName) throws SQLException, NamingException {
        throw new SQLException("База данных не поддерживается");
    }
}
