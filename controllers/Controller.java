package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import miniNetwork.*;


import static miniNetwork.Driver.importDataFromTxt;
import static miniNetwork.Driver.selectUser;




public class Controller extends Application {
    Driver driver = new Driver();


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/Menu.fxml"));

        //input data to object Driver
        Driver.inputData();

        //input data from people.txt
        Driver.importDataFromTxt();

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
    public void handleListButton(ActionEvent event){
        System.out.println("You clicked List button!");
        userListArea.setText(Driver.getUserList());
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
    public void handleCheckInvalidButton(){
        System.out.println("You clicked \"Check Invalid\" button!");
        parentsGridPane.setVisible(false);
        siblingGridPane.setVisible(false);

        if(Integer.parseInt(ageTextField.getText())<17 &&  Integer.parseInt(ageTextField.getText())>2) {
            parentsGridPane.setVisible(true);
        }
        else if(Integer.parseInt(ageTextField.getText())<3){
            parentsGridPane.setVisible(true);
            siblingGridPane.setVisible(true);
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

        if(falseCount!=0)
            isValidToRegister=false;

        if(isValidToRegister)
            addParentsResultLabel.setText("Valid parents!!!");

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
        }
        else{
            addSiblingResultLabel.setText("Oops, inValid Sibling");
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
        }
        else
            resultLabel.setText("Oops, registered unsuccessfully!!!");

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
    public void handleSelectButton(){
        Driver.setSelectedPerson(selectUser(Driver.getUsers(),Driver.getUserNum(),selectUserLabel.getText()));
        if(Driver.getSelectedPerson() != null){
            System.out.println("the selected user is: " + Driver.getSelectedPerson().getName());
            selectResultLabel.setText("the selected user: " + Driver.getSelectedPerson().getName());
            userInfoTextArea.setText(Driver.getUserInfo());
        }
        else{
            selectResultLabel.setText("the user \"" + selectUserLabel.getText() + "\" is not registered yet!!");
            userInfoTextArea.setText("");
        }

    }



    /**
     *   "Delete Use" tab
     */

    @FXML
    private TextField deleteTextField;
    @FXML
    private Label deleteResultLabel;

    @FXML
    public void handleDeleteButton(){
        System.out.println("You clicked Delete button!");
        if(Driver.deleteUser(Driver.getUsers(),Driver.getUserNum(),deleteTextField.getText())){
            Driver.setUserNum(Driver.getUserNum()-1);
            deleteResultLabel.setText("User is deleted successfully!");
        }
        else
            deleteResultLabel.setText("User is not found in system");



    }



    /**
     *   "Check Use" tab
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

        Person person1 = selectUser(Driver.getUsers(),Driver.getUserNum(),userOneTextFiled.getText());
        Person person2 = selectUser(Driver.getUsers(),Driver.getUserNum(),userTwoTextFiled.getText());

        if((person1 == null) && (person2 == null))
            checkResultLabel.setText(userOneTextFiled.getText()+" and "+userTwoTextFiled.getText()+" are not in the System yet!");
        else if((person1 != null) && (person2 == null))
            checkResultLabel.setText(userTwoTextFiled.getText()+" is not in the System yet!");
        else if((person1 == null) && (person2 != null))
            checkResultLabel.setText(userOneTextFiled.getText()+" is not in the System yet!");
        else
            checkResultLabel.setText((person1.isFriend(person2.getName())? "Yes, they are friends" : "Nope, they are not friends"));

    }
}
