package ru.hladobor.skilltesttasks.webservice.db;

import javax.naming.NamingException;
import java.sql.SQLException;

/**
 * Интерфейс для классов, реализующих работу с БД
 * Created by sever on 03.11.2016.
 */
public interface DBHandler {
    String getCreateTableDDL(String schemaName, String tableName) throws SQLException, NamingException;

    String getSelectDDL(String schemaName, String tableName) throws SQLException, NamingException;

    String getUpdateDDL(String schemaName, String tableName) throws SQLException, NamingException;
}
