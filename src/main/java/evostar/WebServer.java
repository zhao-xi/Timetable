package evostar;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    /**
     * 默认使用的服务器Socket端口号
     */
    private ServerSocket serverSocket;

    public void startServer(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Web Server startup on  " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                // 通过线程的方式来处理客户的请求
                new Processor(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}