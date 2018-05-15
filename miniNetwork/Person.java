package miniNetwork;

public class Person {

    private String name;
    private int age=0;
    private char gender;
    private String profilePicture = "no profile picture";
    private String status = "no status";
    private String state = "no state";

    private String userType;

    private Person[] friends = new Person[10];
    private int friendNumber = 0;

    private Person[] classmates= new Person[10];
    private int classmateNumber = 0;


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        if(age<3)
            this.userType = "Young Child";
        else if( age<16)
            this.userType = "Child";

        else
            this.userType = "Adult";
    }


    public String getName() {
        return name;
    }

    public int getAge(){
        return age;
    }

    public char getGender(){
        return gender;
    }

    public void setGender(char gender){
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePicture() {
        //if(profilePicture.equals("no profile picture"))
            return profilePicture;
        //else
         //   return String.format( profilePicture + ".jpg");
    }

    public void setProfilePicture(String profile){
        profilePicture = profile;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getUserType(){
        return userType;
    }

    public Person[] getFriendList(){
        return friends;
    }

    public int getFriendNumber(){
        return friendNumber;
    }

    public void setFriendNumber(int number){
        this.friendNumber = number;
    }

    public void addFriend(Person friend) {
        friends[friendNumber] = friend;
        friendNumber++;
        friend.getFriendList()[friend.getFriendNumber()] = this;
        friend.setFriendNumber(friend.getFriendNumber()+1);
    }

    public boolean isFriend(String name) {
        boolean selected = false;
        for(int i=0; i< friendNumber; i++) {
            if((friends[i].getName()).equals(name)) {
                selected =true;
                break;
            }
        }
        return selected;
    }

    public void addClassmate(Person classmate){
        classmates[classmateNumber] = classmate;
        classmateNumber++;
    }

    public boolean isClassmate(String name){
        boolean selected = false;
        for(int i=0; i< classmateNumber;i++){
            if((classmates[i].getName()).equals(name)) {
                selected =true;
                break;
            }
        }
        return selected;
    }

    public int getClassmateNumber(){
        return classmateNumber;
    }
    public Person[] getClassmates(){
        return classmates;
    }
    public void decreaseClassmateNumber(){
        classmateNumber--;
    }
}
