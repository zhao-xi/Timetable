package evostar.mapper;

import evostar.pojo.CourseList;

import java.util.List;

public interface CourseListMapper {
    public List<CourseList> getCourseList(int studentId);
    public String getCourseNameById(int courseId);
    public void increase_total_num(int courseId);
    public void decrease_total_num(int courseId);
}
