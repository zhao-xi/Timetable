package evostar.dao;

import evostar.pojo.CourseList;
import evostar.pojo.Teacher;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseListDAO extends JdbcDaoSupport {
    public List<CourseList> getCourseList(int studentId){
        List<CourseList> list = this.getJdbcTemplate().query("" +
                "        select " +
                "            course_list.id," +
                "            course_list.name," +
                "            course_list.day," +
                "            course_list.begin," +
                "            teacher.name as teacher_name," +
                "            myselected.id as selected" +
                "        from" +
                "            course_list" +
                "        left join" +
                "            (select * from selected_course where student_id = ?) as myselected" +
                "        on" +
                "            course_list.id = myselected.course_id" +
                "        left join" +
                "            teacher" +
                "        on" +
                "            course_list.teacher_id = teacher.id" +
                "        order by course_list.id asc",new CourseListMapper(),studentId);
        return list;
    }
    static class CourseListMapper implements RowMapper<CourseList> {
        @Override
        public CourseList mapRow(ResultSet resultSet, int i) throws SQLException {
            System.out.println(resultSet.getString("name"));
            CourseList courseList = new CourseList();
            courseList.setId(resultSet.getInt("id"));
            courseList.setName(resultSet.getString("name"));
            courseList.setDay(resultSet.getInt("day"));
            courseList.setBegin(resultSet.getInt("begin"));
            Teacher teacher = new Teacher();
            teacher.setName(resultSet.getString("teacher_name"));
            courseList.setTeacher(teacher);
            courseList.setSelected(resultSet.getInt("selected"));
            return courseList;
        }
    }

    public String getCourseNameById(int courseId){
        return this.getJdbcTemplate().queryForObject("select `name` from course_list where id=?",String.class,courseId);
    }

    //课程订阅人数增加
    public void increase_total_num(int courseId){
        this.getJdbcTemplate().update("update course_list set total_num = total_num + 1 where id = ?",courseId);
    }
    //课程订阅人数减少
    public void decrease_total_num(int courseId){
        this.getJdbcTemplate().update("update course_list set total_num = total_num - 1 where id = ?",courseId);
    }
}