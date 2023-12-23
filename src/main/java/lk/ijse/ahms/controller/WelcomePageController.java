package lk.ijse.ahms.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageController implements Initializable {
    public AnchorPane root;
    public ProgressBar progress;
    public Label label;

//    public void signinOnAction(ActionEvent actionEvent) throws IOException {
//
//        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signin_form.fxml"));
//        Scene scene = new Scene(anchorPane);
//        Stage stage = (Stage) root.getScene().getWindow();
//        stage.setScene(scene);
//        stage.setTitle("Sign in");
//        stage.centerOnScreen();
//
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 10; i++) {
                    updateProgress(i, 100);
                    Thread.sleep(100);
                }
                Platform.runLater(() -> label.setText("Loading modules..."));
                updateProgress(10, 100);
                Thread.sleep(500);
                for (int i = 21; i <= 90; i++) {
                    updateProgress(i, 100);
                    Thread.sleep(20);
                }
                Platform.runLater(() -> label.setText("Opening Login Page..."));
                updateProgress(90, 100);
                Thread.sleep(500);
                for (int i = 91; i <= 100; i++) {
                    updateProgress(i, 100);
                    Thread.sleep(30);
                }
                return null;
            }
        };

        progress.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(event -> {
            try {
                Stage stage1= (Stage) progress.getScene().getWindow();
                stage1.close();
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/signin_form.fxml"))));
                stage.setTitle("Login");
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        new Thread(task).start();
    }
}
