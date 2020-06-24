package evostar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TimeTable {
    public static ApplicationContext applicationContext;

    public static void main(String[] args){
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        WebServer server = (WebServer) applicationContext.getBean("webServer");
        server.startServer();
    }
}