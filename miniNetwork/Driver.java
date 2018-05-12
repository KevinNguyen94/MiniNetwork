package miniNetwork;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import exceptions.NotToBeFriendsException;
import exceptions.TooYoungException;

import java.util.Scanner;

/**
 *
 * @author khanhnguyen
 */
public class Driver {

    private static Person[] users = new Person[20];
    private static int userNum = 9;
    private static Person selectedPerson = null;

    private static Scanner sc;
    private static Scanner numberSc;

    public static void setUserNum(int num){
        userNum = num;
    }

    public static Person[] getUsers(){
        return users;
    }

    public static int getUserNum(){
        return userNum;
    }

    public void start()throws TooYoungException, NotToBeFriendsException  {
        sc = new Scanner(System.in);
        numberSc = new Scanner(System.in);

        inputData();

        int choice=0;
        do {
            printMenu();

            choice = numberSc.nextInt();

            switch(choice) {
                case 1:
                    listUsers(users , userNum);
                    break;

                case 2:
                    case2();
                    break;

                case 3:
                    case3();
                    break;

                case 4:
                    case4();
                    break;

                case 5:
                    case5();
                    break;

                case 6:
                    case6();
                    break;

                case 7:
                    case7();
                    break;

                case 8:
                    case8();
                    break;

                case 9:
                    case9();
                    break;

                case 10:
                    case10();
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

    public static void inputData(){
        users[0] = new Child("Bob",15);
        users[4] = new Adult("Mom",35);
        ((Child) users[0]).addParent((Adult)users[4]);
        users[5] = new Adult("Dad",34);
        ((Child) users[0]).addParent((Adult)users[5]);


        ((Adult)users[4]).setSpouse((Adult) users[5]);
        ((Adult)users[5]).setSpouse((Adult) users[4]);

        users[1] = new Adult("Alice",18);
        users[2] = new Adult("Don",21);
        users[3] = new Adult("Cathy",24);

        users[0].addFriend(users[1]);
        users[1].addFriend(users[2]);
        users[2].addFriend(users[3]);


        users[6] = new YoungChild("Kevin",2);
        users[7] = new YoungChild("Neo",1);
        users[8] = new YoungChild("Alvin",1);
        ((YoungChild)users[6]).addSibling((YoungChild) users[7]);
        ((YoungChild)users[7]).addSibling((YoungChild) users[6]);
        ((YoungChild)users[6]).addSibling((YoungChild) users[8]);
        ((YoungChild)users[8]).addSibling((YoungChild) users[6]);
        ((YoungChild)users[7]).addSibling((YoungChild) users[8]);
        ((YoungChild)users[8]).addSibling((YoungChild) users[7]);

        ((Child) users[6]).addParent((Adult)users[1]);
        ((Child) users[6]).addParent((Adult)users[2]);
        ((Child) users[7]).addParent((Adult)users[1]);
        ((Child) users[7]).addParent((Adult)users[2]);
        ((Child) users[8]).addParent((Adult)users[1]);
        ((Child) users[8]).addParent((Adult)users[2]);

        ((Adult)users[1]).setSpouse((Adult) users[2]);
        ((Adult)users[2]).setSpouse((Adult) users[1]);
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
                + "7. Add colleague\n"
                + "8. Add classmate\n"
                + "9. Delete user\n"
                + "10. Are these two direct friends?\n"
                + "0. Exit\n"
                + "Choose a number: (NUMBER ONLY): ");
    }

    public static Person selectUser(Person[] users, int userNum, String name) {
        int i=0;
        for(i=0; i< userNum; i++) {
            if((users[i].getName()).equals(name))
                break;
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

    public static Boolean deleteUser(Person[] users, int userNum, String name) {
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
            System.out.println("Person is deleted!");
            return true;
        }
        else {
            System.out.println("Person is not found in list");
            return false;
        }
    }

    public static void listUsers(Person[] users, int userNum) {
        for(int i=0 ; i<userNum ; i++)
            System.out.println(users[i].getName());
    }

    public static String getUserList(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0 ; i<userNum ; i++)
            stringBuilder.append(users[i].getName()+"\n");
        return stringBuilder.toString();
    }

    public void case2(){
        Person selectedPerson;

        System.out.println("Enter username: ");
        String username = sc.next();
        System.out.println("Enter age: ");
        int age = sc.nextInt();
        addUser(users, userNum, username,age);
        userNum++;

        if(age<17 ){
            Scanner input = new Scanner(System.in);
            Person[] parents = new Adult[2];
            boolean result= false;
            int count;
            System.out.println("The new user is under 16, please add 2 parents !");

            while(result == false){
                for(int i=0; i<2 ; i++){
                    System.out.println("input name of parent number "+(i+1));
                    parents[i] = selectUser(users,userNum,input.next());
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


        Scanner number = new Scanner(System.in);
        selectedPerson = selectUser(users, userNum, username);

        if(age<3) {
            Person youngChild;
            Scanner input1 = new Scanner(System.in);
            System.out.println("Do you have any sibling?\n please enter the number of siblings (0 if none)");
            int num = number.nextInt();
            for (int i = 0; i < num; i++) {
                System.out.println("input name of sibling number " + (i + 1));
                youngChild = selectUser(users, userNum, input1.next());
                ((YoungChild) selectedPerson).addSibling((YoungChild) youngChild);
                ((YoungChild) youngChild).addSibling((YoungChild) selectedPerson);
            }
        }
    }

    public void case3(){
        System.out.println("Enter username: ");
        String username = sc.next();
        selectedPerson = selectUser(users, userNum, username);
        if(selectedPerson != null){
            System.out.println("the selected user is: " + selectedPerson.getName());
        }
        else
            System.out.println("the user is not registered yet!!");

    }

    public void case4(){
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

            if(selectedPerson instanceof YoungChild){
                System.out.println("Parents: ");
                for(int i=0 ; i<2 ; i++){
                    System.out.print(((YoungChild) selectedPerson).getParentList()[i].getName()+"  ");
                }
                System.out.println("\n\n");

                System.out.println("Siblings: ");
                for(int i = 0; i< ((YoungChild)selectedPerson).getSiblingNumber(); i++){
                    System.out.println(((YoungChild)selectedPerson).getSiblings()[i].getName());
                }
                System.out.println("\n\n");
            }

            else if(selectedPerson instanceof Child){
                System.out.println("Classmate list:");
                for(int i=0 ; i< selectedPerson.getClassmateNumber() ; i++){
                    System.out.print(selectedPerson.getClassmates()[i].getName()+"  ");
                }
                System.out.println("\n\n");

                System.out.println("Parents: ");
                for(int i=0 ; i<2 ; i++){
                    System.out.print(((Child) selectedPerson).getParentList()[i].getName()+"  ");
                }
                System.out.println("\n\n");
            }

            else if(selectedPerson instanceof Adult){
                System.out.println("Classmate list:");
                for(int i=0 ; i< selectedPerson.getClassmateNumber() ; i++){
                    System.out.print(selectedPerson.getClassmates()[i].getName()+"  ");
                }
                System.out.println("\n\n");

                System.out.println("Colleague list:");
                for(int i = 0; i< ((Adult) selectedPerson).getColleagueNumber() ; i++){
                    System.out.print(((Adult) selectedPerson).getColleagues()[i].getName()+"  ");
                }
                System.out.println("\n\n");

                if(((Adult) selectedPerson).isMarried()){
                    System.out.println("Spouse: "+ ((Adult) selectedPerson).getSpouse().getName());
                    System.out.println("Children: ");
                    for(int i = 0; i<((Adult) selectedPerson).getChildrenNumber() ; i++){
                        System.out.print(((Adult) selectedPerson).getChildrenList()[i].getName()+"  ");
                    }
                    System.out.println("\n\n");
                }
            }

        }
        else
            System.out.println("You have not selected any users");
    }

    public void case5(){
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
    }

    public void case6()throws TooYoungException, NotToBeFriendsException {
        Person friend;
        if(selectedPerson != null) {
            if(selectedPerson.getAge()<3){
                throw new TooYoungException("The selected person is too young!!");
            }
            else{
                System.out.println("hi "+ selectedPerson.getName() +", enter name of user you wanna add to your friendlist: "  );
                String username = sc.next();
                friend = selectUser(users, userNum, username);

                if(friend != null){
                    if((selectedPerson.getAge() < 17 && friend.getAge() >16) || (selectedPerson.getAge() > 16 && friend.getAge() <17)){
                        throw new NotToBeFriendsException("Can not make an adult and a child friend!!!");
                    }
                    else if(selectedPerson.getAge() < 17 && friend.getAge() <17){
                        if(Math.abs(selectedPerson.getAge() - friend.getAge())<4)
                            selectedPerson.addFriend(friend);
                        else
                            throw new NotToBeFriendsException("Age gap larger than 3 between two children!!!");
                        //System.out.println("the age difference is more than 3!!!");
                    }
                    else
                        selectedPerson.addFriend(friend);
                }
                else
                    System.out.println("the friend is not in the system!!!");

            }
        }
        else
            System.out.println("You have not selected any users");
    }

    public void case7(){
        Scanner case7 = new Scanner(System.in);
        if(selectedPerson.getAge()<17){
            System.out.println("Sorry, you are under 17, can not add colleague!!!");
        }
        else{
            Person colleague;

            System.out.println("Enter your colleague name:");
            String colleagueName = case7.nextLine();
            colleague = selectUser(users,userNum,colleagueName);

            if(colleague!= null){
                //if(selectedPerson.isFriend(colleagueName)){
                ((Adult)selectedPerson).addColleague((Adult)colleague);
                ((Adult)colleague).addColleague((Adult)selectedPerson);
                // }
                // else System.out.println("Sorry, you have not made friend with "+ colleagueName + " yet!!");
            }
            else
                System.out.println("Your colleague is not in the system!!!");

        }
    }

    public void case8(){
        Scanner case8 = new Scanner(System.in);
        if(selectedPerson.getAge()<3){
            System.out.println("Sorry, you are under 3, can not add classmate!!!");
        }
        else{
            Person classmate;

            System.out.println("Enter your classmate name:");
            String classmateName = case8.nextLine();
            classmate = selectUser(users,userNum,classmateName);

            if(classmate!= null){
                //if(selectedPerson.isFriend(classmateName)){
                selectedPerson.addClassmate(classmate);
                classmate.addClassmate(selectedPerson);
                // }
                //else System.out.println("Sorry, you have not made friend with "+ classmateName + " yet!!");
            }
            else
                System.out.println("Your classmate is not in the system!!!");
        }
    }

    public void case9(){
        System.out.println("Enter username: ");
        String username = sc.next();
        Person deletedUser = selectUser(users, userNum, username);

        if(deletedUser != null){
            if(deletedUser instanceof YoungChild){
                /**
                 * Delete deleted user record from the Sibling list of the deleted user' siblings
                 */
                for(int i = 0; i< ((YoungChild) deletedUser).getSiblingNumber() ; i++){
                    deleteUser(((YoungChild)((YoungChild) deletedUser).getSiblings()[i]).getSiblings(),((YoungChild)((YoungChild) deletedUser).getSiblings()[i]).getSiblingNumber(), deletedUser.getName());
                    ((YoungChild)((YoungChild) deletedUser).getSiblings()[i]).decreaseSiblingNumber();
                }
                /**
                 * Delete deleted user record from the children list of the deleted user' parents
                 */
                for(int i = 0; i<((YoungChild) deletedUser).getParentNumber() ; i++){
                    deleteUser(((YoungChild)deletedUser).getParentList()[i].getChildrenList(),((YoungChild)deletedUser).getParentList()[i].getChildrenNumber(),deletedUser.getName());
                    ((YoungChild)deletedUser).getParentList()[i].decreaseChildrenNumber();
                }
            }

            else if(deletedUser instanceof Child){
                /**
                 * Delete deleted user record from the Children list of the deleted user' Parents
                 */
                for(int i = 0; i<((Child) deletedUser).getParentNumber() ; i++){
                    deleteUser(((Child)deletedUser).getParentList()[i].getChildrenList(),((Child)deletedUser).getParentList()[i].getChildrenNumber(),deletedUser.getName());
                    ((Child)deletedUser).getParentList()[i].decreaseChildrenNumber();
                }

                /**
                 * Delete deleted user record from the Friend list of the deleted user' Friends
                 */
                for(int i=0; i< deletedUser.getFriendNumber() ; i++){
                    deleteUser(deletedUser.getFriendList()[i].getFriendList(),deletedUser.getFriendList()[i].getFriendNumber(), deletedUser.getName());
                    deletedUser.getFriendList()[i].setFriendNumber(deletedUser.getFriendList()[i].getFriendNumber() -1);
                }

                /**
                 * Delete deleted user record from the Classmate list of the deleted user' Classmates
                 */
                for(int i=0; i< deletedUser.getClassmateNumber() ; i++){
                    deleteUser(deletedUser.getClassmates()[i].getClassmates(),deletedUser.getClassmates()[i].getClassmateNumber(), deletedUser.getName());
                    deletedUser.getClassmates()[i].decreaseClassmateNumber();
                }
            }
            else if(deletedUser instanceof Adult){
                /**
                 * Delete deleted user record from the Friend list of the deleted user' Friends
                 */
                for(int i=0; i< deletedUser.getFriendNumber() ; i++){
                    deleteUser(deletedUser.getFriendList()[i].getFriendList(),deletedUser.getFriendList()[i].getFriendNumber(), deletedUser.getName());
                    deletedUser.getFriendList()[i].setFriendNumber(deletedUser.getFriendList()[i].getFriendNumber() -1);
                }

                /**
                 * Delete deleted user record from the Colleague list of the deleted user' Colleagues
                 */
                for(int i = 0; i< ((Adult) deletedUser).getColleagueNumber() ; i++){
                    deleteUser(((Adult) deletedUser).getColleagues()[i].getColleagues(),((Adult) deletedUser).getColleagues()[i].getColleagueNumber(), deletedUser.getName());
                    ((Adult) deletedUser).getColleagues()[i].decreaseColleagueNumber();
                }
            }

            deleteUser(users, userNum, username);
            userNum--;
        }
        else
            System.out.println("the user is not in the Network!!!");
    }

    public void case10(){
        Scanner case10 = new Scanner(System.in);
        System.out.println("Select 2 person to check direct friend: ");
        Person person1 = selectUser(users,userNum,case10.next());
        Person person2 = selectUser(users,userNum,case10.next());
        System.out.println(" 2 person to check: " + person1.getName() +"  "+ person2.getName()
                + "\nResult: "+ (person1.isFriend(person2.getName())? "Yes, they are friends" : "Nope") );
    }
}
