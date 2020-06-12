import java.io.*;
import java.util.*;

public class TimeTable {
    public String[][] timetable;

    public TimeTable() {
        int slot = 6;
        int day = 5;
        timetable = new String[day][slot];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                timetable[i][j] = "''";
            }
        }
    }
    public void setTimeTable(List<Course> AllCourses, HashSet<Integer> selectedID) {
        for(Course course : AllCourses) {
            if(selectedID.contains(course.getId())) {
                int day = course.getDay();
                int slot = course.getSlot();
                String info = "'" + course.getName() + "@" + course.getLocation() + "'";
                timetable[day - 1][slot - 1] = info;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // 1. read AllCourse.txt
        List<Course> AllCourses = new LinkedList<>();
        List<String> fileString = MyFileIO.toStringList("./AllCourse.txt");

        fileString.remove(0); // remove table head

        for(String line : fileString) {
            Course course = CourseFactory.toCourse(line);
            AllCourses.add(course);
        }

//        for(Course course : AllCourses) {
//            System.out.println(course.toString());
//        }

        // 2. read MySelectedCourseID.txt
        HashSet<Integer> selectedID = new HashSet<>();
        fileString = MyFileIO.toStringList("./MySelectedCourseID.txt");
        for(String line : fileString) {
            selectedID.add(Integer.parseInt(line));
        }

        // 3. initialize the time table
        TimeTable timetable = new TimeTable();

        // 4. fill in the time table
        timetable.setTimeTable(AllCourses, selectedID);

        //System.out.println(Arrays.deepToString(timetable));

        // 5. replace the placeholders in template.html
        String strTimetable = Arrays.deepToString(timetable.timetable);

        fileString = MyFileIO.toStringList("./template.html");
        List<String> newFileString = new LinkedList<>();
        for (String line : fileString) {
            line = line.replaceFirst("'##TimetableString##'", strTimetable);
            newFileString.add(line);
        }
        MyFileIO.writeToFile("./timetable.html", newFileString);


        // 6. start web server
        WebServer webServer = new WebServer();
        webServer.startServer(8000);
    }
}