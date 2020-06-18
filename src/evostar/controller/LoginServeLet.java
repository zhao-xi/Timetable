package evostar.controller;

import evostar.*;
import net.sf.json.JSONObject;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginServeLet extends ServeLet {
    //需要参数 username password
    //登录成功后需要setCookie
    public void login(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, SAXException, ParserConfigurationException, IOException {
        HashMap  map = new HashMap();

        if(!ControllerHelper.checkParam(new String[]{"username", "password"},request.getUriParam())){
            map.put("status",0);
            map.put("msg","参数不全");
            JSONObject jsonObject = JSONObject.fromObject(map);
            response.setContent(jsonObject.toString());
            return ;
        }

        HashMap<String,String> param = ControllerHelper.args(request.getUriParam());
        String username = param.get("username");
        String password = param.get("password");
        String sql = "select * from user inner join student on user.student_id = student.id where user.username='"+username+"' and user.type=1";
        System.out.println(sql);
        List result = mysqlManage.select(sql, new String[]{"user.student_id","username","password"});
        if(result.size() == 0){
            map.put("status",-1);
            map.put("msg","账号不存在");
        }else{
            String[] user = (String[]) result.get(0);
            System.out.println(ControllerHelper.md5(password));
            if(user[2].equals(ControllerHelper.md5(password))){
                map.put("status",1);
                map.put("msg","登录成功");
                map.put("studentId",user[0]);
                response.setCookie("sessionID", ControllerHelper.toSessionID(user[0]));
                webAppContext.setSession(user[0]);
            }else{
                map.put("status",-1);
                map.put("msg","密码错误");
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        response.setContent(jsonObject.toString());
    }

}
