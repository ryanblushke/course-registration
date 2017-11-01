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

        if( !classList.contains(c) ){ // DOESN'T CONSTAIN CLASS ALREADY.

            Iterator<Course> I = classList.iterator();

            while( I.hasNext() ){

                Course classFromList = I.next();

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
