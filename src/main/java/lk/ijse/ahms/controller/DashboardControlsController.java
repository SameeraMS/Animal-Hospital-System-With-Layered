package lk.ijse.ahms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;

public class DashboardControlsController {
    public AnchorPane mainroot;
    public AnchorPane subroot;
    public Label lblname;
    public JFXButton btndashboard;
    public JFXButton btnpets;
    public JFXButton btnEmployee;
    public JFXButton btnmedicine;
    public JFXButton btnsettings;
    public JFXButton btnappointment;
    public JFXButton btndetails;
    public JFXButton btnpayment;
    public JFXButton btnsignout;

    @Setter
    private SigninFormController signinFormController;


    public void initialize() throws IOException {
        setButtonUp();
        dash();
       // DashboardController.lblToChange.setText("Welcome Back..!");
    }
    private void setform(String form) throws IOException {
        URL resource = getClass().getResource(form);
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subroot.getChildren().clear();
        subroot.getChildren().add(load);
    }

    private void setButtonUp() {
        btnpets.setStyle("-fx-background-color: white;");
        btnpayment.setStyle("-fx-background-color: white;");
        btndetails.setStyle("-fx-background-color: white;");
        btnmedicine.setStyle("-fx-background-color: white;");
        btnappointment.setStyle("-fx-background-color: white;");
        btnsettings.setStyle("-fx-background-color: white;");
        btndashboard.setStyle("-fx-background-color: white;");
        btnEmployee.setStyle("-fx-background-color: white;");
        btnsignout.setStyle("-fx-background-color: #D0D4CA;");
    }

    public void signoutOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signin_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) mainroot.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("dashboard");

    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btndashboard.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/dashboard_form.fxml");
    }

    public void dash( ) throws IOException {
        setform("/view/dashboard/dashboard_form.fxml");
    }

    public void petsOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btnpets.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/pets_form.fxml");
    }

    public void employeeOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btnEmployee.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/employee_form.fxml");
    }

    public void medicineOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btnmedicine.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/medicine_form.fxml");
    }

    public void settingsOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btnsettings.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/settings_form.fxml");
    }

    public void appointmentOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btnappointment.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/appointment_form.fxml");
    }

    public void detailsOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btndetails.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/prescription_form.fxml");
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException {
        setButtonUp();
        btnpayment.setStyle("-fx-background-color: #89CFF3;");
        setform("/view/dashboard/payment_form.fxml");
    }

    public void signoutOnMouseEnter(MouseEvent mouseEvent) {
        btnsignout.setStyle("-fx-background-color: red;");
    }

    public void signoutOnMouseExit(MouseEvent mouseEvent) {
        setButtonUp();
    }

    public void setLblname(String name) {
        lblname.setText(name);
    }
}
