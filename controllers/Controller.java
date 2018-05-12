package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import miniNetwork.Driver;
import miniNetwork.Person;


import static miniNetwork.Driver.selectUser;




public class Controller extends Application {
    Driver driver = new Driver();

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
     * Handle all button click event on Menu.fxml
     */

    //List User tab
    @FXML
    private TextArea userListArea;


    @FXML
    public void handleListButton(ActionEvent event){
        System.out.println("You clicked List button!");
        userListArea.setText(Driver.getUserList());
    }


    //Delete User tab
    @FXML
    private TextField deleteTextField;

    @FXML
    public void handleDeleteButton(){
        System.out.println("You clicked Delete button!");
        if(Driver.deleteUser(Driver.getUsers(),Driver.getUserNum(),deleteTextField.getText()))
            Driver.setUserNum(Driver.getUserNum()-1);
    }

    //Add User tab
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField ageTextField;

    @FXML
    public void handleRegisterButton(){
        System.out.println("You clicked Register button!");
        Driver.addUser(Driver.getUsers(),Driver.getUserNum(),nameTextField.getText(),Integer.parseInt(ageTextField.getText()));
        Driver.setUserNum(Driver.getUserNum()+1);
    }

    //Check Friendship tab
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
            checkResultLabel.setText((person1.isFriend(person2.getName())? "Yes, they are friends" : "Nope"));

    }






    /**
     * Handle all button click event on AddUSer.fxml
     */

    @FXML
    public void handleOKButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/SelectUser.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select User");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    @FXML
    public void handleExitButton(ActionEvent e){
        Platform.exit();
    }

    /**
     * Handle all button click event on SelectUser.fxml
     */
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/GUI/AddUser.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add User");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }
}
