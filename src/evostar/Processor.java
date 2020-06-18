package evostar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.sql.Time;

/**
 * 处理一个HTTP用户请求的线程类。
 */
public class Processor extends Thread {
    private PrintStream out;
    private InputStream input;
    private Socket socket;

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.init();
    }
    public void init() throws IOException {
        this.input = socket.getInputStream();
        this.out = new PrintStream(socket.getOutputStream());
    }

    public void run(){

        try {
            WebAppContext webAppContext = (WebAppContext) TimeTable.applicationContext.getBean("webAppContext");

            HttpRequest request = (HttpRequest) TimeTable.applicationContext.getBean("httpRequest");
            request.parseHttpRequest(input);
            String route = request.getUriPath();
            String content = null;

            TimeTable.applicationContext.getBean("httpResponse");
            HttpResponse response = (HttpResponse) TimeTable.applicationContext.getBean("httpResponse");
            response.init(out);

            if(webAppContext.isServeLet(route)){//访问的是servlet方法
                webAppContext.callServeLet(request,response);
                response.sendMessage(200);
            }else{
                //访问的是静态文件,这个地方理论上也应该加上访问权限检查,但对login.html相关的资源需要进行特殊处理，正式的框架里面会有过滤器来做
                String fileName = "."+request.getUriPath();
                File file = new File(fileName);
                if (file.exists()) {
                    response.sendStaticFile(fileName);
                } else {
                    response.setContent("File not found");
                    response.sendMessage(404);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}