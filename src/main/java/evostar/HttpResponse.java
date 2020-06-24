package evostar;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpResponse {
    private PrintStream out;
    private Map<String,String> cookie;
    private String content;

    public void init(PrintStream out){
        this.out = out;
        cookie = new HashMap<String,String>();
        content = null;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setCookie(String name, String value){
        cookie.put(name,value);
    }


    public void sendStaticFile(String fileName) throws IOException {
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

    private String dateTime(){
        String dateStr = null;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat greenwichDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        greenwichDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.add(cal.DATE, 1);
        dateStr = greenwichDate.format(cal.getTime());

        return dateStr;
    }

    /**
     * 返回给客户端文字数据
     */
    public void sendMessage(int state) {
        out.println("HTTP/1.0 " + state );
        out.println("Content-type:text/html;charset=utf-8");
        if(!cookie.isEmpty()){
            String cookieStr = "Set-Cookie: ";
            for(Map.Entry<String,String> entry : cookie.entrySet()){
                cookieStr += entry.getKey()+"="+entry.getValue()+"; ";
            }
            out.println(cookieStr);
            out.println("Expires："+dateTime());
        }
        out.println();
        out.println(content);
        out.flush();
        out.close();
        System.out.println(state+","+content);
    }

    /*重定向*/
    public void redirectTo(String uri) {
        out.println("HTTP/1.0 302 Moved Temporarily");
        out.println("Location: "+uri);
        out.println();
        out.flush();
        System.out.println("Redirect to "+uri);
    }

}
