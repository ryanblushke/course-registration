

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
    private static final int MAXCOMPLETEDCOURSES = 50 + 2; // + 2 for index correction in database

    private String url;
    private String username;
    private String password;
    private Connection connection;
    private LinkedList<Course> viewClassList;
    public Schedule T1_Schedule;
    private Schedule T1_Schedule_DB;
    public Schedule T2_Schedule;
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
    public void updateSchedulesFromDB(String nsid){

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
     * Adds a course based on where it's at in the vJLIST to it's corresponding schedule.
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
     * Gets a specified course from the database and returns the course in toString format
     * within an array. ALSO POPULATES A COURSE CLASS WITH ALL THE CLASSES SPECIFIED INFORMATION AND ADDS IT TO
     * viewClassList JList.
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
                if( resultSetOfCourseInfo.getString("ClassPrereq1") != null ){
                    c.addPrereq( resultSetOfCourseInfo.getString("ClassPrereq1"));
                }
                if( resultSetOfCourseInfo.getString("ClassPrereq2") != null ){
                    c.addPrereq( resultSetOfCourseInfo.getString("ClassPrereq2"));
                }
                if( resultSetOfCourseInfo.getString("ClassPrereq3") != null ){
                    c.addPrereq( resultSetOfCourseInfo.getString("ClassPrereq3"));
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

                            if (shouldCourseBeAddedToListings(myResultSpecialCourseList, classesTaken, classNames, coReqsThatNeedToBeDisplayed, nsid)) {
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
                    if( shouldCourseBeAddedToListings(resultSetOfDegreeRequirementCourses, classesTaken, classNames, coReqsThatNeedToBeDisplayed, nsid) ){
                        if( !classNames.contains(resultSetOfDegreeRequirementCourses.getString("ClassName")) ){
                            classNames.add(resultSetOfDegreeRequirementCourses.getString("ClassName"));
                        }
                    }
                    // Check if regular class from degree requirements needs to be displayed END -----------------------
                }
            }

            // Add COREQs to list of classes to display IF THEY HAVEN'T BEEN TAKEN ALREADY
            for( String c : coReqsThatNeedToBeDisplayed ){
                if( (!classNames.contains(c)) && (!classesTaken.contains(c)) ){
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
     * Gets an array of strings that contains all the classes currently enrolled in and is used to populate
     * classes that a user can drop.
     * @param nsid identification of student.
     * @return array of Courses the student is currently enrolled in.
     */
    public ArrayList<Course> getDroppableCourses(String nsid) {

        String getTakingT1 = "SELECT * FROM Taking_T1 WHERE NSID = ?";
        String getTakingT2 = "SELECT * FROM Taking_T2 WHERE NSID = ?";

        List<Course> classesTakingT1 = new ArrayList<>();
        List<Course> classesTakingT2 = new ArrayList<>();
        List<Course> classesTakingTotal;

        try {
            PreparedStatement statForTakingT1 = this.connection.prepareStatement(getTakingT1);
            statForTakingT1.setString(1, nsid);
            PreparedStatement statForTakingT2 = this.connection.prepareStatement(getTakingT2);
            statForTakingT2.setString(1, nsid);

            // Execute SQL query
            ResultSet resultSetOfTakingT1 = statForTakingT1.executeQuery();
            ResultSet resultSetOfTakingT2 = statForTakingT2.executeQuery();

            // Get classes taking BEGIN
            while(resultSetOfTakingT1.next()){
                int index = 2;

                while (resultSetOfTakingT1.getString(index) != null) {
                    classesTakingT1.add(this.getCourseGivenIDNumber(Integer.valueOf(resultSetOfTakingT1.getString
                            (index))));
                    index++;
                }
            }

            while(resultSetOfTakingT2.next()){
                int index = 2;

                while (resultSetOfTakingT2.getString(index) != null) {
                    classesTakingT2.add(this.getCourseGivenIDNumber(Integer.valueOf(resultSetOfTakingT2.getString
                            (index))));
                    index++;
                }
            }
            // Get classes taking END
        } catch (Exception e) {
            e.printStackTrace();
        }

        classesTakingTotal = classesTakingT1;
        classesTakingTotal.addAll(classesTakingT2);
//        Collections.sort(classesTakingTotal);
        return (ArrayList<Course>) classesTakingTotal;
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
    private boolean shouldCourseBeAddedToListings(ResultSet resultSetOfCourseToSeeIfItShouldBeAdded, List<String> classesTaken,
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

                    String debug = myResultCourseInfo.getString("ClassName");

                    // Check PREREQUISITES BEGINS --------------------------------------------------------------
                    while ((myResultCourseInfo.getString(index) != null) && (index < PREREQPOSITION + MAXNUMOFPREQ)) {
                        // IF YOU DON'T HAVE THE PREREQ, SET HAVE PREREQ TO FALSE
                        if (!classesTaken.contains(myResultCourseInfo.getString(index))) {
                            haveAllPreReq = false;
                            // IF YOU ARE REGISTERS IN THE PREREQ IN FIRST TERM, THEM ASSUME YOU HAVE THE PREREQ.
                            if( T1_Schedule_DB.contains( myResultCourseInfo.getString(index)) ) haveAllPreReq = true;
                            else { break; } // Don't have the prereqs, so keep haveAllPreReq at false.
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

        updateSchedulesFromDB(nsid);

        return error;
    }


    /**
     * Adds a course into the database of a logged in users. The class is added to its corresponding Term within the
     * database. IE Taking_T1 ot Taking_T2.
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
                        addClassUpdateUsersInfo(c, nsid);
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
     * updates RegisteredCU_T(X) in Users table for given term that c is in.
     * updates number of courses a user is registered in for a given term.
     * @param c is course to add credit units from.
     * @return true on success, false otherwise.
     */
    public boolean addClassUpdateUsersInfo(Course c, String nsid){

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
                // TODO: if( !c.getName().contains(" L ")) {
                updateCUResults.updateInt("EnrolledClass", updateCUResults.getInt("EnrolledClass") + 1);
                // TODO: }
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
     * Returns a string array that is used to populate incomplete section of degree progress.
     * @param nsid the current user logged in.
     * @return a string array of course names that haven't been completed.
     */
    public String[] populateIncomplete(String nsid){
        List<String> incomplete = new ArrayList<>();
        List<String> completed = new ArrayList<>();

        try {
            String getDegreeReqSQL = "SELECT ClassName FROM DegreeReq";
            String getTakenClasses = "SELECT * FROM Completed WHERE NSID = ?";

            Statement myStatForCoursesNeeded = this.connection.createStatement();
            ResultSet resultSetOfDegreeRequirementCourses = myStatForCoursesNeeded.executeQuery(getDegreeReqSQL);

            PreparedStatement myStatForCoursesTaken = this.connection.prepareStatement(getTakenClasses);
            myStatForCoursesTaken.setString(1, nsid);
            ResultSet resultSetOfClassesTaken = myStatForCoursesTaken.executeQuery();

            while( resultSetOfDegreeRequirementCourses.next() ){
                incomplete.add( resultSetOfDegreeRequirementCourses.getString("ClassName") );
            }

            if( resultSetOfClassesTaken.next() ) {
                int index = 2;
                while ((index < MAXCOMPLETEDCOURSES) && (resultSetOfClassesTaken.getString(index) != null) ){
                    completed.add(resultSetOfClassesTaken.getString(index));
                    index++;
                }
            }

            // Remove completed classes from incomplete.
            for( String name : completed ){
                if( incomplete.contains(name) ) incomplete.remove(name);
            }

        } catch ( Exception e ){
            e.printStackTrace();
        }

        Collections.sort(incomplete);

        return incomplete.toArray(new String[incomplete.size()]);
    }


    /**
     * Returns a string array that i sused to populate the complete section of degree progress.
     * @param nsid the current user logged in.
     * @return a string array of course names that have been completed.
     */
    public String[] populateComplete(String nsid){
        List<String> completed = new ArrayList<>();

        try {
            String getTakenClasses = "SELECT * FROM Completed WHERE NSID = ?";
            PreparedStatement myStatForCoursesTaken = this.connection.prepareStatement(getTakenClasses);
            myStatForCoursesTaken.setString(1, nsid);
            ResultSet resultSetOfClassesTaken = myStatForCoursesTaken.executeQuery();

            if( resultSetOfClassesTaken.next() ) {
                int index = 2;
                while ((index < MAXCOMPLETEDCOURSES) && (resultSetOfClassesTaken.getString(index) != null) ){
                    completed.add(resultSetOfClassesTaken.getString(index));
                    index++;
                }
            }

        } catch ( Exception e ){
            e.printStackTrace();
        }

        Collections.sort(completed);
        return completed.toArray( new String[completed.size()] );
    }


    /**
     * returns the total degree progress of a student in float% format.
     * @param nsid the current user logged in.
     * @return a float that gives the percentage of degree progress for the student logged in.
     */
    public float updateProgressBar(String nsid){

        float totalClasses = 0;

        try {
            String getDegreeReqSQL = "SELECT ClassName FROM DegreeReq";

            Statement myStatForCoursesNeeded = this.connection.createStatement();
            ResultSet resultSetOfDegreeRequirementCourses = myStatForCoursesNeeded.executeQuery(getDegreeReqSQL);

            while( resultSetOfDegreeRequirementCourses.next() ){
                totalClasses++;
            }
        } catch ( Exception e ){
            e.printStackTrace();
        }

        String[] complete = populateComplete(nsid);

        return (complete.length / totalClasses ) * 100;
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

    //this code is absolutely brutal but it works

    /**
     *
     * @param nsid pass a valid NSID already registered in the system
     * @param ClassID the CLASSID will be removed from their list of takin classes for t1
     * @return VOID nothing happens if it isn't in there taking courses
     */
    public void removeClass_t1(String nsid, int ClassID){
        System.out.println("fuck this bitch I'm out");
        try {
            String classReplacement1 = "UPDATE Taking_T1 SET T1 =NULL WHERE (NSID = ? AND T1= ?)" ;
            String classReplacement2 = "UPDATE Taking_T1 SET T2 =NULL WHERE (NSID = ? AND T2= ?)" ;
            String classReplacement3 = "UPDATE Taking_T1 SET T3 =NULL WHERE (NSID = ? AND T3= ?)" ;
            String classReplacement4 = "UPDATE Taking_T1 SET T4 =NULL WHERE (NSID = ? AND T4= ?)" ;
            String classReplacement5 = "UPDATE Taking_T1 SET T5 =NULL WHERE (NSID = ? AND T5= ?)" ;
            String classReplacement6 = "UPDATE Taking_T1 SET T6 =NULL WHERE (NSID = ? AND T6= ?)" ;
            String classReplacement7 = "UPDATE Taking_T1 SET T7 =NULL WHERE (NSID = ? AND T7= ?)" ;
            String classReplacement8 = "UPDATE Taking_T1 SET T8 =NULL WHERE (NSID = ? AND T8= ?)" ;
            String classReplacement9 = "UPDATE Taking_T1 SET T9 =NULL WHERE (NSID = ? AND T9= ?)" ;
            String classReplacement10 = "UPDATE Taking_T1 SET T10 =NULL WHERE (NSID = ? AND T10= ?)" ;
            String classReplacement11 = "UPDATE Taking_T1 SET T11 =NULL WHERE (NSID = ? AND T11= ?)" ;
            String classReplacement12 = "UPDATE Taking_T1 SET T12 =NULL WHERE (NSID = ? AND T12= ?)" ;
            String classReplacement13 = "UPDATE Taking_T1 SET T13 =NULL WHERE (NSID = ? AND T13= ?)" ;
            String classReplacement14 = "UPDATE Taking_T1 SET T14 =NULL WHERE (NSID = ? AND T14= ?)" ;
            String classReplacement15 = "UPDATE Taking_T1 SET T15 =NULL WHERE (NSID = ? AND T15= ?)" ;
            String classReplacement16 = "UPDATE Taking_T1 SET T16 =NULL WHERE (NSID = ? AND T16= ?)" ;
            String classReplacement17 = "UPDATE Taking_T1 SET T17 =NULL WHERE (NSID = ? AND T17= ?)" ;

            PreparedStatement testReplace = this.connection.prepareStatement(classReplacement1);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement2);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement3);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement4);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement5);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement6);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement7);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement8);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement9);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement10);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement11);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement12);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement13);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement14);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement15);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement16);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement17);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param nsid pass a valid NSID already registered in the system
     * @param ClassID the CLASSID will be removed from their list of taking classes for t2
     * @return VOID
     */
    public void removeClass_t2(String nsid, int ClassID){
        System.out.println("fuck this bitch I'm out");
        try {
            String classReplacement1 = "UPDATE Taking_T2 SET T1 =NULL WHERE (NSID = ? AND T1= ?)" ;
            String classReplacement2 = "UPDATE Taking_T2 SET T2 =NULL WHERE (NSID = ? AND T2= ?)" ;
            String classReplacement3 = "UPDATE Taking_T2 SET T3 =NULL WHERE (NSID = ? AND T3= ?)" ;
            String classReplacement4 = "UPDATE Taking_T2 SET T4 =NULL WHERE (NSID = ? AND T4= ?)" ;
            String classReplacement5 = "UPDATE Taking_T2 SET T5 =NULL WHERE (NSID = ? AND T5= ?)" ;
            String classReplacement6 = "UPDATE Taking_T2 SET T6 =NULL WHERE (NSID = ? AND T6= ?)" ;
            String classReplacement7 = "UPDATE Taking_T2 SET T7 =NULL WHERE (NSID = ? AND T7= ?)" ;
            String classReplacement8 = "UPDATE Taking_T2 SET T8 =NULL WHERE (NSID = ? AND T8= ?)" ;
            String classReplacement9 = "UPDATE Taking_T2 SET T9 =NULL WHERE (NSID = ? AND T9= ?)" ;
            String classReplacement10 = "UPDATE Taking_T2 SET T10 =NULL WHERE (NSID = ? AND T10= ?)" ;
            String classReplacement11 = "UPDATE Taking_T2 SET T11 =NULL WHERE (NSID = ? AND T11= ?)" ;
            String classReplacement12 = "UPDATE Taking_T2 SET T12 =NULL WHERE (NSID = ? AND T12= ?)" ;
            String classReplacement13 = "UPDATE Taking_T2 SET T13 =NULL WHERE (NSID = ? AND T13= ?)" ;
            String classReplacement14 = "UPDATE Taking_T2 SET T14 =NULL WHERE (NSID = ? AND T14= ?)" ;
            String classReplacement15 = "UPDATE Taking_T2 SET T15 =NULL WHERE (NSID = ? AND T15= ?)" ;
            String classReplacement16 = "UPDATE Taking_T2 SET T16 =NULL WHERE (NSID = ? AND T16= ?)" ;
            String classReplacement17 = "UPDATE Taking_T2 SET T17 =NULL WHERE (NSID = ? AND T17= ?)" ;

            PreparedStatement testReplace = this.connection.prepareStatement(classReplacement1);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement2);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement3);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement4);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement5);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement6);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement7);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement8);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement9);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement10);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement11);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement12);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement13);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement14);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement15);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement16);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();

            testReplace = this.connection.prepareStatement(classReplacement17);
            testReplace.setString(1, nsid);
            testReplace.setInt(2, ClassID);
            testReplace.executeUpdate();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Determines which courses the student is enrolled in must be dropped if dropping a specified course
     * @param dropper the course being dropped
     * @param prevDropper the previous course in the recursive chain
     * @return LinkedList of Courses that must be dropped
     */
    public LinkedList<Course> goodToDrop(Course dropper, Course prevDropper) {

        LinkedList<Course> T1affectedClasses = new LinkedList<>();
        LinkedList<Course> T2affectedClasses = new LinkedList<>();
        LinkedList<Course> totalAffectedCourses = new LinkedList<>();
        if (T1_Schedule_DB.contains(dropper.name)) {
            // loop over every course in term one and check their corec list for dropper.name
            // if there is a hit we need to add it to a list T1 affected.
            // at the end we need to do the same check on every item in the list to make sure no other item in the list
            // once we get we get to then of all classes in the schedule we need to check the prereq
            // FOR TERM 1 CLASSES ONLY
            // Check if any of the classes in the list are prereqs for classes in term 2 add the class to the term2 affected list
            // once all check are completed
            // check the term 2 classes to see if any of their corecs are in the T2affected list if so add it to the list
            // once done issue the warning to the user and return the concatted lists (T1 + T2) affected courses
            for (Course courseInT1 : T1_Schedule_DB.getCoursesInSchedule()) {
                for (String coreq : courseInT1.coreq) {
                    if (coreq.equals(dropper.name)) {
                        T1affectedClasses.add(courseInT1);
                    }
                }
            }

            for (Course courseInT2 : T2_Schedule_DB.getCoursesInSchedule()) {
                for (String prereq : courseInT2.getPrereq()) {
                    if (prereq.equals(dropper.name)) {
                            T2affectedClasses.add(courseInT2);
                    }
                }
            }

        }
        if (T2_Schedule.contains(dropper.name)) {
            //repeat same as above for loop above
            for (Course courseInT2 : T2_Schedule_DB.getCoursesInSchedule()) {
                for (String coreq : courseInT2.coreq) {
                    if (coreq.equals(dropper.name)) {
                        T2affectedClasses.add(courseInT2);
                    }
                }
            }
        }


        for( Course affectedCourse : T1affectedClasses ) {

            // check used to make sure two classes with each other listed as coreqs do not create an infinite loop
            if( !affectedCourse.getName().equals(prevDropper.getName()) ) {
                totalAffectedCourses.add(affectedCourse);
                totalAffectedCourses.addAll(goodToDrop(affectedCourse, dropper));
            }

        }
        for( Course affectedCourse : T2affectedClasses ){

            // check used to make sure two classes with each other listed as coreqs do not create an infinite loop
            if( !affectedCourse.getName().equals(prevDropper.getName()) ) {
                totalAffectedCourses.add(affectedCourse);
                totalAffectedCourses.addAll(goodToDrop(affectedCourse, dropper));
            }

        }

        return totalAffectedCourses;

    }
}
