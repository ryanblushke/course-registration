/**
 * Created by kevin on 2017-09-28.
 * NSID: kpb637
 * Student#: 11173877
 */
public class Person {

    private String name;
    private String phoneNumber;
    private String address;

    public Person(String name, String address, String phoneNumber){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * default constructor uninitialized
     */
    public Person(){
        this.name = "";
        this.address = "";
        this.phoneNumber = "";
    }


    /**
     * gets the current persons name
     * @return the persons name
     */
    public String getName(){
        return name;
    }

    /**
     * sets the persons name
     * @param name String with the persons name in it
     * @return true if named changed, false otherwise
     */
    public boolean setName(String name){
        if(name instanceof String){
            this.name = name;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * gets the telephone number of the person
     * @return the persons telephone number
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }

    /**
     * sets the phoneNumber of the person
     * @param phoneNumber String with persons phone number in it.
     * @return true if phone number changed, false otherwise
     */
    public boolean setPhoneNumber(String phoneNumber){

        if(phoneNumber instanceof String) {
            this.phoneNumber = phoneNumber;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * gets persons address
     * @return String of persons address
     */
    public String getAddress(){
        return address;
    }

    /**
     * sets a persons address.
     * @param address String of persons address
     * @return true if persons address is changed, false otherwise
     */
    public boolean setAddress(String address){

        if(address instanceof String) {
            this.address = address;
            return true;
        }
        else {
            return false;
        }
    }


}
