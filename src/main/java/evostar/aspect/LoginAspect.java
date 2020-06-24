package evostar.aspect;

import evostar.HttpRequest;
import evostar.HttpResponse;
import evostar.WebAppContext;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class LoginAspect {
    private WebAppContext webAppContext;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    public void setWebAppContext(WebAppContext webAppContext) {
        this.webAppContext = webAppContext;
    }
    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
    //增强，用来验证用户的登录信息
    public Object checkSession(ProceedingJoinPoint joinPoint) throws Throwable {
        if(httpRequest.hasCookie() && httpRequest.getCookie("sessionID")!=null) {
            String sessionID = httpRequest.getCookie("sessionID");
            String userID = this.webAppContext.getSession(sessionID);
            if (userID != null) {
                httpRequest.setUserID(userID);
                return joinPoint.proceed();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status",-1);
        map.put("msg","无访问权限，请重新登录");
        JSONObject jsonObject = JSONObject.fromObject(map);
        httpResponse.setContent(jsonObject.toString());
        return null;
    }
}
