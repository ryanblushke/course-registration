/**
 * Created by kevin on 2017-09-28.
 * NSIDs: kpb637
 * Student#s: 11173877
 */
public class Person {

    protected String name;
    protected String phoneNumber;
    protected String address;

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

    /**
     * creates a String representation
     * @return a String representation of the person
     */
    public String toString() {
        return "Person{ " + name + ", " + address + ", " + phoneNumber +  " }";
    }


    public static void main(String[] args) {
        Person pleb = new Person("Andy Cheung", "8008 DilliLane Street West","3066308008");

        System.out.println(pleb);

        // Set and get name
        System.out.println("Changing Name");
        pleb.setName("Kevin");
        System.out.println(pleb.getName());
        System.out.println(pleb);

        // Set and get address
        System.out.println("Changing Address");
        pleb.setAddress("Dingus Lane");
        System.out.println(pleb.getAddress());
        System.out.println(pleb);

        // Set and get phoneNumber
        System.out.println("Changing phone number");
        pleb.setPhoneNumber("3066906969");
        System.out.println(pleb.getPhoneNumber());
        System.out.println(pleb);

    }

}
