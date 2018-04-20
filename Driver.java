/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import exceptions.TooYoungException;

import java.util.Scanner;

/**
 *
 * @author khanhnguyen
 */
public class Driver {


    private Person[] users = new Person[20];
    private static Scanner sc;
    private static Scanner numberSc;

    public void start() throws TooYoungException {
        sc = new Scanner(System.in);
        numberSc = new Scanner(System.in);
        int userNum = 6;

        inputData();

        Person selectedPerson = null;
        Person friend = null;
        String username;
        int age;

        int choice=0;
        do {
            printMenu();

            choice = numberSc.nextInt();

            switch(choice) {
                case 1:
                    listUsers(users , userNum);
                    break;

                case 2:
                    System.out.println("Enter username: ");
                    username = sc.next();
                    System.out.println("Enter age: ");
                    age = sc.nextInt();
                    addUser(users, userNum, username,age);
                    userNum++;

                    if(age<16 && age>2){
                        Scanner input = new Scanner(System.in);
                        Person[] parents = new Adult[2];
                        boolean result= false;
                        int count;
                        System.out.println("The new user is under 16, please add 2 parents !");

                        while(result == false){
                            for(int i=0; i<2 ; i++){
                                System.out.println("input name of parent number "+(i+1));
                            }

                            count=0;

                            if(parents[0].getAge()<16){
                                System.out.println(parents[0].getName()+" cannot have children ( age: " +parents[0].getAge()+")");
                                count++;
                            }

                            if(parents[1].getAge()<16){
                                System.out.println(parents[1].getName()+" cannot have children ( age: " +parents[1].getAge()+")");
                                count++;
                            }

                            if(((Adult)parents[0]).getSpouse() != null && ((Adult)parents[1]).getSpouse() == null){
                                System.out.println(parents[0].getName()+" already get married!!!");
                                count++;
                            }

                            if(((Adult)parents[0]).getSpouse() == null && ((Adult)parents[1]).getSpouse() != null){
                                System.out.println(parents[1].getName()+" already get married!!!");
                                count++;
                            }

                            if(((Adult)parents[0]).getSpouse() != null && ((Adult)parents[1]).getSpouse() != null)
                                if(!(((Adult)parents[0]).getSpouse().getName().equals(parents[1].getName()))){
                                    System.out.println("Both parents belong to different family!!!");
                                    count++;
                                }



                            if(count == 0)
                                result =true;
                        }

                        selectedPerson = selectUser(users, userNum, username);
                        ((Child)selectedPerson).addParent((Adult)parents[0]);
                        ((Child)selectedPerson).addParent((Adult)parents[1]);

                        ((Adult)parents[0]).setSpouse((Adult) parents[1]);
                        ((Adult)parents[1]).setSpouse((Adult) parents[0]);

                    }

                    break;

                case 3:
                    System.out.println("Enter username: ");
                    username = sc.next();
                    selectedPerson = selectUser(users, userNum, username);
                    if(selectedPerson != null){
                        System.out.println("the selected user is: " + selectedPerson.getName());
                    }
                    else
                        System.out.println("the user is not registered yet!!");

                    break;

                case 4:
                    if(selectedPerson != null) {
                        System.out.println("Selected user INFO:\nName: " + selectedPerson.getName()
                                +"\nProfile picture: "+ selectedPerson.getProfilePicture()
                                +"\nStatus: "+selectedPerson.getStatus()
                                +"\nAge: "+ selectedPerson.getAge()
                                +"\nUser Type: "+ selectedPerson.getUserType()
                                +"\nFriend list:");
                        for(int i=0 ; i< selectedPerson.getFriendNumber() ; i++){
                            System.out.print(selectedPerson.getFriendList()[i].getName()+"  ");
                        }
                        System.out.println("\n");

                        if(selectedPerson instanceof Child){
                            System.out.println("Parents: ");
                            for(int i=0 ; i<2 ; i++){
                                System.out.print(((Child) selectedPerson).getParentList()[i].getName()+"  ");
                            }
                            System.out.println("\n\n");
                        }

                        else if(selectedPerson instanceof Adult){
                            System.out.println("Spouse: "+ ((Adult) selectedPerson).getSpouse().getName());
                            System.out.println("Children: ");
                            for(int i=0 ; i<((Adult) selectedPerson).getChildrenNumber() ; i++){
                                System.out.print(((Adult) selectedPerson).getChildrenList()[i].getName()+"  ");
                            }
                            System.out.println("\n\n");
                        }

                    }
                    else
                        System.out.println("You have not selected any users");

                    break;

                case 5:
                    if(selectedPerson != null) {
                        Scanner case5 = new Scanner(System.in);
                        String[] inputs = new String[2];

                        System.out.println("hi "+ selectedPerson.getName());
                        for(int i=0 ; i<2 ; i++){
                            if(i==0)
                                System.out.println("Your new profile picture:  "  );
                            else if(i==1)
                                System.out.println("Your new status: "  );
                            inputs[i] = case5.nextLine();
                        }

                        System.out.println("picture: "+ inputs[0] + "\nstatus: "+ inputs[1]);
                        selectedPerson.setProfilePicture(inputs[0]);
                        selectedPerson.setStatus(inputs[1]);
                    }
                    else
                        System.out.println("You have not selected any users");

                    break;

                case 6:
                    if(selectedPerson != null) {
                        if(selectedPerson.getAge()<2){
                            //System.out.println("Sorry, You are under 3 years old!!");
                            throw new TooYoungException("The selected person is too young!!");
                        }
                        else{
                            System.out.println("hi "+ selectedPerson.getName() +", enter name of user you wanna add to your friendlist: "  );
                            username = sc.next();
                            friend = selectUser(users, userNum, username);

                            if(friend != null){
                                if(Math.abs(selectedPerson.getAge() - friend.getAge())<4)
                                    selectedPerson.addFriend(friend);
                                else
                                    System.out.println("the age difference is more than 3!!!");
                            }
                            else
                                System.out.println("the friend is not existed!!!");

                        }


                    }
                    else
                        System.out.println("You have not selected any users");
                    break;

                case 7:
                    System.out.println("Enter username: ");
                    username = sc.next();
                    Person deletedUser = selectUser(users, userNum, username);

                    if(deletedUser != null){
                        for(int i=0; i< deletedUser.getFriendNumber() ; i++){
                            deleteUser(deletedUser.getFriendList()[i].getFriendList(),deletedUser.getFriendList()[i].getFriendNumber(), deletedUser.getName());
                            deletedUser.getFriendList()[i].setFriendNumber(deletedUser.getFriendList()[i].getFriendNumber() -1);
                        }

                        if(deletedUser instanceof Child){
                            for(int i=0; i<2 ; i++){
                                deleteUser(((Adult)((Child)deletedUser).getParentList()[i]).getChildrenList(),((Adult)((Child)deletedUser).getParentList()[i]).getChildrenNumber(),((Child)deletedUser).getName());
                                ((Adult)((Child)deletedUser).getParentList()[i]).decreaseChildrenNumber();
                            }
                        }

                        deleteUser(users, userNum, username);
                        userNum--;
                    }
                    else
                        System.out.println("the user is not in the Network!!!");
                    break;

                case 8:
                    Scanner case8 = new Scanner(System.in);
                    System.out.println("Select 2 person to check direct friend: ");
                    Person person1 = selectUser(users,userNum,case8.next());
                    Person person2 = selectUser(users,userNum,case8.next());
                    System.out.println(" 2 person to check: " + person1.getName() +"  "+ person2.getName()
                            + "\nResult: "+ (person1.isFriend(person2.getName())? "Yes, they are friends" : "Nope") );

                    break;

                case 0:
                    break;

                default:
                    System.out.println("Wrong input");
                    break;
            }


        } while(choice != 0);

        System.out.println("System exit!!!");

    }

    public void inputData(){
        users[0] = new Child("Bob",15);
        users[4] = new Adult("Mom",35);
        ((Child) users[0]).addParent((Adult)users[4]);
        users[5] = new Adult("Dad",340);
        ((Child) users[0]).addParent((Adult)users[5]);

        ((Adult)users[4]).setSpouse((Adult) users[5]);
        ((Adult)users[5]).setSpouse((Adult) users[4]);

        users[1] = new Adult("Alice",18);
        users[2] = new Adult("Don",21);
        users[3] = new Adult("Cathy",24);

        users[0].addFriend(users[1]);
        users[1].addFriend(users[2]);
        users[2].addFriend(users[3]);
    }

    public static void printMenu() {
        System.out.println(" --------------");
        System.out.println("| MiniNet Menu |");
        System.out.println(" ---------------");
        System.out.print("1. List everyone \n"
                + "2. Add user to network\n"
                + "3. Select a person\n"
                + "4. Display selected person's info\n"
                + "5. Update selected person's info\n"
                + "6. Add friend\n"
                + "7. Delete user\n"
                + "8. Are these two direct friends?\n"
                + "0. Exit\n"
                + "Choose a number: (NUMBER ONLY): ");
    }

    public static Person selectUser(Person[] users, int userNum, String name) {
        boolean selected = false;
        int i=0;
        for(i=0; i< userNum; i++) {
            if((users[i].getName()).equals(name)) {
                selected =true;
                break;
            }
        }
        return users[i];
    }


    public static void addUser(Person[] users, int userNum , String name, int age) {
        if(age <3)
            users[userNum] = new YoungChild(name,age);
        else if (age <16 && age>2)
            users[userNum] = new Child(name,age);
        else
            users[userNum] = new Adult(name,age);

    }

    public static void deleteUser(Person[] users, int userNum, String name) {
        boolean isFound = false;
        int i;
        for(i=0; i<userNum ; i++) {
            if((users[i].getName()).equals(name)){
                isFound = true;
                break;
            }

        }

        if(isFound){
            for(int j = i; j<userNum ; j++) {
                users[j] = users[j+1];
            }
        }
        else
            System.out.println("Person is not found in list");

    }

    public static void listUsers(Person[] users, int userNum) {
        for(int i=0 ; i<userNum ; i++)
            System.out.println(users[i].getName());
    }
}
