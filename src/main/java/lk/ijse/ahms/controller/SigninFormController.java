package lk.ijse.ahms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.EmployeeBO;
import lk.ijse.ahms.bo.custom.UserBO;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.ahms.dao.custom.impl.UserDAOImpl;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.SecurityUtil;
import lk.ijse.ahms.util.SystemAlert;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SigninFormController {
    public AnchorPane root;
    public  TextField txtusername;
    public PasswordField txtpassword;
    public ImageView imageview1;
    public static String newmail;
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize(){
        txtusername.requestFocus();
    }

    public void signinOnAction(ActionEvent actionEvent) throws IOException {

        newmail = txtusername.getText();

       String getun = txtusername.getText();
       String getpw = txtpassword.getText();

        /*
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardControl_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("dashboard");
         */


        if(getun.isEmpty()) {
            new SystemAlert(Alert.AlertType.INFORMATION, "missing information", "username required..!!", ButtonType.OK).show();
        }else if(getpw.isEmpty()) {
            new SystemAlert(Alert.AlertType.INFORMATION, "missing information", "password required..!!", ButtonType.OK).show();
        } else {
            try {

                String enun = SecurityUtil.encoder(getun);
                ResultSet resultSet = userBO.checkCredentials(enun, getpw);

                if (resultSet.next()) {
                    String name = SecurityUtil.decoder(resultSet.getString(1));
                    String password = SecurityUtil.decoder(resultSet.getString(2));
                    String id = resultSet.getString(3);

                        if (password.equals(getpw) & name.equals(getun)) {
                       // AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardControl_form.fxml"));

                            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/dashboardControl_form.fxml"));
                            Parent anchorPane = fxmlLoader.load();

                            DashboardControlsController dash =  fxmlLoader.getController();
                            dash.setSigninFormController(this);

                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("dashboard");

                            EmployeeDto dto = employeeBO.searchEmployee(id);
                            dash.setLblname(dto.getName());


                            String email = getun;
                                String subject = "Animal Hospital System";
                                String message = "Hi..! \n\n You have successfully log in to Animal Hospital System. \n\n Thank you..!";

                                Mail mail = new Mail(email,subject,message);
                                Thread thread = new Thread(mail);

                                mail.valueProperty().addListener((a, oldValue, newValue) -> {
                                    if (newValue){
                                        System.out.println("mail sent");
                                    }else {
                                        System.out.println("mail not sent");
                                    }
                                });

                                thread.setDaemon(true);
                                thread.start();



                        } else {
                            new SystemAlert(Alert.AlertType.INFORMATION, "Information", "username or password incorrect..!", ButtonType.OK).show();
                            }
                    }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void passOnAction(ActionEvent actionEvent) throws IOException {
        signinOnAction(actionEvent);
    }

    public void usernameOnAction(ActionEvent actionEvent) {
        txtpassword.requestFocus();
    }

    public void forgotPassOnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/forgotPassword/forgotpass1_form.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
