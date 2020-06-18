package evostar.controller;

import evostar.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseServeLet extends ServeLet {
    //参数studentId
    //获取所有课程数据

    public void courselist(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String studentId = request.getUserID();
        String sql = "select * from course_list left join (select * from selected_course where student_id = "+studentId+") as myselected on course_list.id = myselected.course_id left join teacher on course_list.teacher_id = teacher.id order by course_list.id asc";
        List result = mysqlManage.select(sql, new String[]{"id","name","day","begin","teacher.name","student_id"});
        map.put("status",1);
        map.put("msg","获取成功");
        List<String> list=new ArrayList<String>();
        for(int i=0;i<result.size();i++){
            String[] course = (String[]) result.get(i);
            JSONArray jsonObject = JSONArray.fromObject(course);
            list.add(jsonObject.toString());
        }
        map.put("data",list);
        JSONObject jsonObject = JSONObject.fromObject(map);

        response.setContent(jsonObject.toString());
        return;
    }

    //选课
    //参数 state courseId
    public Map changeSelected(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException {

        Map<String, Object> map = new HashMap<String, Object>();
        if(!ControllerHelper.checkParam(new String[]{"state","courseId"},request.getUriParam())){
            map.put("status",0);
            map.put("msg","参数不全");
            JSONObject jsonObject = JSONObject.fromObject(map);
            response.setContent(jsonObject.toString());
            return map;
        }
        HashMap<String,String> param = ControllerHelper.args(request.getUriParam());
        int courseId = Integer.parseInt(param.get("courseId"));
        int state = Integer.parseInt(param.get("state"));
        String studentId = request.getUserID();

        String sql = "";

        int num = 0;

        if(state == 1){
            //删除操作
            sql = "delete from selected_course where course_id = "+courseId + " and student_id="+studentId;
            num = mysqlManage.delete(sql);

        }else{
            //添加操作
            sql = "insert into selected_course (course_id,student_id) values ("+courseId+","+studentId+")";
            num = mysqlManage.insert(sql);
        }

        if(num > 0){
            //执行成功
            map.put("status",1);
            map.put("msg","操作成功");
            map.put("courseId",courseId);
            map.put("state",state);
        }else{
            //执行失败
            map.put("status",-1);
            map.put("msg","操作失败，请重试");
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        response.setContent(jsonObject.toString());
        return map;
    }

    //返回我选中的课程数据
    //studentID由cookie中的sessionID查找得来
    public void selectedCourse(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String studentId;

        studentId = request.getUserID();

        String sql = "select * from selected_course left join course_list on selected_course.course_id = course_list.id left join teacher on course_list.teacher_id = teacher.id left join classroom on course_list.room_id = classroom.room_id where selected_course.student_id="+studentId;
        List result = mysqlManage.select(sql, new String[]{"course_list.id","course_list.name","course_list.day","course_list.begin","teacher.name","classroom.name"});
        String[][] selected = new String[5][6];
        for(int i=0;i<selected.length;i++){
            for(int n=0;n<selected[i].length;n++){
                selected[i][n] = "";
            }
        }
        for(int i=0;i<result.size();i++){
            String[] course = (String[]) result.get(i);
            int day = Integer.parseInt(course[2]);
            int begin = Integer.parseInt(course[3]);
            String name = course[1];
            String teacherName = course[4];
            String classRoom = course[5];
            selected[day-1][begin-1] = name+"@"+teacherName+"@"+classRoom;
        }
        JSONArray data = JSONArray.fromObject(selected);
        map.put("status",1);
        map.put("msg","获取成功");
        map.put("data",data.toString());
        JSONObject jsonObject = JSONObject.fromObject(map);

        response.setContent(jsonObject.toString());
        return;
    }

}
