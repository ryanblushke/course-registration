import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ryan Blushke
 * NSID: ryb861
 * Student Number: 11177824
 * Course: CMPT 370
 */

/**
 * This is a testing class designed to test key functionality of the Driver Class using JUnit
 */
class DriverTest {

    @Test
    void getCourseGivenIDNumber() {
        Driver testDriver = new Driver();
        testDriver.connectToDatabase();
        assertFalse(testDriver.getCourseGivenIDNumber(4).getName().isEmpty(), "Course ID 4 should have a name");
        assertEquals(1, testDriver.getCourseGivenIDNumber(4).getClassCU(), "Course ID 4 should be 1 CU");
        assertEquals("1", testDriver.getCourseGivenIDNumber(4).getTerm(), "Course ID 4 should be Term 1");
        assertEquals(1600, testDriver.getCourseGivenIDNumber(4).getStartTime(), "Course ID 4 should start at 16:00");
        assertEquals(1650, testDriver.getCourseGivenIDNumber(4).getEndTime(), "Course ID 4 should end at 16:50");
        assertEquals("TR", testDriver.getCourseGivenIDNumber(4).getDays(), "Course ID 4 should be on TR");
        assertEquals("THORV 105", testDriver.getCourseGivenIDNumber(4).getRoom(), "Course ID 4 should be in THORV " +
                "105");
        assertEquals("BRUCE S", testDriver.getCourseGivenIDNumber(4).getProf(), "Course ID 4 should be taught by " +
                "BRUCE S");
        assertTrue(testDriver.getCourseGivenIDNumber(4).getPrereq().isEmpty(), "Course ID 4 should not have any " +
                "PreReqs");
        assertFalse(testDriver.getCourseGivenIDNumber(4).getCoreqs().isEmpty(), "Course ID 4 should have a CoReq");
    }

    @Test
    void authenticateUser() {
        Driver testDriver = new Driver();
        testDriver.connectToDatabase();

        assertTrue(testDriver.authenticateUser("Test_1", "test1"));
        assertTrue(testDriver.authenticateUser("Test_2", "test2"));
        assertTrue(testDriver.authenticateUser("Test_3", "test3"));
        assertTrue(testDriver.authenticateUser("Test_4", "test4"));
        assertTrue(testDriver.authenticateUser("Test_5", "test5"));
        assertTrue(testDriver.authenticateUser("Test_6", "test6"));

    }

    @Test
    void goodToDrop() {
        Driver testDriver = new Driver();
        testDriver.connectToDatabase();

        Course courseToDrop = new Course("EP 202", "ALEXANDER K", 0, 0);
        courseToDrop.setClassCU(3);
        courseToDrop.setClassID(122);
        courseToDrop.setDays("MWF");
        courseToDrop.setTerm("1");
        courseToDrop.setRoom("PHYSICS 103");
        courseToDrop.setStartTime(1030);
        courseToDrop.setEndTime(1120);
        courseToDrop.addCoereq("EP L 202");
        courseToDrop.addPrereq("MATH 124");
        courseToDrop.addPrereq("PHYSICS 155");

//        testDriver.T1_Schedule_DB.addCourse();
//        testDriver.T1_Schedule_DB.addCourse();


        assertEquals(0, testDriver.goodToDrop(courseToDrop, null).size(), "Dropping EP 202 should not affect anything" +
                " else.");

    }

}