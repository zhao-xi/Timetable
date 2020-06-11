public class CourseFactory {
    public static Course toCourse(String line) {
        String[] strs = line.split("\\s+");
        Course course = new Course(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]),
                Integer.parseInt(strs[2]), strs[3], strs[4]);
        return course;
    }
}
