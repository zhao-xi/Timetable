package evostar.aspect;

import evostar.Log;
import evostar.TimeTable;
import evostar.dao.CourseListDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CourseAspect {
    @Autowired
    private CourseListDAO courseListDAO;

    //使用后置增强，把学生的选课操作记录保存在txt文件中
    public void record(Map result) throws Throwable {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String currentTime = df.format(new Date());
        Integer status = (Integer) result.get("status");
        String msg = result.get("msg").toString();
        String record = "";
        if (status==1){
            //选课成功
            Integer courseId = (Integer) result.get("courseId");
            Integer state = (Integer) result.get("state");
            String selectType = state==1 ? "添加课程" : "删除课程";
            String name = courseListDAO.getCourseNameById(courseId);
            record = currentTime+ " "+selectType+" "+name;
        }else{
            //选课失败
            record = currentTime+" "+msg;
        }
        Log log = (Log) TimeTable.applicationContext.getBean("log");
        log.wrirteIn(record);
    }
}
