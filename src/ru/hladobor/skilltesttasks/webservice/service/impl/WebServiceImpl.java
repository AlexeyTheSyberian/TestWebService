package ru.hladobor.skilltesttasks.webservice.service.impl;

import ru.hladobor.skilltesttasks.webservice.db.DBHandlerSelector;
import ru.hladobor.skilltesttasks.webservice.service.WebServiceInterface;

import javax.jws.WebService;

/**
 * Реализация интерфейса веб-сервиса
 * Created by sever on 02.11.2016.
 */
@WebService(endpointInterface = "ru.hladobor.skilltesttasks.webservice.service.WebServiceInterface")
public class WebServiceImpl implements WebServiceInterface {
    @Override
    public String getCreate(String schemaName, String tableName) {
        String result = "";
        try {
            result = DBHandlerSelector.getDBHandler().getCreateTableDDL(schemaName, tableName);
        } catch (Exception ex) {
            result = "Ошибка привыполнении запроса: " + ex.getMessage();
        }
        return result;
    }

    @Override
    public String getSelect(String schemaName, String tableName) {
        String result = "";
        try {
            result = DBHandlerSelector.getDBHandler().getSelectDDL(schemaName, tableName);
        } catch (Exception ex) {
            result = "Ошибка привыполнении запроса: " + ex.getMessage();
        }
        return result;
    }

    @Override
    public String getUpdate(String schemaName, String tableName) {
        String result = "";
        try {
            result = DBHandlerSelector.getDBHandler().getUpdateDDL(schemaName, tableName);
        } catch (Exception ex) {
            result = "Ошибка привыполнении запроса: " + ex.getMessage();
        }
        return result;
    }
}
