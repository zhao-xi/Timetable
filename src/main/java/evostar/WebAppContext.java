package evostar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.Time;
import java.util.concurrent.ConcurrentHashMap;

public class WebAppContext {

    private static ConcurrentHashMap<String,Object> controllerInstances = null;

    public static ConcurrentHashMap<String,String> sessions = null;

    private WebAppContext() {
        controllerInstances = new ConcurrentHashMap<String,Object>();
        sessions = new ConcurrentHashMap<String,String>();
        System.out.println("WebAppContext实例化了.........................");
    }


    public void setSession(String userID){
        String sessionID = ControllerHelper.toSessionID(userID);
        sessions.put(sessionID, userID);
        System.out.println("SessionID set userID="+userID+" sessionID="+sessionID );
    }

    public String getSession(String sessionID){
        System.out.println("SessionID get sessionID="+sessionID );
        String userID = sessions.get(sessionID);
        return userID;
    }
    public boolean isServeLet(String route) throws IOException, SAXException, ParserConfigurationException {
        String className = AppConfigHelper.getRouteMap(route);
        if(className==null)
            return false;
        else
            return true;
    }
    public void callServeLet(HttpRequest request, HttpResponse response){
        String route = request.getUriPath();
        String method = route.substring(1);
        try {
            Object object = TimeTable.applicationContext.getBean(route);
            object.getClass().getMethod(method, HttpRequest.class,
                    HttpResponse.class).invoke(object, request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}