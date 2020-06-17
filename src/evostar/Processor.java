package evostar;

import javax.print.attribute.HashPrintServiceAttributeSet;
import java.io.*;
import java.net.Socket;

/**
 * 处理一个HTTP用户请求的线程类。
 */
public class Processor extends Thread {
    private PrintStream out;
    private InputStream input;

    public Processor(Socket socket) {
        try {
            this.input = socket.getInputStream();
            this.out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            Response response = new Response(this.out);

            Service service = Service.getInstance();
            String uri = service.parse(input);
            if(uri==null)
                return;

            String route = uri.split("\\?")[0];
            String content = null;

            if(service.isServeLet(route)){//访问的是servlet方法
                content = service.callServeLet(uri);
                response.sendMessage(200, content);

            }else{//访问的是静态文件

                String fileName = "."+uri;
                File file = new File(fileName);
                if (file.exists()) {
                    response.sendOut(fileName);
                } else {
                    response.sendMessage(404, "File not found!");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}