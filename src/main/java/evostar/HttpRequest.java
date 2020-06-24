package evostar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private   String method;
    private   String uriPath;
    private   String uriParam;
    private   String userID;
    private   Map<String,String> cookie;

    private HttpRequest(){
        method = null;
        uriPath = null;
        uriParam = null;
        cookie = null;
        userID = null;
    }

    public String getUserID(){
        return userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getUriPath() {
        return uriPath;
    }

    public String getUriParam() {
        return uriParam;
    }

    public boolean hasCookie(){
        return cookie!=null;
    }

    public String  getCookie(String name){
        return  cookie.get(name);
    }

    /*一个典型的HTTP请求头长这样，我们只解析第一行 和 Cookie行（如果有的话）
      GET /login.html HTTP/1.1
      Host: 127.0.0.1
      Connection: keep-alive
      Cache-Control: max-age=0
      Accept: text/html,application/xhtml+xml,application/xml;q=0.9
      User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64)
      Accept-Encoding: gzip,deflate,sdch
      Accept-Language: en-US,en;q=0.8
      Cookie: pgv_pvi=9956446208; pbm_total_match_cookie_281589=1; has_js=1;
     */

     public static HttpRequest parseHttpRequest(InputStream input) throws IOException {
         HttpRequest httpRequest = (HttpRequest) TimeTable.applicationContext.getBean("httpRequest");
         BufferedReader in = new BufferedReader(new InputStreamReader(input));

         //第一行是 "请求方法 请求路径 http版本号"
         String inputLine = in.readLine();
         System.out.println("inputline : " + inputLine);
         // 分析客户请求的HTTP信息，分析出到底想访问哪个文件，
         String[] parts = inputLine.split(" ");
         // 第一部分是请求的方法 GET or POST ， 还有可能是HEAD 等等，这个项目中不考虑
         httpRequest.method = parts[0];

         // 第二部分是请求的URI，拆分为path和param
         String uri = parts[1];
         if(uri.split("\\?").length==2) {
             httpRequest.uriPath = parts[1].split("\\?")[0];
             httpRequest.uriParam = parts[1].split("\\?")[1];
         }else{
             httpRequest.uriPath = uri;
             httpRequest.uriParam = "";
         }

         try {
             int no =1;
             //作为一个简化的版本，忽略掉其他的请求头，直接找到Cookie部分
             while ((inputLine = in.readLine()) != null) {
                 parts = inputLine.split(" ");
                 String name = parts[0];
                 if(name.equals(""))
                     break;

                 if (name.equals("Cookie:")) {
                     Map cookie = new HashMap<String, String>();
                     for (int i = 1; i <= parts.length-1; i++) {
                         String[] cookie_pair = parts[i].split("=");
                         String cookie_name = cookie_pair[0];
                         String cookie_value = cookie_pair[1];
                         if(cookie_value.charAt(cookie_value.length()-1)==';')
                             cookie_value = cookie_value.substring(0,cookie_value.length()-1);

                         cookie.put(cookie_name, cookie_value);
                     }
                     httpRequest.cookie = cookie;
                 }
             }

         }catch (Exception e){
             e.printStackTrace();
         }

         return httpRequest;
     }


}
