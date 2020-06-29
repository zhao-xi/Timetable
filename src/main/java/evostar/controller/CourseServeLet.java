package evostar.controller;

import evostar.*;
import evostar.dao.CourseListDAO;
import evostar.dao.SelectedCourseDAO;
import evostar.mapper.CourseListMapper;
import evostar.mapper.SelectedCourseMapper;
import evostar.pojo.CourseList;
import evostar.pojo.SelectedCourse;
import evostar.service.CourseListService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseServeLet extends ServeLet {
    @Autowired
    private CourseListService courseListService;
    @Autowired
    private SelectedCourseMapper selectedCourseMapper;

    //参数studentId
    //获取所有课程数据
    public void courselist(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
        Map<String, Object> map = new HashMap<>();
        String studentId = request.getUserID();
        System.out.println(studentId);
        List<CourseList> courseList = courseListService.getCourseList(Integer.parseInt(studentId));

        map.put("status",1);
        map.put("msg","获取成功");
        List<String> list = new ArrayList<>();
        for(CourseList course : courseList){
            String[] courseArr = {String.valueOf(course.getId()), course.getName(), String.valueOf(course.getDay()),
            String.valueOf(course.getBegin()), course.getTeacher().getName(), String.valueOf(course.getSelected())};
            JSONArray jsonObject = JSONArray.fromObject(courseArr);
            list.add(jsonObject.toString());
        }
        map.put("data", list);
        JSONObject jsonObject = JSONObject.fromObject(map);
        response.setContent(jsonObject.toString());
        return;
    }

    //选课
    //参数 state courseId
    @Transactional
    public Map changeSelected(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException {

        Map<String, Object> map = new HashMap<>();
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
        SelectedCourse selectedCourse = new SelectedCourse();
        selectedCourse.setCourse_id(courseId);
        selectedCourse.setStudent_id(Integer.parseInt(studentId));

        if(state == 1){
            //删除操作
            selectedCourseMapper.delete(selectedCourse);
            courseListService.decrease_total_num(courseId);
        }else{
            //添加操作
            selectedCourseMapper.insert(selectedCourse);
            courseListService.increase_total_num(courseId);
        }

        //执行成功
        map.put("status",1);
        map.put("msg","操作成功");
        map.put("courseId",courseId);
        map.put("state",state);

        JSONObject jsonObject = JSONObject.fromObject(map);
        response.setContent(jsonObject.toString());
        return map;
    }

    //返回我选中的课程数据
    //studentID由cookie中的sessionID查找得来
    public void selectedCourse(HttpRequest request, HttpResponse response) throws SQLException, ClassNotFoundException, ParserConfigurationException, SAXException, IOException {
        Map<String, Object> map = new HashMap<>();
        String studentId;

        studentId = request.getUserID();

        List<SelectedCourse> list =selectedCourseMapper.getSelectedCourse(Integer.parseInt(studentId));
        String[][] selected = new String[5][6];

        for(SelectedCourse course : list) {
            int day = course.getCourseList().getDay();
            int begin = course.getCourseList().getBegin();
            String name = course.getCourseList().getName();
            String teacherName = course.getTeacher().getName();
            String classroom = course.getClassRoom().getName();
            selected[day - 1][begin - 1] = name + "@" + teacherName + "@" + classroom;
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
