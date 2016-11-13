package ru.hladobor.skilltesttasks.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Интерфейс веб-сервиса
 * Created by sever on 02.11.2016.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface WebServiceInterface {
    @WebMethod
    String getCreate(String schemaName, String tableName);
    @WebMethod
    String getSelect(String schemaName, String tableName);
    @WebMethod
    String getUpdate(String schemaName, String tableName);
}
