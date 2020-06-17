package evostar;

public class TimeTable {
    private final static int port = 8080;
    public static void main(String[] args){
        WebServer server = new WebServer();
        server.startServer(port);
    }
}