package evostar.pojo;

public class CourseList {
    private int id;
    private String name;
    private int day;
    private int begin;
    private Teacher teacher;
    private int selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "CourseList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", day=" + day +
                ", begin=" + begin +
                ", teacher=" + teacher +
                ", selected=" + selected +
                '}';
    }
}
