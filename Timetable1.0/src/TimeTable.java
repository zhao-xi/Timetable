import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeTable {

    public static void main(String[] args) throws IOException {
        // 1. read AllCourse.txt
        BufferedReader bfReader = new BufferedReader(new FileReader("./AllCourse.txt"));

        List<String[]> allCourses = new ArrayList<>();
        String line = bfReader.readLine(); // this line is table head
        while((line = bfReader.readLine()) != null) {
            String[] course = line.split("\\s+");
            allCourses.add(course);
        }
        bfReader.close();

//        for(String[] strs : allCourses) {
//            System.out.println(Arrays.toString(strs));
//        }

        // 2. read MySelectedCourseID.txt
        bfReader = new BufferedReader(new FileReader("./MySelectedCourseID.txt"));

        List<String> mySelectedCoursesID = new ArrayList<>();
        line = null;
        while((line = bfReader.readLine()) != null) {
            String selectedId = line;
            mySelectedCoursesID.add(selectedId);
        }
        bfReader.close();
//        for(String str : mySelectedCoursesID) {
//            System.out.println(str);
//        }

        // 3. initialize the time table
        String[][] timeTable = new String[5][6];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                timeTable[i][j] = "\'\'";
            }
        }


        // 4. fill in the time table
        for(String id : mySelectedCoursesID) {
            for(String[] course : allCourses) {
                String courseId = course[0];
                if(id.equals(courseId)) {
                    int day = Integer.parseInt(course[1]);
                    int slot = Integer.parseInt(course[2]);
                    String courseName = course[3];
                    String location = course[4];
                    timeTable[day - 1][slot - 1] = "'" + courseName + "@" + location + "'";
                    break;
                }
            }
        }

//        for(String[] strs : timeTable) {
//            System.out.println(Arrays.toString(strs));
//        }

        // 5. replace the placeholders in template.html
        bfReader = new BufferedReader(new FileReader("./template.html"));
        PrintWriter pw = new PrintWriter("./timetable.html");
        
        String timeTableStr = Arrays.deepToString(timeTable);
        
        while((line = bfReader.readLine()) != null) {
            line = line.replaceFirst("'##TimetableString##'", timeTableStr);
            pw.println(line);
        }
        pw.close();
        bfReader.close();
    }
}