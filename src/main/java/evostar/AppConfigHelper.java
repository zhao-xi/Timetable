package evostar;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class AppConfigHelper {

    private static String appContextPath = "src/main/resources/applicationContext.xml";
    private static HashMap<String,String> routeMap=null;


    private static synchronized void  initRouteMap(String XMLPath) throws IOException, SAXException, ParserConfigurationException {
        if(routeMap!=null)
            return;

        routeMap = new HashMap<String,String>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(XMLPath);

        NodeList beanList  = doc.getElementsByTagName("bean");

        for(int i=0;i<beanList.getLength();i++){
            Node beanNode = beanList.item(i);
            if(beanNode.getNodeType()==Node.ELEMENT_NODE){
                Element elem =(Element)beanNode;
                routeMap.put(elem.getAttribute("name"), elem.getAttribute("class"));

                }
        }
    }


    public static String getRouteMap(String route) throws ParserConfigurationException, SAXException, IOException {
        initRouteMap(appContextPath);
        return routeMap.get(route);
    }


}
