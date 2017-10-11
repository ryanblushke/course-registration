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
import java.util.Date;

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
     * Test method for connecting to our database and Querying or Updating a table
     * @param args not used
     */
    public static void main(String[] args) {

        Driver BDSM_Driver = new Driver();
        BDSM_Driver.connectToDatabase();
        //BDSM_Driver.addUser("lml145","smallboi");
        Boolean userExists = BDSM_Driver.authenticateUser("lml145","smallboi");
        System.out.println(userExists);
        userExists = BDSM_Driver.authenticateUser("kpb637","cats");
        System.out.println(userExists);


        try {
            Statement myStat = BDSM_Driver.connection.createStatement();
            // Execute SQL query
            ResultSet myRes = myStat.executeQuery("select * from Classes");
            // Process the result set
            while(myRes.next()){
                System.out.println(myRes.getString("ClassName") + ", " + myRes.getString("ClassCode"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
