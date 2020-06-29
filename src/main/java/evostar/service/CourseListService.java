package evostar.service;

import evostar.mapper.CourseListMapper;
import evostar.pojo.CourseList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseListService {
    @Autowired
    private CourseListMapper courseListMapper;
    public List<CourseList> getCourseList(int studentId) {
        List<CourseList> list = courseListMapper.getCourseList(studentId);
        return list;
    }
    public void increase_total_num(int courseId) {
        courseListMapper.increase_total_num(courseId);
    }
    public void decrease_total_num(int courseId) {
        courseListMapper.decrease_total_num(courseId);
    }
}
