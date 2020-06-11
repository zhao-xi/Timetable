public class Course {
    private int id;
    private int day;
    private int slot;
    private String name;
    private String location;

    public Course(int id, int day, int slot, String name, String location) {
        this.id = id;
        this.day = day;
        this.slot = slot;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", day=" + day +
                ", slot=" + slot +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
