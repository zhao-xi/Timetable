package evostar;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MysqlManage {
    /*Mysql版本8.0+的使用Driver驱动为：com.mysql.cj.jdbc.Driver，并且需要自行加载Jar包
    Mysql版本低于8.0的使用Driver驱动为：com.mysql.jdbc.Driver*/
    final static private String databaseFile = "src/databases.xml";
    private String JDBC_DRIVER;
    private Connection conn;
    private Statement stmt;
    private ResultSet res;
    private String DB_URL;
    private String USER;
    private String PASS;
    private String DB_NAME;

    public MysqlManage() throws ClassNotFoundException, SQLException, IOException, SAXException, ParserConfigurationException {
        DBConfigHelper.init(databaseFile);

        //获取文件数据库配置信息并赋值
        System.out.println(DBConfigHelper.getConfig("User"));
        this.USER = DBConfigHelper.getConfig("User");
        this.PASS = DBConfigHelper.getConfig("Password");
        this.JDBC_DRIVER = DBConfigHelper.getConfig("Driver");
        this.DB_NAME = DBConfigHelper.getConfig("DB_name");
        String URL = DBConfigHelper.getConfig("url")+"/"+ DBConfigHelper.getConfig("DB_name");
        this.DB_URL = URL;
        // 注册 JDBC 驱动
        System.out.println("this.JDBC_DRIVER"+this.JDBC_DRIVER);
        Class.forName(this.JDBC_DRIVER);
        // 打开链接
        System.out.println("连接数据库...");
        this.conn = DriverManager.getConnection(this.DB_URL,USER,PASS);
        // 执行查询
        System.out.println(" 实例化Statement对象...");
        this.stmt = this.conn.createStatement();
    }

    public List select(String sql,String[] field) throws SQLException {
        System.out.println("sql语句:"+sql);
        ArrayList <String[]> list = new ArrayList<String[]>();
        this.res = this.stmt.executeQuery(sql);
        int count = field.length;
        // 展开结果集数据库
        while(this.res.next()){
            String data[] = new String[count];
            for(int i = 0;i<field.length;i++){
                data[i] = this.res.getString(field[i]);
            }
            list.add(data);
        }

        return list;
    }
    //查询单个数据
    public String select(String sql,String field) throws SQLException {
        String[] newField = {field};
        List list = this.select(sql,newField);
        if(list.size() == 0){
            return null;
        }
        String[] result = (String[]) list.get(0);
        return result[0];
    }

    public int update(String sql, Class<String> stringClass, int courseId) throws SQLException {
        System.out.println(sql);
        int num = this.stmt.executeUpdate(sql);
        return num;
    }

    public int delete(String sql) throws SQLException {
        System.out.println(sql);
        int num = this.stmt.executeUpdate(sql);
        return num;
    }

    public int insert(String sql) throws SQLException {
        System.out.println(sql);
        int num = this.stmt.executeUpdate(sql);
        return num;
    }

    public void close() throws SQLException {
        this.res.close();
        this.stmt.close();
        this.conn.close();
    }
}