package evostar.dao;

import evostar.pojo.SelectedCourse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SelectedCourseDAO extends JdbcDaoSupport {
    public void insert(int courseId, int studentId) {
        assert this.getJdbcTemplate() != null;
        this.getJdbcTemplate().update("insert into selected_course (course_id,student_id) values(?,?)", courseId, studentId);
    }
    public void delete(int courseId, int studentId) {
        assert this.getJdbcTemplate() != null;
        this.getJdbcTemplate().update("delete from selected_course where course_id = ? and student_id = ?", courseId, studentId);
    }

    public List<SelectedCourse> getSelectedCourse(int studentId){
        List<SelectedCourse> list = this.getJdbcTemplate().query("" +
                "select\n" +
                "            selected_course.course_id,\n" +
                "            selected_course.student_id,\n" +
                "            course_list.id,\n" +
                "            course_list.name,\n" +
                "            course_list.day,\n" +
                "            course_list.begin,\n" +
                "            teacher.name as teacher_name,\n" +
                "            classroom.name as classroom_name\n" +
                "        from\n" +
                "            selected_course\n" +
                "        left join\n" +
                "            course_list\n" +
                "        on\n" +
                "            selected_course.course_id = course_list.id\n" +
                "        left join\n" +
                "            teacher\n" +
                "        on\n" +
                "            course_list.teacher_id = teacher.id\n" +
                "        left join\n" +
                "            classroom\n" +
                "        on\n" +
                "            course_list.room_id = classroom.room_id\n" +
                "        where\n" +
                "            selected_course.student_id=?", new SelectedCourseMapper(),studentId);
        return list;
    }

    class SelectedCourseMapper implements RowMapper<SelectedCourse>{
        @Override
        public SelectedCourse mapRow(ResultSet resultSet, int i) throws SQLException {
            SelectedCourse selectedCourse = new SelectedCourse();
            selectedCourse.setCourse_id(resultSet.getInt("course_id"));
            selectedCourse.setStudent_id(resultSet.getInt("student_id"));
            selectedCourse.getCourseList().setId(resultSet.getInt("id"));
            selectedCourse.getCourseList().setName(resultSet.getString("name"));
            selectedCourse.getCourseList().setDay(resultSet.getInt("day"));
            selectedCourse.getCourseList().setBegin(resultSet.getInt("begin"));
            selectedCourse.getTeacher().setName(resultSet.getString("teacher_name"));
            selectedCourse.getClassRoom().setName(resultSet.getString("classroom_name"));
            return selectedCourse;
        }
    }
}
