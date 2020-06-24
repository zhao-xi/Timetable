package evostar;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    /**
     * 默认使用的服务器Socket端口号
     */
    private ServerSocket serverSocket;

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try {
            System.out.println("Web Server startup  ");
            while (true) {
                Socket socket = serverSocket.accept();
                //通过线程的方式来处理客户的请求
                Processor processor = (Processor) TimeTable.applicationContext.getBean("processor");
                processor.setSocket(socket);
                processor.start();
                //new Processor().start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




