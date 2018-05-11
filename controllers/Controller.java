package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;



public class Controller extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/AddUser.fxml"));

        // Set the scene by getting the Parent scene from FXMLLoader
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Add User");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Handle all button click event on AddUSer.fxml
     */

    @FXML
    private TextField nameText;
    @FXML
    private Button exitButton;

    @FXML
    public void handleRegisterButton(ActionEvent event){
        System.out.println("You clicked Register!");
        if(!nameText.getText().isEmpty())
            System.out.println("good!!!");
    }

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
    public void handleExitButton(ActionEvent event){
        //Stage stage = (Stage) exitButton.getScene().getWindow();
        //stage.close();
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
