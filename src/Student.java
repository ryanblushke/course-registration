/**
 * Created by kevin on 2017-09-28.
 * NSIDs: kpb637
 * Student#s: 11173877
 */

import java.util.LinkedList;

public class Student extends Person {

    protected String nsid;
    protected String studentNumber;
    protected String degreeType;
    protected LinkedList<Course> currentCourses;


    /**
     * Constructor for a Student
     * @param nsid Network Services ID
     * @param studentNumber student number
     * @param degreeType the degree the student is pursuing
     */
    public Student(String name, String address, String phoneNumber, String nsid, String studentNumber,
                   String degreeType){

        super(name,address,phoneNumber);
        this.nsid = nsid;
        this.studentNumber = studentNumber;
        this.degreeType = degreeType;
        this.currentCourses = new LinkedList<>();

    }

    /**
     * Default constructor for student that is uninitialized
     */
    public Student(){

        super();
        this.nsid = "";
        this.studentNumber = "";
        this.degreeType = "";
        currentCourses = new LinkedList<Course>();
    }

    /**
     * Get nsid
     * @return nsid
     */
    public String getNsid() {
        return nsid;
    }

    /**
     * set nsid
     * @param nsid new nsid
     */
    public void setNsid(String nsid) {
        this.nsid = nsid;
    }

    /**
     * get student number
     * @return student number
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * set student number
     * @param studentNumber new student number
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * get degree type
     * @return degree type
     */
    public String getDegreeType() {
        return degreeType;
    }

    /**
     * set degree type
     * @param degreeType new degree type
     */
    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    /**
     * get current courses
     * @return current courses
     */
    public LinkedList<Course> getCurrentCourses() {
        return currentCourses;
    }

    /**
     * Add a course to the students course load
     * @param course course to add
     * @return true if successful, false otherwise
     */
    public boolean addCourse(Course course) {

        return this.currentCourses.add(course);

    }

    /**
     * removes a course from the students course load
     * @param course course to remove from course load
     * @return true on success, false otherwise
     */
    public boolean removeCourse(Course course){

        if(currentCourses.contains(course)){
            currentCourses.remove(course);
            return true;
        }
        else{
            return false;
        }

    }

    @Override //Todo: add courses inside this.
    public String toString() {
        return "Student{ " + name + ", " + address + ", " + phoneNumber + ", " + nsid + ", " + studentNumber +
                ", " + degreeType + " }";
    }

    public static void main(String[] args) {
        Student pleb = new Student("Andy Cheung",
                "8008 DilliLane Street West",
                "3066308008",
                "abc123",
                "11122234",
                "Computer Engineering");

        System.out.println(pleb);

        // set and get nsid
        System.out.println("Setting and getting NSID");
        pleb.setNsid("kpb637");
        System.out.println(pleb.getNsid());
        System.out.println(pleb);

        // set and get student number
        System.out.println("Setting and getting student number");
        pleb.setStudentNumber("11173877");
        System.out.println(pleb.getStudentNumber());
        System.out.println(pleb);

        // set and get degree type
        System.out.println("Setting and getting degree type");
        pleb.setDegreeType("Arts and Science");
        System.out.println(pleb.getDegreeType());
        System.out.println(pleb);

        // testing to see if I can add a course.
        Course testCourse = new Course();
        testCourse.setName("Test Course");
        if(!pleb.addCourse(testCourse)){
            System.out.println("ERROR: Failed to add test course to students course load.");
        } else {
            System.out.println("Number of courses after adding to course load: " + pleb.currentCourses.size());
        }


        // testing to see if I can remove a course
        if(!pleb.removeCourse(testCourse)){
            System.out.println("ERROR: Failed to remove test course from students course load.");
        } else {
            System.out.println("Number of courses after removing from course load: " + pleb.currentCourses.size());
        }

    }
}


