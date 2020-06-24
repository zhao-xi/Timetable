package evostar.pojo;

public class SelectedCourse {
    private int id;
    private int course_id;
    private int student_id;
    private ClassRoom classroom;
    private Teacher teacher;
    private CourseList courseList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public ClassRoom getClassRoom() {
        return classroom;
    }

    public void setClassRoom(ClassRoom classroom) {
        this.classroom = classroom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public CourseList getCourseList() {
        return courseList;
    }

    public void setCourseList(CourseList courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "SelectedCourse{" +
                "id=" + id +
                ", course_id=" + course_id +
                ", student_id=" + student_id +
                ", classroom=" + classroom +
                ", teacher=" + teacher +
                ", courseList=" + courseList +
                '}';
    }
}
