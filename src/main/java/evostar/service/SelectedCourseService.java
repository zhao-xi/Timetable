package evostar.service;

import evostar.mapper.SelectedCourseMapper;
import evostar.pojo.SelectedCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedCourseService {
    @Autowired
    private SelectedCourseMapper selectedCourseMapper;

    public void insert(SelectedCourse selectedCourse) {
        selectedCourseMapper.insert(selectedCourse);
    }
    public void delete(SelectedCourse selectedCourse) {
        selectedCourseMapper.delete(selectedCourse);
    }
    public List<SelectedCourse> getSelectedCourse(int studentId) {
        List<SelectedCourse> list = selectedCourseMapper.getSelectedCourse(studentId);
        return list;
    }
}
