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
    public boolean addCourse(Course c){

        boolean courseAdded = false;

        if( this.checkForScheduleConflict(c) == false ){
            this.classList.add(c);
            courseAdded = true;
        }

        return courseAdded;

    }

    /**
     * Checks for schedule conflicts.
     * @param c course to check with classList
     * @return true if there is a conflict, false if there isn't
     */
    public boolean checkForScheduleConflict(Course c){

        boolean scheduleConflict = false;

        if( classList.contains(c) ) { // IF IT DOESN'T CONTAIN CLASS ALREADY.
            scheduleConflict = true;
        }

        Iterator<Course> I = classList.iterator();

        while( I.hasNext() ){

            Course classFromList = I.next();

            // IF TRYING TO ADD THE SAME CLASS BUT AT DIFFERENT TIMES
            if( classFromList.getName().equals(c.getName()) ){
                scheduleConflict = true;
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
            //------------------------------------------------------------------------------------

            if( moreDays.contains(lessDays) ){

                // Scheduled at the same time
                if( classFromList.getStartTime() == c.getStartTime() ) {
                    scheduleConflict = true;
                }
                // Scheduled during other class
                else if( (classFromList.getStartTime() < c.getStartTime()) && (classFromList.getEndTime() > c.getEndTime()) ){
                    scheduleConflict = true;
                }
                // Scheduled right when a class ends
                else if( classFromList.getEndTime() == c.getStartTime() ){
                    scheduleConflict = true;
                }
                else{}
            }

        }


        return scheduleConflict;
    }

}
