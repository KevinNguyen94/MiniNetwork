package controllers;

import exceptions.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import miniNetwork.*;

import static miniNetwork.Driver.selectUser;


public class MiniNet extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/Menu.fxml"));


        //input data to object Driver
        Driver.inputData();

        // Set the scene by getting the Parent scene from FXMLLoader
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    /**
     *   "List User" tab
     */

    @FXML
    private TextArea userListArea;

    @FXML
    public void handleListButton(){
        System.out.println("You clicked List button!");
        userListArea.setText(Driver.getUserList());
    }


    @FXML
    Label fileNotFoundLabel;
    @FXML
    Button importTxtButton;

    @FXML
    public void handleImportTxtButton(){
        //input data from people.txt
        try {
            Driver.importDataFromTxt();
            System.out.println("Data from Txt files imported");
            fileNotFoundLabel.setText("Data from Txt files imported!!");

            importTxtButton.setVisible(false);

        } catch (FileIsNotExistException e) {
            fileNotFoundLabel.setText(e.getMessage());
            System.err.println("Trying to retrieve data from hsqldb!!!");

            //try to read data from database HERE
            Driver.importDataFromDB();

            importTxtButton.setVisible(false);
        }

    }


    /**
     *   "Add User" tab
     */

    private boolean isValidToRegister = true;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    public GridPane parentsGridPane;
    @FXML
    public GridPane siblingGridPane;
    @FXML
    private Label resultLabel;

    @FXML
    public void handleCheckInvalidButton() {
        System.out.println("You clicked \"Check Invalid\" button!");
        parentsGridPane.setVisible(false);
        siblingGridPane.setVisible(false);
        try {
            if(Integer.parseInt(ageTextField.getText()) < 0 || Integer.parseInt(ageTextField.getText()) > 150){
                isValidToRegister = false;
                throw new NoSuchAgeException("Error! You input an invalid age");
            }
            else if (Integer.parseInt(ageTextField.getText()) < 17 && Integer.parseInt(ageTextField.getText()) > 2) {
                parentsGridPane.setVisible(true);
            }
            else if (Integer.parseInt(ageTextField.getText()) < 3) {
                parentsGridPane.setVisible(true);
                siblingGridPane.setVisible(true);
            }
            else if(Integer.parseInt(ageTextField.getText())>16){
                resultLabel.setText("the Username and Age valid");
                System.out.println("the Username and Age valid");
            }

        } catch (NumberFormatException e) {
            resultLabel.setText("Wrong format!! please input a number!!");
            System.err.println("Wrong format!! please input a number!!");
        } catch (NoSuchAgeException e) {
            resultLabel.setText("The age is invalid!!");
        }
    }

    @FXML
    private TextField parent1TextField;
    @FXML
    private TextField parent2TextField;
    @FXML
    private Label addParentsResultLabel;

    Person[] parents = new Person[2];
    @FXML
    public void handleAddParentButton(){
        System.out.println("You clicked \"Add Parents\" button!");

        isValidToRegister=true;
        int falseCount=0;
        StringBuilder stringBuilder = new StringBuilder();

        parents[0] = selectUser(Driver.getUsers(),Driver.getUserNum(),parent1TextField.getText());
        parents[1] = selectUser(Driver.getUsers(),Driver.getUserNum(),parent2TextField.getText());

        if(parents[0].getAge()<16){
            stringBuilder.append(parents[0].getName()+" cannot have children ( age: " +parents[0].getAge()+")\n");
            falseCount++;
        }

        if(parents[1].getAge()<16){
            stringBuilder.append(parents[1].getName()+" cannot have children ( age: " +parents[1].getAge()+")\n");
            falseCount++;
        }

        if((parents[0] instanceof Adult) && (parents[1] instanceof Adult)){
            if(((Adult)parents[0]).getSpouse() != null && ((Adult)parents[1]).getSpouse() == null){
                stringBuilder.append(parents[0].getName()+" already get married!!!\n");
                falseCount++;
            }

            if(((Adult)parents[0]).getSpouse() == null && ((Adult)parents[1]).getSpouse() != null){
                stringBuilder.append(parents[1].getName()+" already get married!!!\n");
                falseCount++;
            }

            if(((Adult)parents[0]).getSpouse() != null && ((Adult)parents[1]).getSpouse() != null)
                if(!(((Adult)parents[0]).getSpouse().getName().equals(parents[1].getName()))) {
                    stringBuilder.append("Both parents belong to different family!!!\n");
                    falseCount++;
                }
        }
        addParentsResultLabel.setText(stringBuilder.toString());
        System.out.println(stringBuilder.toString());

        if(falseCount!=0)
            isValidToRegister=false;

        if(isValidToRegister){
            addParentsResultLabel.setText("Valid parents!!!");
            System.out.println("Valid parents!!!");
        }

    }


    @FXML
    private TextField siblingTextField;
    @FXML
    private Label addSiblingResultLabel;

    @FXML
    public void handleAddSiblingButton(){
        isValidToRegister=true;
        System.out.println("You clicked \"Add Sibling\" button!");
        Person youngChild = selectUser(Driver.getUsers(),Driver.getUserNum(), siblingTextField.getText());
        if(youngChild instanceof YoungChild){
            addSiblingResultLabel.setText("Valid Sibling");
            System.out.println("Valid Sibling");
        }
        else{
            addSiblingResultLabel.setText("Oops, inValid Sibling");
            System.out.println("Oops, inValid Sibling");
            isValidToRegister=false;
        }

    }

    @FXML
    public void handleRegisterButton(){
        System.out.println("You clicked Register button!");
        if(isValidToRegister){
            Driver.addUser(Driver.getUsers(),Driver.getUserNum(),nameTextField.getText(),Integer.parseInt(ageTextField.getText()));
            Driver.setUserNum(Driver.getUserNum()+1);

            Driver.setSelectedPerson(selectUser(Driver.getUsers(),Driver.getUserNum(),nameTextField.getText()));

            if(Driver.getSelectedPerson() instanceof YoungChild){
                Person youngChild = selectUser(Driver.getUsers(),Driver.getUserNum(), siblingTextField.getText());

                ((YoungChild)Driver.getSelectedPerson()).addSibling((YoungChild) youngChild);
                ((YoungChild) youngChild).addSibling((YoungChild)Driver.getSelectedPerson());

                ((Child)Driver.getSelectedPerson()).addParent((Adult)parents[0]);
                ((Child)Driver.getSelectedPerson()).addParent((Adult)parents[1]);

                ((Adult)parents[0]).setSpouse((Adult) parents[1]);
                ((Adult)parents[1]).setSpouse((Adult) parents[0]);
            }
            else if(Driver.getSelectedPerson() instanceof Child){
                ((Child)Driver.getSelectedPerson()).addParent((Adult)parents[0]);
                ((Child)Driver.getSelectedPerson()).addParent((Adult)parents[1]);

                ((Adult)parents[0]).setSpouse((Adult) parents[1]);
                ((Adult)parents[1]).setSpouse((Adult) parents[0]);
            }

            resultLabel.setText("registered successfully!!!");
            System.out.println("registered successfully!!!");
        }
        else {
            resultLabel.setText("Oops, registered unsuccessfully!!!");
            System.out.println("Oops, registered unsuccessfully!!!");
        }
    }



    /**
     *   "Select Use" tab
     */

    @FXML
    private TextField selectUserLabel;
    @FXML
    private TextArea userInfoTextArea;
    @FXML
    private Label selectResultLabel;
    @FXML
    private GridPane addFriendGridPane;
    @FXML
    private GridPane addClassmateGridPane;
    @FXML
    private GridPane addColleagueGridPane;

    @FXML
    public void handleSelectButton(){
        System.out.println("You clicked \"Select\" button!");
        addFriendGridPane.setVisible(false);
        addClassmateGridPane.setVisible(false);
        addColleagueGridPane.setVisible(false);

        Driver.setSelectedPerson(selectUser(Driver.getUsers(),Driver.getUserNum(),selectUserLabel.getText()));
        if(Driver.getSelectedPerson() != null){
            System.out.println("the selected user is: " + Driver.getSelectedPerson().getName());
            selectResultLabel.setText("the selected user: " + Driver.getSelectedPerson().getName());
            userInfoTextArea.setText(Driver.getUserInfo());

            if(Driver.getSelectedPerson() instanceof YoungChild){
                addFriendGridPane.setVisible(false);
                addClassmateGridPane.setVisible(false);
            }
            else if(Driver.getSelectedPerson() instanceof Child){
                addFriendGridPane.setVisible(true);
                addClassmateGridPane.setVisible(true);
            }
            else if(Driver.getSelectedPerson() instanceof Adult){
                addFriendGridPane.setVisible(true);
                addClassmateGridPane.setVisible(true);
                addColleagueGridPane.setVisible(true);
            }
        }
        else{
            selectResultLabel.setText("the user \"" + selectUserLabel.getText() + "\" is not registered yet!!");
            System.out.println("the user \"" + selectUserLabel.getText() + "\" is not registered yet!!");
            userInfoTextArea.setText("");
        }

    }


    @FXML
    private TextField pictureTextField;
    @FXML
    private TextField statusTextField;
    @FXML
    private TextField stateTextField;
    @FXML
    public void handleInfoUpdateButton(){
        System.out.println("You clicked \"Update Info\" button");
        Driver.getSelectedPerson().setProfilePicture(pictureTextField.getText());
        Driver.getSelectedPerson().setStatus(statusTextField.getText());
        Driver.getSelectedPerson().setState(stateTextField.getText());
    }


    @FXML
    private TextField addFriendTextField;
    @FXML
    private Label addFriendResultLabel;

    Person friend =null;

    @FXML
    public void handleAddFriendButton(){
        System.out.println("You clicked \"Add Friend\" button!");
        Boolean error = false;

        friend = selectUser(Driver.getUsers(),Driver.getUserNum(),addFriendTextField.getText());
        if(friend != null){
            try{
                if(friend instanceof YoungChild) {
                    error=true;
                    throw new TooYoungException();
                }
                else if((Driver.getSelectedPerson() instanceof Child && friend instanceof Adult) || (friend instanceof Child && Driver.getSelectedPerson() instanceof Adult) ){
                    error=true;
                    throw new NotToBeFriendsException("Error! You trying to make an adult and a child friend!");
                }

            }catch (TooYoungException e){
                addFriendResultLabel.setText("Error! Young child friend not accepted!");
            } catch (NotToBeFriendsException e) {
                addFriendResultLabel.setText("Adult and Child cannot be friend!");
            }

            try{
               if( (Driver.getSelectedPerson() instanceof Child) && (friend instanceof Child)  && (Math.abs(Driver.getSelectedPerson().getAge() - friend.getAge())>3) ){
                    error=true;
                    throw new NotToBeFriendsException("Error! Age gap between 2 Child is larger than 3");
                }

            }catch (NotToBeFriendsException e) {
                addFriendResultLabel.setText("(2 Child) Age gap > 3 forbid!");
            }

            if(!error) {
                Driver.getSelectedPerson().addFriend(friend);
                addFriendResultLabel.setText(friend.getName() + " added successfully");
                System.out.println(friend.getName() + " added successfully");
            }
        }
        else{
            System.out.println("your friend is not in the system!");
            addFriendResultLabel.setText("Friend not in System!!");
        }
    }


    @FXML
    private TextField addClassmateTextField;
    @FXML
    private Label addClassmateResultLabel;

    Person classmate = null;

    @FXML
    public void handleAddClassmateButton(){
        System.out.println("You clicked \"Add Classmate\" button!");
        classmate = selectUser(Driver.getUsers(),Driver.getUserNum(),addClassmateTextField.getText());
        if(classmate != null){
            try{
                if(! (colleague instanceof Adult)){
                    throw new NotToBeClassmatesException("You are trying to make a young child in a classmate relation");
                }
                else{
                    Driver.getSelectedPerson().addClassmate(classmate);
                    classmate.addClassmate(Driver.getSelectedPerson());
                    addClassmateResultLabel.setText(classmate.getName()+" added successfully");
                    System.out.println(classmate.getName()+" added successfully");}
            }catch(NotToBeClassmatesException e){
                addClassmateResultLabel.setText("Young Child is forbid!");
            }

        }
        else{
            System.out.println("your classmate is not in the system!");
            addClassmateResultLabel.setText("Classmate not in System!!");
        }
    }


    @FXML
    private TextField addColleagueTextField;
    @FXML
    private Label addColleagueResultLabel;

    Person colleague = null;

    @FXML
    public void handleAddColleagueButton(){
        System.out.println("You clicked \"Add Colleague\" button!");
        colleague = selectUser(Driver.getUsers(),Driver.getUserNum(),addColleagueTextField.getText());
        if(colleague != null){
            try{
                if(! (colleague instanceof Adult)){
                    throw new NotToBeColleaguesException();
                }
                else{
                    ((Adult)Driver.getSelectedPerson()).addColleague(colleague);
                    ((Adult)colleague).addColleague((Driver.getSelectedPerson()));
                    addColleagueResultLabel.setText(colleague.getName()+" added successfully");
                    System.out.println(colleague.getName()+" added successfully");
                }

            }catch(NotToBeColleaguesException e){
                addColleagueResultLabel.setText("Only adult accepted!");
            }
        }
        else{
            System.out.println("your colleague is not in the system!");
            addColleagueResultLabel.setText("Colleague not in System!!");
        }

    }


    /**
     *   "Delete User" tab
     */

    @FXML
    private TextField deleteTextField;
    @FXML
    private Label deleteResultLabel;

    @FXML
    public void handleDeleteButton(){
        System.out.println("You clicked Delete button!");

        Person deletedUser = selectUser(Driver.getUsers(),Driver.getUserNum(),deleteTextField.getText());
        if(deletedUser != null){
            Driver.deleteUserFromOtherList(deletedUser);
            Driver.deleteUser(Driver.getUsers(),Driver.getUserNum(),deleteTextField.getText());
            Driver.setUserNum(Driver.getUserNum()-1);
            deleteResultLabel.setText("User is deleted successfully!");
            System.out.println("User is deleted successfully!");

        }
        else {
            deleteResultLabel.setText("User is not found in system");
            System.out.println("User is not found in system");
        }
    }


    /**
     *   "Check User" tab
     */

    @FXML
    private TextField userOneTextFiled;
    @FXML
    private TextField userTwoTextFiled;
    @FXML
    private Label checkResultLabel;


    @FXML
    public void handleCheckButton(){
        System.out.println("You clicked Check button!");

        StringBuilder stringBuilder = new StringBuilder();

        Person person1 = selectUser(Driver.getUsers(),Driver.getUserNum(),userOneTextFiled.getText());
        Person person2 = selectUser(Driver.getUsers(),Driver.getUserNum(),userTwoTextFiled.getText());

        if((person1 == null) && (person2 == null)) {
            checkResultLabel.setText(userOneTextFiled.getText() + " and " + userTwoTextFiled.getText() + " are not in the System yet!");
            System.out.println(userOneTextFiled.getText()+" and "+userTwoTextFiled.getText()+" are not in the System yet!");
        }
        else if((person1 != null) && (person2 == null)) {
            checkResultLabel.setText(userTwoTextFiled.getText() + " is not in the System yet!");
            System.out.println(userTwoTextFiled.getText() + " is not in the System yet!");
        }
        else if((person1 == null) && (person2 != null)) {
            checkResultLabel.setText(userOneTextFiled.getText() + " is not in the System yet!");
            System.out.println(userOneTextFiled.getText()+" is not in the System yet!");
        }
        else {
            if((person1 instanceof YoungChild) || (person2 instanceof YoungChild)){
                checkResultLabel.setText("There is at least one user is Young Child type\nAnd a Young Child does not have friend, classmate or colleague relation!!! ");
            }
            else{
                if(person1.isFriend(person2.getName())){
                    stringBuilder.append("They are friends\n");
                }
                if(person1.isClassmate(person2.getName())){
                    stringBuilder.append("They are classmates\n");
                }
                if((person1 instanceof Adult) && (person2 instanceof Adult) && (((Adult) person1).isColleague(person2.getName()))){
                    stringBuilder.append("They are colleagues");
                }
                checkResultLabel.setText(stringBuilder.toString());

            }



        }
    }
}
