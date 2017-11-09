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
    private static final int MAXCLASSESYOUCANTAKE = 17;

    private String url;
    private String username;
    private String password;
    private Connection connection;
    private LinkedList<Course> viewClassList;
    private Schedule T1_Schedule;
    private Schedule T1_Schedule_DB;
    private Schedule T2_Schedule;
    private Schedule T2_Schedule_DB;

    /**
     * Default constructor method
     * Assigns variables url, username, and password with our GitLab Repository credentials
     */
    public Driver(){
        url = "jdbc:mysql://db.cs.usask.ca:3306/cmpt370_bigdsm";
        username = "cmpt370_bigdsm";
        password = "bnxOEOlO7i8Xlv4FWrJc";
        T1_Schedule = new Schedule(); // HOLDS TERM 1 SCHEDULE and Schedule from database
        T2_Schedule = new Schedule(); // HOLDS TERM 2 SCHEDULE and Schedule from database
        T1_Schedule_DB = new Schedule(); // HOLDS TERM 1 SCHEDULE JUST FROM DATABASE
        T2_Schedule_DB = new Schedule(); // HOLDS TERM 2 SCHEDULE JUST FROM DATABASE
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
     * Called on login, Updates T1_Schedule and T2_Schedule from database,
     * call this function whenever a change to Taking_T1 or Taking_T2 changes.
     * @param nsid current user logged in.
     */
    public void getSchedulesFromDB(String nsid){

        List<String> T1 = new ArrayList<>();
        List<String> T2 = new ArrayList<>();

        try{
            String getT1ScheduleSQL = "SELECT * FROM Taking_T1 WHERE NSID = ?";
            String getT2ScheduleSQL = "SELECT * FROM Taking_T2 WHERE NSID = ?";
            PreparedStatement myStatForT1Schedule = this.connection.prepareStatement(getT1ScheduleSQL);
            PreparedStatement myStatForT2Schedule = this.connection.prepareStatement(getT2ScheduleSQL);
            myStatForT1Schedule.setString(1, nsid);
            myStatForT2Schedule.setString(1, nsid);
            ResultSet T1ScheduleResults = myStatForT1Schedule.executeQuery();
            ResultSet T2ScheduleResults = myStatForT2Schedule.executeQuery();

            if( T1ScheduleResults.next() ){
                int i = 2;
                while( i < (MAXCLASSESYOUCANTAKE + 1) ){
                    if( T1ScheduleResults.getString(i) != null ){
                        T1.add( T1ScheduleResults.getString(i) );
                    }
                    i++;
                }
            }

            if( T2ScheduleResults.next() ){
                int i = 2;
                while( i < (MAXCLASSESYOUCANTAKE + 1) ){
                    if( T2ScheduleResults.getString(i) != null ){
                        T2.add( T2ScheduleResults.getString(i) );
                    }
                    i++;
                }
            }
        } catch ( Exception e ){
            e.printStackTrace();
        }

        T1_Schedule_DB = new Schedule();
        T2_Schedule_DB = new Schedule();
        // ADDS COURSES TO THERE CORRESPONDING SCHEDULES
        for( String ID : T1 ){
            Course c = getCourseGivenIDNumber(Integer.valueOf(ID));
            T1_Schedule_DB.addCourse( c );

            if( !T1_Schedule.contains( c.getName() ) ) {
                T1_Schedule.addCourse( c );
            }
        }
        for( String ID : T2 ){
            Course c = getCourseGivenIDNumber(Integer.valueOf(ID));
            T2_Schedule_DB.addCourse( c );

            if( !T2_Schedule.contains( c.getName()) ) {
                T2_Schedule.addCourse( c );
            }
        }
        return;
    }

    /**
     * Gets a course from the database from its CourseID number.
     * @param ID unique id of course.
     * @return course with given ID, null if course doesn't exist.
     */
    public Course getCourseGivenIDNumber(int ID){

        Course c = null;

        try{
            String getCourseFromIDSQL = "SELECT * FROM Classes WHERE ClassID = ?";
            PreparedStatement getCourseFromIDStatement = this.connection.prepareStatement(getCourseFromIDSQL);
            getCourseFromIDStatement.setInt(1, ID);
            ResultSet courseWithIDResult = getCourseFromIDStatement.executeQuery();

            if( courseWithIDResult.next() ){
                c = new Course();
                c.setName(courseWithIDResult.getString("ClassName"));
                c.setStartTime(courseWithIDResult.getInt("Start"));
                c.setEndTime(courseWithIDResult.getInt("Finish"));
                c.setTerm(courseWithIDResult.getString("Term"));
                c.setDays(courseWithIDResult.getString("Day"));
                c.setClassID(courseWithIDResult.getInt("ClassID"));
                c.setClassCU(courseWithIDResult.getInt("ClassCU"));
                c.setRoom(courseWithIDResult.getString("Room"));
                c.setProf(courseWithIDResult.getString("ClassProf"));
                if( courseWithIDResult.getString("ClassCoreq1") != null){
                    c.addCoereq( courseWithIDResult.getString("ClassCoreq1") );
                }
                if( courseWithIDResult.getString("ClassCoreq2") != null){
                    c.addCoereq( courseWithIDResult.getString("ClassCoreq2") );
                }
                if( courseWithIDResult.getString("ClassCoreq3") != null){
                    c.addCoereq( courseWithIDResult.getString("ClassCoreq3") );
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return c;
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
     * DOES SOME MAGIC: removes classes already registered for in DB from list.
     * @return schedule as a string array for updating registerList data.
     */
    public String[] getScheduleAsStringArray(){

        List<String> termScheduleStringArray = new ArrayList<>();

        Iterator<Course> I = T1_Schedule.getCoursesInSchedule().iterator();
        while( I.hasNext() ){
            termScheduleStringArray.add( I.next().toString() );
        }

        for( Course c : T1_Schedule_DB.getCoursesInSchedule() ){
            termScheduleStringArray.remove(c.toString());
        }

        I = T2_Schedule.getCoursesInSchedule().iterator();
        while( I.hasNext() ){
            termScheduleStringArray.add( I.next().toString() );
        }

        for( Course c : T2_Schedule_DB.getCoursesInSchedule() ){
            termScheduleStringArray.remove(c.toString());
        }

        return termScheduleStringArray.toArray(new String[termScheduleStringArray.size()]);
    }

    /**
     * Adds a course based on where it's at in the viewLIST to it's corresponding schedule.
     * DOESN'T COMMIT THE SCHEDULE TO DATABASE UNTIL REGISTER BUTTON IS PRESSED.
     * @param indexOfCourseInClassView index of where the course is at within viewClassList
     * @return null if class adds successfully, error message otherwise
     */
    public String addToSchedule(int indexOfCourseInClassView){

        String error = null;
        Course c = this.viewClassList.get(indexOfCourseInClassView);

        if( c.getTerm().equals("1") ){

            if( !T2_Schedule.contains(c.getName()) ){
                error = T1_Schedule.addCourse(c);
            } else {
                error = "Already trying to register for this class in a different term.";
            }

        }
        else if( c.getTerm().equals("2") ){

            if( !T1_Schedule.contains(c.getName()) ) {
                error = T2_Schedule.addCourse(c);
            } else {
                error = "Already trying to register for this class in a different term.";
            }
        }

        return error;
    }

    /**
     * Gets a specified course from the database and returns the course in start-finish Room Day format
     * within an array. ALSO POPULATES A COURSE CLASS WITH ALL THE CLASSES SPECIFIED INFORMATION AND ADDS IT TO
     * viewClassList
     * @param course the class to search for.
     * @return an array of Strings containing class time information.
     */
    public String[] getCourseInformation(String course){

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
                if( resultSetOfCourseInfo.getString("ClassCoreq1") != null){
                    c.addCoereq( resultSetOfCourseInfo.getString("ClassCoreq1") );
                }
                if( resultSetOfCourseInfo.getString("ClassCoreq2") != null){
                    c.addCoereq( resultSetOfCourseInfo.getString("ClassCoreq2") );
                }
                if( resultSetOfCourseInfo.getString("ClassCoreq3") != null){
                    c.addCoereq( resultSetOfCourseInfo.getString("ClassCoreq3") );
                }
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
    public String[] populateCourseList(String nsid){
        String getDegreeReqSQL = "SELECT ClassName FROM DegreeReq";
        String getTakenClasses = "SELECT * FROM Completed WHERE NSID = ?";

        List<String> classNames = new ArrayList<>();
        List<String> classesTaken = new ArrayList<>();
        List<String> coReqsThatNeedToBeDisplayed = new ArrayList<>();

        try {
            Statement myStatForCourses = this.connection.createStatement();
            PreparedStatement myStatForCoursesTaken = this.connection.prepareStatement(getTakenClasses);
            myStatForCoursesTaken.setString(1, nsid);
            // Execute SQL query
            ResultSet resultSetOfDegreeRequirementCourses = myStatForCourses.executeQuery(getDegreeReqSQL);
            ResultSet resultSetOfClassesTaken = myStatForCoursesTaken.executeQuery();
            
            // Get classes taken BEGIN ---------------------------------------------------------------------------------
            while(resultSetOfClassesTaken.next()){
                int index = 1;

                while(resultSetOfClassesTaken.getString(index) != null){
                    classesTaken.add(resultSetOfClassesTaken.getString(index));
                    index++;
                }
            }
            // Get classes taken END -----------------------------------------------------------------------------------

            // Get User List of Special Classes Taken BEGIN ------------------------------------------------------------
            String getUsersSpecialClassesTakenSQL = "SELECT * FROM Users WHERE NSID = ?";
            PreparedStatement myStatForSpecialClassesTaken = this.connection.prepareStatement(getUsersSpecialClassesTakenSQL);
            myStatForSpecialClassesTaken.setString(1, nsid);
            ResultSet specialClassesTakenResultSet = myStatForSpecialClassesTaken.executeQuery();
            specialClassesTakenResultSet.next();
            String stream = specialClassesTakenResultSet.getString("Stream");
            // Get User List of Special Classes Taken END --------------------------------------------------------------

            // LOOPS THROUGH ALL DEGREE REQUIREMENT COURSES
            while(resultSetOfDegreeRequirementCourses.next()){

                if( resultSetOfDegreeRequirementCourses.getString("ClassName").equals("ScienceElective") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("JuniorHumanities") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("SeniorHumanities") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("Complementary") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec1") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec2") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec3") ||
                    resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec4")){

                    String getSpecialCourseListSQL = null;
                    Statement myStatSpecialCourseList;
                    ResultSet myResultSpecialCourseList;

                    boolean specialClassTaken = false;
                    if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("ScienceElective")) {
                        if( specialClassesTakenResultSet.getBoolean("ScienceElective") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM ScienceElective";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("JuniorHumanities")) {
                        if( specialClassesTakenResultSet.getBoolean("JuniorHumanities") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM JuniorHumanities";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("SeniorHumanities")) {
                        if( specialClassesTakenResultSet.getBoolean("SeniorHumanities") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM SeniorHumanities";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("Complementary")) {
                        if( specialClassesTakenResultSet.getBoolean("Complementary") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM Complementary";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec")) {
                        if( specialClassesTakenResultSet.getBoolean("StreamElec") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM StreamElec WHERE Stream = '" + stream + "'";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec1")) {
                        if( specialClassesTakenResultSet.getBoolean("StreamElec1") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM StreamElec1 WHERE Stream = '" + stream + "'";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec2")) {
                        if( specialClassesTakenResultSet.getBoolean("StreamElec2") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM StreamElec2 WHERE Stream = '" + stream + "'";
                    }
                    else if (resultSetOfDegreeRequirementCourses.getString("ClassName").equals("StreamElec3")) {
                        if( specialClassesTakenResultSet.getBoolean("StreamElec3") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM StreamElec3 WHERE Stream = '" + stream + "'";
                    }
                    else {
                        if( specialClassesTakenResultSet.getBoolean("StreamElec4") ) specialClassTaken = true;
                        else getSpecialCourseListSQL = "SELECT ClassName FROM StreamElec4 WHERE Stream = '" + stream + "'";
                    }

                    // If the special type hasn't been taken BEGIN -----------------------------------------------------
                    if( !specialClassTaken ) {
                        myStatSpecialCourseList = this.connection.createStatement();
                        myResultSpecialCourseList = myStatSpecialCourseList.executeQuery(getSpecialCourseListSQL);

                        while (myResultSpecialCourseList.next()) {

                            if (courseShouldBeAddedToListings(myResultSpecialCourseList, classesTaken, classNames, coReqsThatNeedToBeDisplayed, nsid)) {
                                if (!classNames.contains(myResultSpecialCourseList.getString("ClassName"))) {
                                    classNames.add(myResultSpecialCourseList.getString("ClassName"));
                                }
                            }

                        }
                    }
                    // If the special type hasn't been taken END -------------------------------------------------------

                }
                else{
                    // Check if regular class from degree requirements needs to be displayed BEGIN ---------------------
                    if( courseShouldBeAddedToListings(resultSetOfDegreeRequirementCourses, classesTaken, classNames, coReqsThatNeedToBeDisplayed, nsid) ){
                        if( !classNames.contains(resultSetOfDegreeRequirementCourses.getString("ClassName")) ){
                            classNames.add(resultSetOfDegreeRequirementCourses.getString("ClassName"));
                        }
                    }
                    // Check if regular class from degree requirements needs to be displayed END -----------------------
                }
            }

            // Add COREQs to list of classes to display
            for( String c : coReqsThatNeedToBeDisplayed ){
                if( !classNames.contains(c) ){
                    classNames.add(c);
                }
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        Collections.sort(classNames);
        return classNames.toArray(new String[classNames.size()]);
    }

    /**
     * Checks to see if a given course from DEGREE REQUIREMENTS needs to be displayed.
     * @param resultSetOfCourseToSeeIfItShouldBeAdded A result from the database that contains the name of a class that
     *                                                may need to be be displayed.
     * @param classesTaken A list of classes that the given nsid has already taken.
     * @param classNames A list of classes in string format that will be displayed in the classList JList.
     * @param nsid the nsid of the person logged in.
     * @return true if the class needs to be added to classNames, false otherwise
     */
    private boolean courseShouldBeAddedToListings(ResultSet resultSetOfCourseToSeeIfItShouldBeAdded, List<String> classesTaken,
                                                  List<String> classNames, List<String> coReqsThatNeedToBeDisplayed, String nsid){

        boolean shouldClassBeAdded = false;

        try{
            String getCourseInfoSQL = "SELECT * FROM Classes WHERE ClassName = ?";
            PreparedStatement myStatCourseInfo = this.connection.prepareStatement(getCourseInfoSQL);
            myStatCourseInfo.setString(1, resultSetOfCourseToSeeIfItShouldBeAdded.getString("ClassName"));
            ResultSet myResultCourseInfo = myStatCourseInfo.executeQuery();

            // CLASS HASN'T BEEN TAKEN
            if( !classesTaken.contains(resultSetOfCourseToSeeIfItShouldBeAdded.getString("ClassName")) ) {

                int index = PREREQPOSITION; // INDEX OF PREREQUISITES FIELD IN TABLE
                boolean haveAllPreReq = true;
                boolean dontNeedCreditUnits = true;
                boolean classExistsInClasses = true;
                boolean areNotAlreadyRegisteredInClass = true;

                if (myResultCourseInfo.next()) { // Makes myResultCourseInfo point at the first index of the result iterator.

                    // Check PREREQUISITES BEGINS --------------------------------------------------------------
                    while ((myResultCourseInfo.getString(index) != null) && (index < PREREQPOSITION + MAXNUMOFPREQ)) {
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

                    if (myResultStudentInfo.next()) {
                        if (myResultCourseInfo.getString("CreditReq") != null) {
                            if (myResultCourseInfo.getInt("CreditReq") > myResultStudentInfo.getInt("CreditUnits")) {
                                dontNeedCreditUnits = false;
                            }
                        }
                    }
                    // Check for 24 CreditUnits Needed END -----------------------------------------------------

                    // Checks to see if the person is currently registered in the class BEGIN ------------------
                    String getStudentsT1CoursesSQL = "SELECT * FROM Taking_T1 WHERE NSID = ?";
                    String getStudentsT2CoursesSQL = "SELECT * FROM Taking_T2 WHERE NSID = ?";
                    PreparedStatement myStatT1Info = this.connection.prepareStatement(getStudentsT1CoursesSQL);
                    PreparedStatement myStatT2Info = this.connection.prepareStatement(getStudentsT2CoursesSQL);
                    myStatT1Info.setString(1, nsid);
                    myStatT2Info.setString(1, nsid);
                    ResultSet myResultT1Info = myStatT1Info.executeQuery();
                    ResultSet myResultT2Info = myStatT2Info.executeQuery();

                    // CHECK TERM 1
                    while( myResultT1Info.next() ){

                        int i = 2;

                        while( i < (MAXCLASSESYOUCANTAKE + 1) ) {

                            if( myResultT1Info.getString(i) != null ) {

                                int classID = myResultT1Info.getInt(i);
                                String getClassNameFromClassIdSQL = "SELECT * FROM Classes WHERE ClassID = ?";
                                PreparedStatement myStatClassNameFromID = this.connection.prepareStatement(getClassNameFromClassIdSQL);
                                myStatClassNameFromID.setInt(1, classID);
                                ResultSet myResultClassNameFromID = myStatClassNameFromID.executeQuery();

                                if( myResultClassNameFromID.next() ){
                                    // IF YOU ALREADY ENROLLED IN THE CLASSES, DON'T DISPLAY IT
                                    if( myResultClassNameFromID.getString("ClassName").equals(myResultCourseInfo.getString("ClassName")) ){
                                        areNotAlreadyRegisteredInClass = false;
                                        break;
                                    }

                                }
                            }
                            i++;

                        }
                    }

                    // CHECK Term 2
                    while( myResultT2Info.next() ){

                        int i = 2;

                        while( i < (MAXCLASSESYOUCANTAKE + 1) ) {

                            if( myResultT2Info.getString(i) != null ) {

                                int classID = myResultT2Info.getInt(i);
                                String getClassNameFromClassIdSQL = "SELECT * FROM Classes WHERE ClassID = ?";
                                PreparedStatement myStatClassNameFromID = this.connection.prepareStatement(getClassNameFromClassIdSQL);
                                myStatClassNameFromID.setInt(1, classID);
                                ResultSet myResultClassNameFromID = myStatClassNameFromID.executeQuery();

                                if( myResultClassNameFromID.next() ){
                                    // IF YOU ALREADY ENROLLED IN THE CLASSES, DON'T DISPLAY IT
                                    if( myResultClassNameFromID.getString("ClassName").equals(myResultCourseInfo.getString("ClassName")) ){
                                        areNotAlreadyRegisteredInClass = false;
                                        break;
                                    }

                                }
                            }
                            i++;

                        }

                    }
                    // Checks to see if the person is currently registered in the class END --------------------

                } else {
                    classExistsInClasses = false;
                }


                if (haveAllPreReq && dontNeedCreditUnits && classExistsInClasses && areNotAlreadyRegisteredInClass) {
                    shouldClassBeAdded = true;
                    // Add Coreqs of course to be displayed to a list that will be used to add them to the course listings
                    // Add coreqs to list BEGIN ------------------------------------------------------------------------
                    if( myResultCourseInfo.getString("ClassCoreq1") != null ){
                        coReqsThatNeedToBeDisplayed.add( myResultCourseInfo.getString("ClassCoreq1") );
                    }
                    if( myResultCourseInfo.getString("ClassCoreq2") != null ){
                        coReqsThatNeedToBeDisplayed.add( myResultCourseInfo.getString("ClassCoreq2") );
                    }
                    if( myResultCourseInfo.getString("ClassCoreq3") != null ){
                        coReqsThatNeedToBeDisplayed.add( myResultCourseInfo.getString("ClassCoreq3") );
                    }
                    // Add coreqs to list END --------------------------------------------------------------------------
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return shouldClassBeAdded;
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


    /**
     * Checks to make sure before you register that you have all the corequisites that you need for a course.
     * @return returns null on no conflicts, otherwise returns error message.
     */
    public String checkSchedulesForCoreqProblems(){
        String error = null;

        for( Course c : T1_Schedule.getCoursesInSchedule() ){
            if( c.hasCoreqs() ){
                for( String coreq : c.getCoreqs() ){

                    boolean foundCoreq = false;
                    for( Course courseCheck : T1_Schedule.getCoursesInSchedule() ){
                        if( courseCheck.getName().equals(coreq) ){
                            foundCoreq = true;
                        }
                    }

                    for( Course courseCheck : T2_Schedule.getCoursesInSchedule() ){
                        if( courseCheck.getName().equals(coreq) ){
                            foundCoreq = true;
                        }
                    }

                    if( !foundCoreq ){
                        if( error == null ) error = "Error: " + c.getName() + " needs " + coreq + " as a corequisite.\n";
                        else error = error + "Error: " + c.getName() + " needs " + coreq + " as a corequisite.\n";
                    }

                }
            }
        }

        for( Course c : T2_Schedule.getCoursesInSchedule() ){
            if( c.hasCoreqs() ){
                for( String coreq : c.getCoreqs() ){

                    boolean foundCoreq = false;
                    for( Course courseCheck : T2_Schedule.getCoursesInSchedule() ){
                        if( courseCheck.getName().equals(coreq) ){
                            foundCoreq = true;
                        }
                    }

                    for( Course courseCheck : T1_Schedule.getCoursesInSchedule() ){
                        if( courseCheck.getName().equals(coreq) ){
                            foundCoreq = true;
                        }
                    }

                    if( !foundCoreq ){
                        if( error == null ) error = "Error: " + c.getName() + " needs " + coreq + " as a corequisite.\n";
                        else error = error + "Error: " + c.getName() + " needs " + coreq + " as a corequisite.\n";
                    }

                }
            }
        }

        return error;
    }


    /**
     * Adds T1_schedule and T2_Schedule to database (Taking_T1 and Taking_T2) if there is no co requisite conflicts.
     * @param nsid current person logged in.
     * @return null on success, otherwise returns error message.
     */
    public String addRegisterListToDatabase(String nsid){

        String error = null;
        Schedule Temp1 = new Schedule();
        Schedule Temp2 = new Schedule();

        // Gets list of courses not already registered in.
        for( Course c : T1_Schedule.getCoursesInSchedule() ){
            if( !T1_Schedule_DB.contains(c.getName()) ){
                Temp1.addCourse(c);
            }
        }

        // Gets list of courses not already registered in.
        for( Course c: T2_Schedule.getCoursesInSchedule() ){
            if( !T2_Schedule_DB.contains(c.getName()) ){
                Temp2.addCourse(c);
            }
        }

        if( (error = checkSchedulesForCoreqProblems()) == null){

            for( Course c : Temp1.getCoursesInSchedule() ){
                error = addCourseToDB(c, nsid);
            }
            for( Course c : Temp2.getCoursesInSchedule() ){
                error = addCourseToDB(c, nsid);
            }
        }

        getSchedulesFromDB(nsid);

        return error;
    }


    /**
     * Adds a course to the database for the currently logged in user
     * @param c is the course to be added.
     * @param nsid current person logged in.
     * @return returns null on complete, else return error message.
     */
    public String addCourseToDB(Course c, String nsid){

        String error = null;

        try {
            int courseID = c.getClassID();
            String term = "T" + c.getTerm();

            String addCourseToDataBaseSQL = "SELECT * FROM Taking_" + term + " WHERE NSID = ?";
            PreparedStatement addCourseToDBStatement = this.connection.prepareStatement(addCourseToDataBaseSQL,
                                                                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                                                        ResultSet.CONCUR_UPDATABLE);
            addCourseToDBStatement.setString(1, nsid);
            ResultSet addCourseToDBResult = addCourseToDBStatement.executeQuery();

            if( addCourseToDBResult.next() ){

                int i = 2;
                while( i < (MAXCLASSESYOUCANTAKE + 1) ){
                    if( addCourseToDBResult.getString(i) == null ){
                        addCourseToDBResult.updateInt(i, courseID);
                        addCourseToDBResult.updateRow();
                        updateCreditsEnrolled(c, nsid);
                        error = null;
                        break;
                    } else {
                        error = "Max courses exceeded.";
                    }
                    i++;
                }
            } else {
                error = "Couldn't find user in Taking_T? Table.";
            }
            addCourseToDBStatement.close();
            addCourseToDBResult.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return error;
    }


    /**
     * updates RegisteredCU_T? in Users table for given term that c is in.
     * @param c is course to add credit units from.
     * @return true on success, false otherwise.
     */
    public boolean updateCreditsEnrolled(Course c, String nsid){

        boolean successStatus = true;

        try {
            String addUpdateRegisteredCreditUnitsSQL = "SELECT * FROM Users WHERE NSID = ?";
            PreparedStatement updateCUStatement = this.connection.prepareStatement(addUpdateRegisteredCreditUnitsSQL,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            updateCUStatement.setString(1, nsid);

            ResultSet updateCUResults = updateCUStatement.executeQuery();

            if( updateCUResults.next() ){
                updateCUResults.updateInt("RegisteredCU_T" + c.getTerm(), (updateCUResults.getInt("RegisteredCU_T" + c.getTerm()) + c.getClassCU() ) );
                updateCUResults.updateRow();
            } else {
                successStatus = false;
            }
            updateCUStatement.close();
            updateCUResults.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return successStatus;
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


        /*
        try {
            Statement myStat = BDSM_Driver.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            // Execute SQL query
            ResultSet myRes = myStat.executeQuery("SELECT * from Taking_T1 WHERE NSID = 'kpb637'");
            // Process the result set
            while(myRes.next()){
                myRes.updateInt(3,157);
                myRes.updateRow();
                System.out.println(myRes.getString("NSID"));
                myRes.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        */

    }
}
