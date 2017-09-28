/**
 * Created by kevin on 2017-09-28.
 * NSID: kpb637
 * Student#: 11173877
 */

import java.util.LinkedList;

public class Student extends Person {

    private String nsid;
    private String studentNumber;
    private String degreeType;
    private LinkedList<Course> currentCourses;


    /**
     * Constructor for a Student
     * @param nsid Network Services ID
     * @param studentNumber students
     * @param degreeType
     */
    public Student(String name, String address, String phoneNumber, String nsid, String studentNumber, String degreeType){

        super(name,address,phoneNumber);
        this.nsid = nsid;
        this.studentNumber = studentNumber;
        this.degreeType = degreeType;
        currentCourses = new LinkedList<Course>();

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

}


