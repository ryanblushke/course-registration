/**
 * Name: Kevin Baker
 * NSID: kpb637
 * Student Number:
 * Course:
 * Project Name: classRegister
 */

/**
 * Name: Ryan Blushke
 * NSID: ryb861
 * Student Number: 11177824
 * Course: CMPT 370
 * Project Name: classRegister
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Driver {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    /**
     * Default constructor method
     * Assigns variables url, username, and password with our GitLab Repository credentials
     */
    public Driver(){
        url = "jdbc:mysql://db.cs.usask.ca:3306/cmpt370_bigdsm";
        username = "cmpt370_bigdsm";
        password = "bnxOEOlO7i8Xlv4FWrJc";
    }

    /**
     * Closes connection to mySQL
     */
    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * Creates a connection to the mySQL database
     */
    public void connectToDatabase(){

        try{
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e){
            System.out.println("UNABLE TO CONNECT TO DATABASE.");
            e.printStackTrace();
        }
    }


    /**
     * Gets a specified course from the data base and returns the courses times in start-finish Room Day format
     * in an array.
     * @param course the class to search for.
     * @return an array of Strings containing class time information.
     */
    public String[] getCourseTimes(String course){
        // TODO: FIGURE OUT A WAY TO ADD LABS TO VIEW LIST
        String getCourseTimesSQL = "SELECT * FROM Classes WHERE ClassName = ?";
        List<String> courseTimeInfo = new ArrayList<>();
        String cInfo = new String();

        try {
            PreparedStatement myStatForCourseInfo = this.connection.prepareStatement(getCourseTimesSQL);
            myStatForCourseInfo.setString(1, course);
            // Execute SQL query
            ResultSet resultSetOfCourseInfo = myStatForCourseInfo.executeQuery();
            // Process the result set
            while(resultSetOfCourseInfo.next()){
                cInfo = resultSetOfCourseInfo.getString("Start") + "-" +
                        resultSetOfCourseInfo.getString("Finish") + " " +
                        resultSetOfCourseInfo.getString("Room") + " " +
                        resultSetOfCourseInfo.getString("Day");

                courseTimeInfo.add( cInfo );
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return courseTimeInfo.toArray(new String[courseTimeInfo.size()]);

    }

    /**
     * Gets an array of strings that contains all the classes needed for degree requirements and is used to populate
     * classes that a user can take.
     * @param nsid identification of student.
     * @return array of strings containing course names for degree requirement.
     */
    public String[] getClassList(String nsid){
        String getDegreeReqSQL = "SELECT ClassName FROM DegreeReq";
        String getTakenClasses = "SELECT * FROM Completed WHERE NSID = " + nsid;

        List<String> classNames = new ArrayList<>();

        try {
            Statement myStatForCourses = this.connection.createStatement();
            Statement myStatForCoursesTaken = this.connection.createStatement();
            // Execute SQL query
            ResultSet resultSetOfCourses = myStatForCourses.executeQuery(getDegreeReqSQL);
            ResultSet resultSetOfClassesTaken = myStatForCoursesTaken.executeQuery(getTakenClasses);
            // Process the result set
            while(resultSetOfCourses.next()){
                classNames.add((resultSetOfCourses.getString("ClassName")));
            }

            // Get classes taken
            while(resultSetOfClassesTaken.next()){
                int index = 1;

                while(resultSetOfClassesTaken.getNString(index) != null){

                    // If class has been taken, remove from classes to take.
                    if( classNames.contains(resultSetOfClassesTaken.getNString(index)) ){
                        classNames.remove(resultSetOfClassesTaken.getNString(index));
                    }
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return classNames.toArray(new String[classNames.size()]);
    }

    /**
     * Add a new user to the database
     * @param username the desired username of the new user
     * @param password the desired password of the new user
     */
    public void addUser(String username, String password){

        // Add User
        Date date = new Date(System.currentTimeMillis());
        Timestamp tStamp = new Timestamp(date.getTime());
        System.out.println(tStamp);

        String addUserSQL = "insert into loginaccount " +
                "(username, password, TS) " +
                "values (?,?,?)";

        try {
            PreparedStatement addUserStat = this.connection.prepareStatement(addUserSQL);
            addUserStat.setString(1, username);
            addUserStat.setString(2, password);
            addUserStat.setTimestamp(3, tStamp);

            addUserStat.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user into the system.
     * @param username
     * @param password
     * @return true if user credentials are correct, false otherwise.
     */
    public boolean authenticateUser(String username, String password){

        boolean userExists = false;
        String lookingForUser = username + " " + password;

        try {
            Statement myStat = this.connection.createStatement();
            // Execute SQL query
            ResultSet myRes = myStat.executeQuery("select * from loginaccount");
            // Process the result set
            while(myRes.next()){
                String returnedUser = myRes.getString("username") + " " + myRes.getString("password");
                if(returnedUser.equals(lookingForUser)){
                    userExists = true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return userExists;
    }

    /**
     * Determines whether or not a specified username is in the system or not.
     * @param username
     * @return true if the username exists, false otherwise
     */
    public boolean userExists(String username) {
        String selectSQL = "SELECT * FROM loginaccount WHERE username = ?";

        try {
            PreparedStatement myStmt = this.connection.prepareStatement(selectSQL);
            myStmt.setString(1, username);

            // Execute SQL query
             ResultSet myRS = myStmt.executeQuery();

            // Process the result set
            if ( myRS.next() ) {
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Test method for connecting to our database and Querying or Updating a table
     * @param args not used
     */
    public static void main(String[] args) {

        Driver BDSM_Driver = new Driver();
        BDSM_Driver.connectToDatabase();
//        //BDSM_Driver.addUser("lml145","smallboi");
//        Boolean userExists = BDSM_Driver.authenticateUser("lml145","smallboi");
//        System.out.println(userExists);
//        userExists = BDSM_Driver.authenticateUser("kpb637","cats");
//        System.out.println(userExists);
        if (BDSM_Driver.userExists("lml145")) {
            System.out.println("lml145");
        }

        if (BDSM_Driver.userExists("asdf")) {
            System.out.println("asdf");
        }

        if (BDSM_Driver.userExists("Chanchal")) {
            System.out.println("Chanchal");
        }

        if (BDSM_Driver.userExists("scrub")) {
            System.out.println("scrub");
        }

        if (BDSM_Driver.userExists("anc172")) {
            System.out.println("anc172");
        }


//        try {
//            Statement myStat = BDSM_Driver.connection.createStatement();
//            // Execute SQL query
//            ResultSet myRes = myStat.executeQuery("select * from Classes");
//            // Process the result set
//            while(myRes.next()){
//                System.out.println(myRes.getString("ClassName") + ", " + myRes.getString("ClassCode"));
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }

    }
}
