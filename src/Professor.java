import java.util.LinkedList;

/**
 * Created by kevin on 2017-09-28.
 * NSIDs: kpb637
 * Student#s: 11173877
 */
public class Professor extends Person {

    protected String nsid;
    protected String phd;
    protected LinkedList<Course> coursesTeaching;
    protected String officeLocation; // ie THORV 271

    /**
     * Professor Constructor
     * @param name
     * @param address
     * @param phoneNumber
     * @param nsid
     * @param phd
     * @param officeLocation
     */
    public Professor(String name, String address, String phoneNumber, String nsid, String phd, String officeLocation) {
        super(name, address, phoneNumber);
        this.nsid = nsid;
        this.phd = phd;
        this.officeLocation = officeLocation;
        this.coursesTeaching = new LinkedList<>();
    }

    /**
     * Smaller professor constructor
     * @param name
     * @param address
     * @param phoneNumber
     */
    public Professor(String name, String address, String phoneNumber) {
        super(name, address, phoneNumber);
        this.coursesTeaching = new LinkedList<>();
    }

    /**
     * Uninitialized professor constructor
     */
    public Professor(){
        this.coursesTeaching = new LinkedList<>();
    }

    /**
     * get nsid
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
     * get PHD
     * @return PHD
     */
    public String getPhd() {
        return phd;
    }

    /**
     * set PHD
     * @param phd new PHD
     */
    public void setPhd(String phd) {
        this.phd = phd;
    }

    /**
     * get room location
     * @return room location
     */
    public String getOfficeLocation() {
        return officeLocation;
    }

    /**
     * set room location
     * @param officeLocation new room location
     */
    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }


    /**
     * Add to professors courses that they are teaching
     * @param courseToTeach course to teach
     * @return true if successful, false otherwise
     */
    public boolean addToCoursesTeaching(Course courseToTeach){
        return coursesTeaching.add(courseToTeach);
    }

    /**
     * remove from courses that the prof is teaching
     * @param courseToRemove course to remove
     * @return true if successful, false otherwise
     */
    public boolean removeFromCoursesTeaching(Course courseToRemove){

        if(coursesTeaching.contains(courseToRemove)){
            coursesTeaching.remove(courseToRemove);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Student{ " + name + ", " + address + ", " + phoneNumber + ", " + nsid + ", " + phd +
                ", " + officeLocation + " }";
    }




    public static void main(String[] args) {
        Professor bigboi = new Professor("Dwight",
                                         "123 fake street",
                                         "3066123453",
                                         "ddd333",
                                          "Computer Science",
                                          "Spinks 4somthing");

        System.out.println(bigboi);

        // set and get NSID
        // set and get nsid
        System.out.println("Setting and getting NSID");
        bigboi.setNsid("eee123");
        System.out.println(bigboi.getNsid());
        System.out.println(bigboi);

        // set and get PH.D.
        System.out.println("Setting and getting PHD");
        bigboi.setPhd("Philosophy");
        System.out.println(bigboi.getPhd());
        System.out.println(bigboi);

        // set and get office location
        System.out.println("Setting and getting office location");
        bigboi.setOfficeLocation("Card Board Box");
        System.out.println(bigboi.getOfficeLocation());
        System.out.println(bigboi);

        // testing to see if I can add a course.
        Course testCourse = new Course();
        testCourse.setName("Test Course");
        if(!bigboi.addToCoursesTeaching(testCourse)){
            System.out.println("ERROR: Failed to add test course to students course load.");
        } else {
            System.out.println("Number of courses after adding to course load: " + bigboi.coursesTeaching.size());
        }


        // testing to see if I can remove a course
        if(!bigboi.removeFromCoursesTeaching(testCourse)){
            System.out.println("ERROR: Failed to remove test course from students course load.");
        } else {
            System.out.println("Number of courses after removing from course load: " + bigboi.coursesTeaching.size());
        }

    }

}


