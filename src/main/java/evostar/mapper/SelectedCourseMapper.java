package evostar.mapper;

import evostar.pojo.SelectedCourse;

import java.util.List;

public interface SelectedCourseMapper {
    public void insert(SelectedCourse selectedCourse);
    public void delete(SelectedCourse selectedCourse);
    public List<SelectedCourse> getSelectedCourse(int studentId);
}
