import java.io.*;
import java.util.*;

public class TimeTable {

    public static void main(String[] args) throws IOException {
        // 1. read AllCourse.txt
        List<Course> AllCourses = new LinkedList<>();
        List<String> fileString = myFileIO.toStringList("./AllCourse.txt");

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
        fileString = myFileIO.toStringList("./MySelectedCourseID.txt");
        for(String line : fileString) {
            selectedID.add(Integer.parseInt(line));
        }

        // 3. initialize the time table
        String[][] timetable = new String[5][6];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                timetable[i][j] = "\'\'";
            }
        }


        // 4. fill in the time table
        for(Course course : AllCourses) {
            if(selectedID.contains(course.getId())) {
                int day = course.getDay();
                int slot = course.getSlot();
                String info = "'" + course.getName() + "@" + course.getLocation() + "'";
                timetable[day - 1][slot - 1] = info;
            }
        }

        //System.out.println(Arrays.deepToString(timetable));

        // 5. replace the placeholders in template.html
        String strTimetable = Arrays.deepToString(timetable);

        fileString = myFileIO.toStringList("./template.html");
        List<String> newFileString = new LinkedList<>();
        for(int i = 0; i < fileString.size(); i++) {
            String line = fileString.get(i);
            line = line.replaceFirst("'##TimetableString##'", strTimetable);
            newFileString.add(line);
        }
        myFileIO.writeToFile("./timetable.html", newFileString);
    }
}