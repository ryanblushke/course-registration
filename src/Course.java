import java.util.LinkedList;

/**
 * Created by kevin on 2017-09-28.
 * NSIDs: kpb637
 * Student#s: 11173877
 */
public class Course {

    protected String name; // ie CME 341
    protected int startTime;
    protected int endTime;
    protected String days;
    protected String term;
    protected Professor prof;
    protected Integer sizeLimit;
    protected Integer numStudentsEnrolled;
    protected LinkedList<Student> enrolledStudents;
    protected LinkedList<Course> prereq;
    protected LinkedList<Course> coreq;
    //private TimeSchedule scheduledTime;

    /**
     * Fully Loaded Constructor
     * @param name
     * @param prof
     * @param sizeLimit
     * @param numStudentsEnrolled
     */
    public Course(String name, Professor prof, Integer sizeLimit, Integer numStudentsEnrolled) {
        this.numStudentsEnrolled = 0;
        this.name = name;
        this.prof = prof;
        this.sizeLimit = sizeLimit;
        this.numStudentsEnrolled = numStudentsEnrolled;
        enrolledStudents = new LinkedList<>();
        prereq = new LinkedList<>();
        coreq = new LinkedList<>();
    }

    /**
     * Uninitialized constructor
     */
    public Course(){
        this.numStudentsEnrolled = 0;
        this.setSizeLimit(100);
        enrolledStudents = new LinkedList<>();
        prereq = new LinkedList<>();
        coreq = new LinkedList<>();
    }


    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }


    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    /**
     * get course name
     * @return course name
     */
    public String getName() {
        return name;
    }

    /**
     * set course name
     * @param name new course name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * get professor teaching the course
     * @return professor
     */
    public Professor getProf() {
        return prof;
    }

    /**
     * set the professor teaching the course
     * @param prof professor
     */
    public void setProf(Professor prof) {
        this.prof = prof;
    }

    /**
     * get size limit (number of students that can take the course) of course
     * @return size limit of course
     */
    public Integer getSizeLimit() {
        return sizeLimit;
    }

    /**
     * sets a size limit for the course
     * @param sizeLimit
     */
    public void setSizeLimit(Integer sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    /**
     * get number of student currently enrolled in class
     * @return
     */
    public Integer getNumStudentsEnrolled() {
        return numStudentsEnrolled;
    }


    /**
     * get a list of enrolled students in class
     * @return list of students
     */
    public LinkedList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    /**
     * get a list of prerequisites for the course
     * @return list of prerequisites
     */
    public LinkedList<Course> getPrereq() {
        return prereq;
    }

    /**
     * adds a prerequisite to the course
     * @param prereqCourse
     * @return true on success, false otherwise
     */
    public boolean addPrereq(Course prereqCourse){
        return prereq.add(prereqCourse);
    }

    /**
     * remove prerequisite from the course
     * @param prereqCourse
     * @return true on success, false otherwise
     */
    public boolean removePrereq(Course prereqCourse){
        if(prereq.contains(prereqCourse)){
            prereq.remove(prereqCourse);
            return true;
        } else {
            return false;
        }
    }

    /**
     * get a list of corequisites for the course
     * @return list of corequisites
     */
    public LinkedList<Course> getCoreq() {
        return coreq;
    }

    /**
     * add a corequisite to the course
     * @param coreqCourse class to add to corequisites
     * @return true if successful, false otherwise
     */
    public boolean addCoereq(Course coreqCourse){
        return coreq.add(coreqCourse);
    }

    /**
     * remove a corequisite from the course
     * @param coreqCourse course to remove
     * @return true if successful, false otherwise
     */
    public boolean removeCoereq(Course coreqCourse){
        if(coreq.contains(coreqCourse)){
            coreq.remove(coreqCourse);
            return true;
        } else {
            return false;
        }
    }

    /**
     * add student to this course.
     * @param student student to add to course
     * @return true on success, false otherwise
     */
    public boolean addStudent(Student student){

        // Class isn't full and the student isn't already enrolled.
        if( (this.enrolledStudents.size() < this.sizeLimit) && (!enrolledStudents.contains(student)) ){
            enrolledStudents.add(student);
            student.addCourse(this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * removes a student from this course
     * @param student student to add to course
     * @return true on success, false otherwise
     */
    public boolean removeStudent(Student student){
        if( (this.enrolledStudents.size() != 0) && (enrolledStudents.contains(student)) ) {
            enrolledStudents.remove(student);
            student.removeCourse(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Term " + this.term + " | " +
                Integer.toString(this.startTime) + "-" + Integer.toString(this.endTime) + " | " +
                this.days + " | " +
                this.name;

    }

    public static void main(String[] args) {

        Student pleb = new Student("Andy Cheung",
                "8008 DilliLane Street West",
                "3066308008",
                "abc123",
                "11122234",
                "Computer Engineering");

        Professor mike = new Professor("Mike Hawk", "123 Fake Street", "3066309999", "mbk123", "Mathmatics", "THORV 331");

        // Creating testCourse
        Course testCourse = new Course();
        testCourse.setName("Introduction to Algebraic Equations");
        testCourse.setProf(mike);
        testCourse.setSizeLimit(5);



    }


}
