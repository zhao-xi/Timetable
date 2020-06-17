package evostar.servelet;

import java.lang.reflect.InvocationTargetException;

public abstract class ServeLet {
    public  String doGet(String uri){
            String method;
            String param;
            String content=null;

            method = uri.split("\\?")[0];
            param = uri.split("\\?")[1];

            //去掉方法前面的'/'
            method = method.substring(1);

            Class clazz = this.getClass();
            try {
                System.out.println("Method name:"+method);
                content = (String)clazz.getMethod(method,String.class).invoke(this, param);
            }catch (NoSuchMethodException e){
                e.printStackTrace();
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return content;
    }
}
