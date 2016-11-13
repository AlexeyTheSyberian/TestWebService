package ru.hladobor.skilltesttasks.webservice.db.impl;

import javafx.util.Pair;
import ru.hladobor.skilltesttasks.webservice.db.DBHandler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация интерфейса {@link DBHandler} для БД MySQL
 * Created by sever on 04.11.2016.
 */
public class DBHandlerMySQLImpl implements DBHandler {
    private static final String CREATE_TABLE_DDL_QUERY = "SHOW CREATE TABLE ";
    private static final String COLUMNS_FROM_TABLE_QUERY = "SHOW COLUMNS FROM ";

    private Connection getConnection() throws NamingException, SQLException {
        InitialContext initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/TestDB");
        Connection conn = ds.getConnection();
        return conn;
    }

    private List<Pair<String, String>> getColumnsInfo(ResultSet rs) throws SQLException {
        List<Pair<String, String>> result = new ArrayList<>();
        while (rs.next()) {
            Pair<String, String> pair = new Pair<>(rs.getString(1), rs.getString("Key"));
            result.add(pair);
        }
        return result;
    }

    public String buildQuery(String schemaName, String tableName, List<Pair<String, String>> columns, boolean update) {
        StringBuilder result = new StringBuilder();
        StringBuilder selectFields = new StringBuilder();
        StringBuilder whereClause = new StringBuilder();
        for (Pair<String, String> pair : columns) {
            if (selectFields.length() > 0) {
                if (update) {
                    selectFields.append("=?");
                }
                selectFields.append(", ");
            }
            selectFields.append(pair.getKey());
            if (pair.getValue().equalsIgnoreCase("PRI")) {
                if (whereClause.length() > 0) {
                    whereClause.append(" AND ");
                }
                whereClause.append(pair.getKey()).append("=?");
            }
        }
        if (update) {
            result.append("UPDATE ").append(schemaName).append(".").append(tableName).append(" SET ");
            result.append(selectFields).append("=? WHERE ").append(whereClause);
        } else {
            result.append("SELECT ").append(selectFields).append(" FROM ").append(schemaName).append(".").append(tableName).append(" ");
            result.append(" WHERE ").append(whereClause);
        }
        return result.toString();
    }

    @Override
    public String getCreateTableDDL(String schemaName, String tableName) throws SQLException, NamingException {
        String result = "";
        try (Connection conn = getConnection()) {
            PreparedStatement pst = conn.prepareStatement(CREATE_TABLE_DDL_QUERY + schemaName + "." + tableName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getNString(2);
            }
        }
        return result;
    }

    @Override
    public String getSelectDDL(String schemaName, String tableName) throws SQLException, NamingException {
        String result = "";
        try (Connection conn = getConnection()) {
            PreparedStatement pst = conn.prepareStatement(COLUMNS_FROM_TABLE_QUERY + schemaName + "." + tableName);
            ResultSet rs = pst.executeQuery();
            List<Pair<String, String>> columns = getColumnsInfo(rs);
            result = buildQuery(schemaName, tableName, columns, false);
        }
        return result;
    }

    @Override
    public String getUpdateDDL(String schemaName, String tableName) throws SQLException, NamingException {
        String result = "";
        try (Connection conn = getConnection()) {
            PreparedStatement pst = conn.prepareStatement(COLUMNS_FROM_TABLE_QUERY + schemaName + "." + tableName);
            ResultSet rs = pst.executeQuery();
            List<Pair<String, String>> columns = getColumnsInfo(rs);
            result = buildQuery(schemaName, tableName, columns, true);
        }
        return result;
    }
}
