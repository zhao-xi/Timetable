package evostar.controller;

import evostar.WebAppContext;

public abstract class ServeLet {
    protected WebAppContext webAppContext;
    public void setWebAppContext(WebAppContext webAppContext) {
        this.webAppContext = webAppContext;
    }
}
