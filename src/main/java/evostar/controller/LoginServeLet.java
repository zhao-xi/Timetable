package evostar.controller;

import evostar.*;
import evostar.dao.UserDAO;
import evostar.pojo.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginServeLet extends ServeLet {
    @Autowired
    private UserDAO userDAO;

    //需要参数 username password
    //登录成功后需要setCookie
    public void login(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, SAXException, ParserConfigurationException, IOException {
        HashMap map = new HashMap();

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

        User user = userDAO.getUserByUsername(username);
        System.out.println(user);

        if(user.getId() == 0){
            map.put("status",-1);
            map.put("msg","账号不存在");
        }else{
            if(user.getPassword().equals(ControllerHelper.md5(password))){
                map.put("status",1);
                map.put("msg","登录成功");
                map.put("studentId",user.getId());
                response.setCookie("sessionID", ControllerHelper.toSessionID(String.valueOf(user.getId())));
                webAppContext.setSession(String.valueOf(user.getId()));
            }else{
                map.put("status",-1);
                map.put("msg","密码错误");
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        response.setContent(jsonObject.toString());
    }

}
