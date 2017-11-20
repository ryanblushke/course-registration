import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by kevin on 2017-10-31.
 */
public class Schedule {

    private LinkedList<Course> classList;

    public Schedule(){
        classList = new LinkedList<>();
    }

    public LinkedList<Course> getCoursesInSchedule(){
        return classList;
    }


    /**
     * Gets a class from the schedule with the given course name.
     * @param courseName of course that we are looking for.
     * @return course object if found, else returns null.
     */
    public Course getCourseFromSchedule( String courseName){

        for( Course c : classList ){
            if( c.name.equals(courseName) ){
                return c;
            }
        }

        return null;
    }

    /**
     * Determines if a course is in the schedule or not based on the class name
     * @param name string of course name. IE, GE 101
     * @return return true if c is in the list, false otherwise.
     */
    public boolean contains(String name){
        boolean isInList = false;

        Iterator<Course> I = classList.iterator();
        while( I.hasNext() ){
            if( I.next().getName().equals(name) ){
                isInList = true;
            }
        }
        return isInList;
    }

    /**
     * Removes course from a given schedule.
     * @param c course to be removed
     * @return true if successful, false otherwise.
     */
    public boolean removeCourse(Course c){

        return this.classList.remove(c);
    }

    /**
     * Adds a course to someones schedule
     * @param c course to add to schedule
     * @return true if course was added, false if there are schedule conflicts
     */
    public String addCourse(Course c){

        String returnMessage = null;

        if( (returnMessage = this.checkForScheduleConflict(c)) == null ){
            this.classList.add(c);
        }

        return returnMessage;

    }

    /**
     * Checks for schedule conflicts.
     * @param c course to check with classList
     * @return true if there is a conflict, false if there isn't
     */
    public String checkForScheduleConflict(Course c){

        String scheduleConflict = null;

        // THis means it's a lab. Check to make sure they are scheduled in the class first
        if( c.getName().contains(" L ")) {
            boolean error = true;
            String nameOfCourseForLab = c.getName().replace(" L ", " ");
            for (Course cInSchedule : classList) {
                if( cInSchedule.getName().equals(nameOfCourseForLab) ){
                    error = false;
                }
            }

            if( error ) scheduleConflict = "Error: Register in class before lab or register for lab in proper term.";
        }


        if( classList.contains(c) && (scheduleConflict == null) ) { // IF IT DOES CONTAIN CLASS ALREADY.
            scheduleConflict = "Error: Already registering/registered for this class.";
        }

        Iterator<Course> I = classList.iterator();

        while( I.hasNext() ){

            Course classFromList = I.next();

            // IF TRYING TO ADD THE SAME CLASS BUT AT DIFFERENT TIMES
            if( classFromList.getName().equals(c.getName()) && (scheduleConflict == null) ){
                scheduleConflict = "Error: Already registering/registered for this class.";
            }

            // THis elaborate shit is for finding what days each class is on and comparing there times if they are
            // on the same day.
            //------------------------------------------------------------------------------------
            String moreDays, lessDays;
            if( classFromList.getDays().length() > c.getDays().length() ){
                moreDays = classFromList.getDays();
                lessDays = c.getDays();
            }
            else{
                moreDays = c.getDays();
                lessDays = classFromList.getDays();
            }


            if( moreDays.contains(lessDays) ){

                // Scheduled at the same time
                if( classFromList.getStartTime() == c.getStartTime() && (scheduleConflict == null) ) {
                    scheduleConflict = "Error: " + classFromList.getName() + " already scheduled at that time.";
                }
                // Scheduled during other class
                else if( (classFromList.getStartTime() >= c.getStartTime()) && (classFromList.getStartTime() <= c.getEndTime()) && (scheduleConflict == null) ) {
                    scheduleConflict = "Error: " + classFromList.getName() + " overlaps with the time of " + c.getName();
                }
                // Scheduled right when a class ends
                else if( classFromList.getEndTime() == c.getStartTime() && (scheduleConflict == null) ){
                    scheduleConflict = "Error: " + classFromList.getName() + " overlaps with the time of " + c.getName();
                }
                else{}
            }
            //------------------------------------------------------------------------------------

        }


        return scheduleConflict;
    }

}
