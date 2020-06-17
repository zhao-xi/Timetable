package evostar;

import java.io.*;

public class Response {
    private PrintStream out;

    public Response(PrintStream out){
        this.setOut(out);
    }
    public void setOut(PrintStream out) {
        this.out = out;
    }

    /**
     * 返回客户端html文件
     */
    public void sendOut(String fileName) throws IOException {
        File f = new File(fileName);
        FileInputStream fis = new FileInputStream(f);
        BufferedInputStream bis = new BufferedInputStream(fis);
        int len = 0;
        byte[] temp = new byte[1024];
        String content = "";
        while ((len = bis.read(temp)) != -1) {
            content += new String(temp, 0, len);
        }
        System.out.println();
        out.println("HTTP/1.0 " + 200 + " " + "OK");
        out.println("Content-type:text/html");
        out.println();
        out.println(content);
        out.println();
        out.flush();
        out.close();
    }

    /**
     * 返回给客户端文字数据
     */
    public void sendMessage(int state, String content) {
        out.println("HTTP/1.0 " + state + " " + content);
        out.println("Content-type:text/html;charset=utf-8");
        out.println();
        out.println(content);
        out.println();
        out.flush();
        out.close();
        System.out.println(state+","+content);
    }
}
