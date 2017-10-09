/**
 * Created by Kevin on 10/9/2017.
 */

import java.sql.*;
import java.util.Date;

public class Driver {

    String url;
    String username;
    String password;
    Connection connection;

    public Driver(){
        url = "jdbc:mysql://db.cs.usask.ca:3306/cmpt370_bigdsm";
        username = "cmpt370_bigdsm";
        password = "bnxOEOlO7i8Xlv4FWrJc";
    }

    public void connectToDatabase(){
        try{
            connection = DriverManager.getConnection(url,username,password);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public static void main(String[] args) {

        Driver BDSM_Driver = new Driver();
        BDSM_Driver.connectToDatabase();
        //BDSM_Driver.addUser("lml145","smallboi");

        /*
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
        */
    }
}
