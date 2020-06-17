package evostar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class DBConfigHelper {
    private static HashMap<String,String> dbconf = null;

    private static void configDatabase(String databaseFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(databaseFile);

        dbconf = new HashMap<String, String>();
        NodeList dbinfo = doc.getElementsByTagName("DBInfo");

        NodeList confs = dbinfo.item(0).getChildNodes();

        for(int i=0;i< confs.getLength();i++){
            Node node = confs.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE){
                Element elem =(Element)node;
                //配置文件数据信息
                System.out.println(elem.getNodeName()+"--"+elem.getTextContent());
                dbconf.put(elem.getNodeName(),elem.getTextContent());
            }
        }
    }

    public static void init(String databaseFile) throws IOException, SAXException, ParserConfigurationException {
        configDatabase(databaseFile);
    }

    public static String getConfig(String key){
        return dbconf.get(key);
    }
}
