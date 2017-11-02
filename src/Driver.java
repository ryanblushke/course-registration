/**
 * Name: Kevin Baker, Ryan Blushke
 * NSID: kpb637
 * Student Number:
 * Course:
 * Project Name: classRegister
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Driver {

    private static final int PREREQPOSITION = 11;
    private static final int MAXNUMOFPREQ = 3;

    private String url;
    private String username;
    private String password;
    private Connection connection;
    private LinkedList<Course> viewClassList;
    private Schedule T1_Schedule;
    private Schedule T2_Schedule;

    /**
     * Default constructor method
     * Assigns variables url, username, and password with our GitLab Repository credentials
     */
    public Driver(){
        url = "jdbc:mysql://db.cs.usask.ca:3306/cmpt370_bigdsm";
        username = "cmpt370_bigdsm";
        password = "bnxOEOlO7i8Xlv4FWrJc";
        T1_Schedule = new Schedule(); // HOLDS TERM 1 SCHEDULE
        T2_Schedule = new Schedule(); // HOLDS TERM 2 SCHEDULE
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
     * Removes a course from the schedule.
     * @param course course to remove in toString form.
     */
    public void removeFromSchedule(String course){

        Iterator<Course> I = T1_Schedule.getCoursesInSchedule().iterator();
        while( I.hasNext() ){
            Course c = I.next();
            if( c.toString().equals(course) ){
                T1_Schedule.removeCourse(c);
            }
        }

        I = T2_Schedule.getCoursesInSchedule().iterator();
        while( I.hasNext() ){
            Course c = I.next();
            if( c.toString().equals(course) ){
                T2_Schedule.removeCourse(c);
            }
        }

        return;
    }


    /**
     * Returns schedule as a string array for updating registerList data.
     * @return dido.
     */
    public String[] getScheduleAsStringArray(){

        List<String> termScheduleStringArray = new ArrayList<>();

        Iterator<Course> I = T1_Schedule.getCoursesInSchedule().iterator();
        while( I.hasNext() ){
            termScheduleStringArray.add( I.next().toString() );
        }

        I = T2_Schedule.getCoursesInSchedule().iterator();
        while( I.hasNext() ){
            termScheduleStringArray.add( I.next().toString() );
        }

        return termScheduleStringArray.toArray(new String[termScheduleStringArray.size()]);
    }

    /**
     * Adds a course based on where it's at in the viewLIST to it's corresponding schedule.
     * DOESN'T COMMIT THE SCHEDULE TO DATABASE UNTIL REGISTER BUTTON IS PRESSED.
     * @param indexOfCourseInClassView index of where the course is at within viewClassList
     * @return true if class adds successfully, false otherwise
     */
    public boolean addToSchedule(int indexOfCourseInClassView){

        boolean courseAdded = false;
        Course c = this.viewClassList.get(indexOfCourseInClassView);

        if( c.getTerm().equals("1") ){
            if( T1_Schedule.addCourse(c) ){
                courseAdded = true;
            }
        }
        else if( c.getTerm().equals("2") ){
            if( T2_Schedule.addCourse(c) ){
                courseAdded = true;
            }
        }

        return courseAdded;
    }

    /**
     * Gets a specified course from the data base and returns the courses times in start-finish Room Day format
     * in an array. ALSO POPULATES A COURSE CLASS WITH ALL THE CLASSES SPECIFIED INFORMATION AND ADDS IT TO
     * viewClassList
     * @param course the class to search for.
     * @return an array of Strings containing class time information.
     */
    public String[] getCourseInformation(String course){
        // TODO: FIGURE OUT A WAY TO ADD LABS TO VIEW LIST
        String getCourseTimesSQL = "SELECT * FROM Classes WHERE ClassName = ?";
        List<String> courseTimeInfo = new ArrayList<>();
        String cInfo;

        try {
            PreparedStatement myStatForCourseInfo = this.connection.prepareStatement(getCourseTimesSQL);
            myStatForCourseInfo.setString(1, course);
            // Execute SQL query
            ResultSet resultSetOfCourseInfo = myStatForCourseInfo.executeQuery();
            // Process the result set
            viewClassList = new LinkedList<>();
            while(resultSetOfCourseInfo.next()){
                Course c = new Course();
                c.setName(resultSetOfCourseInfo.getString("ClassName"));
                c.setStartTime(resultSetOfCourseInfo.getInt("Start"));
                c.setEndTime(resultSetOfCourseInfo.getInt("Finish"));
                c.setTerm(resultSetOfCourseInfo.getString("Term"));
                c.setDays(resultSetOfCourseInfo.getString("Day"));
                c.setClassID(resultSetOfCourseInfo.getInt("ClassID"));
                c.setClassCU(resultSetOfCourseInfo.getInt("ClassCU"));
                c.setRoom(resultSetOfCourseInfo.getString("Room"));
                c.setProf(resultSetOfCourseInfo.getString("ClassProf"));
                cInfo = c.toString();
                viewClassList.add(c);

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
        String getTakenClasses = "SELECT * FROM Completed WHERE NSID = ?";

        List<String> classNames = new ArrayList<>();
        List<String> classesTaken = new ArrayList<>();

        try {
            Statement myStatForCourses = this.connection.createStatement();
            PreparedStatement myStatForCoursesTaken = this.connection.prepareStatement(getTakenClasses);
            myStatForCoursesTaken.setString(1, nsid);
            // Execute SQL query
            ResultSet resultSetOfDegreeRequirementCourses = myStatForCourses.executeQuery(getDegreeReqSQL);
            ResultSet resultSetOfClassesTaken = myStatForCoursesTaken.executeQuery();
            // Process the result set
            
            // Get classes taken
            while(resultSetOfClassesTaken.next()){
                int index = 1;

                while(resultSetOfClassesTaken.getString(index) != null){
                    classesTaken.add(resultSetOfClassesTaken.getString(index));
                    index++;
                }
            }

            // LOOPS THROUGH ALL DEGREE REQUIREMENT COURSES
            while(resultSetOfDegreeRequirementCourses.next()){

                if( resultSetOfDegreeRequirementCourses.getString("ClassName").equals("ScienceElective") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("JuniorHumanities") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("SeniorHumanities") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("Complementary") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec")){

                    String getSpecialCourseListSQL;
                    Statement myStatSpecialCourseList;
                    ResultSet myResultSpecialCourseList;

                    if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("ScienceElective")) {
                        getSpecialCourseListSQL = "SELECT ClassName FROM ScienceElective";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("JuniorHumanities")) {
                        getSpecialCourseListSQL = "SELECT ClassName FROM JuniorHumanities";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("SeniorHumanities")) {
                        getSpecialCourseListSQL = "SELECT ClassName FROM SeniorHumanities";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("Complementary")) {
                        getSpecialCourseListSQL = "SELECT ClassName FROM Complementary";
                    }
                    else {
                        getSpecialCourseListSQL = "SELECT ClassName FROM StreamElec";
                    }

                    myStatSpecialCourseList = this.connection.createStatement();
                    myResultSpecialCourseList = myStatSpecialCourseList.executeQuery(getSpecialCourseListSQL);

                    while( myResultSpecialCourseList.next() ){

                        String getCourseInfoSQL = "SELECT * FROM Classes WHERE ClassName = ?";
                        PreparedStatement myStatCourseInfo = this.connection.prepareStatement(getCourseInfoSQL);
                        myStatCourseInfo.setString(1, myResultSpecialCourseList.getString("ClassName"));
                        ResultSet myResultCourseInfo = myStatCourseInfo.executeQuery();

                        // CLASS HASN'T BEEN TAKEN
                        if( !classesTaken.contains(myResultSpecialCourseList.getString("ClassName")) ){

                            int index = PREREQPOSITION; // INDEX OF PREREQUISITES FIELD IN TABLE
                            boolean haveAllPreReq = true;
                            boolean dontNeedCreditUnits = true;
                            boolean classExistsInClasses = true;

                            if( myResultCourseInfo.next() ) { // MAKES SURE THE CLASS INFO WAS BROUGHT IN FROM MYSQL

                                // Check PREREQUISITES BEGINS --------------------------------------------------------------
                                while( (myResultCourseInfo.getString(index) != null) && (index < PREREQPOSITION + MAXNUMOFPREQ) ) {
                                    // IF YOU DON'T HAVE THE PREREQ, SET HAVE PREREQ TO FALSE
                                    if (!classesTaken.contains(myResultCourseInfo.getString(index))) {
                                        haveAllPreReq = false;
                                    }
                                    index++;
                                }
                                // Check PREREQUISITES ENDS ----------------------------------------------------------------

                                // Check for 24 CreditUnits Needed BEGIN ---------------------------------------------------
                                String getStudentInfoSQL = "SELECT * FROM Users WHERE NSID = ?";
                                PreparedStatement myStatStudentInfo = this.connection.prepareStatement(getStudentInfoSQL);
                                myStatStudentInfo.setString(1, nsid);
                                ResultSet myResultStudentInfo = myStatStudentInfo.executeQuery();

                                if(myResultStudentInfo.next()){
                                    if( myResultCourseInfo.getString("CreditReq") != null ){
                                        if( myResultCourseInfo.getInt("CreditReq") > myResultStudentInfo.getInt("CreditUnits") ){
                                            dontNeedCreditUnits = false;
                                        }
                                    }
                                }
                                // Check for 24 CreditUnits Needed END -----------------------------------------------------
                            } else {
                                classExistsInClasses = false;
                            }


                            if( haveAllPreReq && dontNeedCreditUnits && classExistsInClasses ){
                                if( !classNames.contains(myResultSpecialCourseList.getString("ClassName")) ){
                                    classNames.add(myResultSpecialCourseList.getString("ClassName"));
                                }
                            }
                        }

                    }

                }
                else{

                    String getCourseInfoSQL = "SELECT * FROM Classes WHERE ClassName = ?";
                    PreparedStatement myStatCourseInfo = this.connection.prepareStatement(getCourseInfoSQL);
                    myStatCourseInfo.setString(1, resultSetOfDegreeRequirementCourses.getString("ClassName"));
                    ResultSet myResultCourseInfo = myStatCourseInfo.executeQuery();

                    // CLASS HASN'T BEEN TAKEN
                    if( !classesTaken.contains(resultSetOfDegreeRequirementCourses.getString("ClassName")) ){

                        int index = PREREQPOSITION; // INDEX OF PREREQUISITES FIELD IN TABLE
                        boolean haveAllPreReq = true;
                        boolean dontNeedCreditUnits = true;
                        boolean classExistsInClasses = true;

                        if( myResultCourseInfo.next() ) { // MAKES SURE THE CLASS INFO WAS BROUGHT IN FROM MYSQL

                            // Check PREREQUISITES BEGINS --------------------------------------------------------------
                            while( (myResultCourseInfo.getString(index) != null) && (index < PREREQPOSITION + MAXNUMOFPREQ) ) {
                                // IF YOU DON'T HAVE THE PREREQ, SET HAVE PREREQ TO FALSE
                                if (!classesTaken.contains(myResultCourseInfo.getString(index))) {
                                    haveAllPreReq = false;
                                }
                                index++;
                            }
                            // Check PREREQUISITES ENDS ----------------------------------------------------------------

                            // Check for 24 CreditUnits Needed BEGIN ---------------------------------------------------
                            String getStudentInfoSQL = "SELECT * FROM Users WHERE NSID = ?";
                            PreparedStatement myStatStudentInfo = this.connection.prepareStatement(getStudentInfoSQL);
                            myStatStudentInfo.setString(1, nsid);
                            ResultSet myResultStudentInfo = myStatStudentInfo.executeQuery();

                            if(myResultStudentInfo.next()){
                                if( myResultCourseInfo.getString("CreditReq") != null ){
                                    if( myResultCourseInfo.getInt("CreditReq") > myResultStudentInfo.getInt("CreditUnits") ){
                                        dontNeedCreditUnits = false;
                                    }
                                }
                            }
                            // Check for 24 CreditUnits Needed END -----------------------------------------------------
                        } else {
                            classExistsInClasses = false;
                        }


                        if(haveAllPreReq && dontNeedCreditUnits && classExistsInClasses){
                            if( !classNames.contains(resultSetOfDegreeRequirementCourses.getString("ClassName")) ){
                                classNames.add(resultSetOfDegreeRequirementCourses.getString("ClassName"));
                            }
                        }
                    }
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        Collections.sort(classNames);
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

        String addUserLoginAccountSQL = "insert into loginaccount " +
                "(username, password, TS) " +
                "values (?,?,?)";

        String addUserCompletedSQL = "INSERT into Completed (NSID) values(?)";
        String addUserFailedSQL = "INSERT into Failed (NSID) values(?)";
        String addUserTakingT1SQL = "INSERT into Taking_T1 (NSID) values(?)";
        String addUserTakingT2SQL = "INSERT into Taking_T2 (NSID) values(?)";
        String addUserUsersSQL = "INSERT into Users (NSID) values(?)";

        try {
            // LoginAccount table
            PreparedStatement addUserStat = this.connection.prepareStatement(addUserLoginAccountSQL);
            addUserStat.setString(1, username);
            addUserStat.setString(2, password);
            addUserStat.setTimestamp(3, tStamp);
            addUserStat.executeUpdate();

            // Completed Table
            addUserStat.close();
            addUserStat = this.connection.prepareStatement(addUserCompletedSQL);
            addUserStat.setString(1, username);
            addUserStat.executeUpdate();

            // Failed Table
            addUserStat.close();
            addUserStat = this.connection.prepareStatement(addUserFailedSQL);
            addUserStat.setString(1, username);
            addUserStat.executeUpdate();

            // Taking_T1 Table
            addUserStat.close();
            addUserStat = this.connection.prepareStatement(addUserTakingT1SQL);
            addUserStat.setString(1, username);
            addUserStat.executeUpdate();

            // Taking_T2 Table
            addUserStat.close();
            addUserStat = this.connection.prepareStatement(addUserTakingT2SQL);
            addUserStat.setString(1, username);
            addUserStat.executeUpdate();

            // Users Table
            addUserStat.close();
            addUserStat = this.connection.prepareStatement(addUserUsersSQL);
            addUserStat.setString(1, username);
            addUserStat.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addClassToDB(String[] classes, String nsid, String term){

        int i = 0;
        int j = 0;
        String t = "T1";
        String alterNSIDclassesSQL = "ALTER TABLE Taking_T1 ALTER NSID set t classes[j]";
        String checkT1ClassesEmpty = "SELECT NSID FROM Taking_t1 WHERE t IS NULL OR t = ''";
        String addT1ClassesSQL = "INSERT INTO  Taking_T1(t) values (classes[j])";
        String updateT1ClassesSQL = "UPDATE Taking_T1 SET t = classes[j] where NSID = nsid ";

        try{
            PreparedStatement checkT1Empty = this.connection.prepareStatement(checkT1ClassesEmpty);
            PreparedStatement addT1classes = this.connection.prepareStatement(addT1ClassesSQL);
            PreparedStatement alterclasses = this.connection.prepareStatement(alterNSIDclassesSQL);
            PreparedStatement updateT1classes = this.connection.prepareStatement(updateT1ClassesSQL);
            if (term == ""){
                for(j = 0; j >= classes.length; j++){
                while(checkT1Empty.execute() == false){
                    i++;
                    t = "T" + i;
                }
                    updateT1classes.executeUpdate();
                }
            }
            else if (term == ""){

            }
        }catch(Exception e){

        }

    }

    /**
     * Authenticates a user into the system.
     * @param username self explanatory
     * @param password self explanatory
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
     * @param username self explanatory
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
