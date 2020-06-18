package evostar.controller;

import evostar.HttpRequest;
import evostar.HttpResponse;
import evostar.MysqlManage;
import evostar.WebAppContext;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ServeLet {
    protected WebAppContext webAppContext;
    protected MysqlManage mysqlManage;

    public void setWebAppContext(WebAppContext webAppContext) {
        this.webAppContext = webAppContext;
    }
    public void setMysqlManage(MysqlManage mysqlManage) {
        this.mysqlManage = mysqlManage;
    }
}
