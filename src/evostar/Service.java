package evostar;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class Service {
    private static Service service = null;

    private static ConcurrentHashMap<String,Object> controllerInstances = null;


    private Service() {
        controllerInstances = new ConcurrentHashMap<String,Object>();

    }

    public static Service getInstance(){
        if(service == null){
            synchronized(Service.class){
                if (service == null) {
                    service = new Service();
                }
            }
        }
        return service;
    }

    public boolean isServeLet(String route) throws IOException, SAXException, ParserConfigurationException {

        String className = AppConfigHelper.getRouteMap(route);
        if(className==null)
            return false;
        else
            return true;
    }


    public String  callServeLet(String uri) throws Exception {
        String route = uri.split("\\?")[0];

        String className = AppConfigHelper.getRouteMap(route);

        Class newClass = Class.forName(className);
        Object classInstance = null;

        synchronized (this.getClass()) {
            if ((classInstance = controllerInstances.get(className)) == null) {
                classInstance = newClass.newInstance();
                controllerInstances.put(className, classInstance);
            }
        }

        return (String) newClass.getMethod("doGet",String.class).invoke(classInstance, uri);

    }


    /**
     * 解析客户机发过的所有HTTP请求，如果是符合HTTP协议内容的， 就分析出客户机所要访问文件的名字，并且返回。
     */
    public static String parse(InputStream input) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String inputContent = in.readLine();
        if(inputContent==null)
            return null;
        System.out.print(inputContent);
        // 分析客户请求的HTTP信息，分析出到底想访问哪个文件，
        String request[] = inputContent.split(" ");
        // 第一部分是请求的方法
        String method = request[0];
        // 第二部分是请求的文件名
        String param = request[1];
        // 第三部分是HTTP版本号
        String httpVersion = request[2];
        System.out.println("request:");
        System.out.println(Arrays.deepToString(request));
        System.out.println("Method: " + method + ", file name: " + param + ", HTTP version: " + httpVersion);
        return param;
    }


}
