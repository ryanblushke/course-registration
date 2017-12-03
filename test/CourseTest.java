import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ryan Blushke
 * NSID: ryb861
 * Student Number: 11177824
 * Course: CMPT 370
 */

/**
 * This is a testing class designed to test key functionality of the Course Class using JUnit
 */
class CourseTest {
    @Test
    void getClassCU() {

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

        assertEquals(3, courseToDrop.getClassCU(), "EP 202 should be 3 CU");

    }

    @Test
    void getName() {
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

        assertEquals("EP 202", courseToDrop.getName(), "EP 202");

    }

}