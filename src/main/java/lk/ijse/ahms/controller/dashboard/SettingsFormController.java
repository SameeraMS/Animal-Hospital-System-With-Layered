package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.EmployeeBO;
import lk.ijse.ahms.bo.custom.UserBO;
import lk.ijse.ahms.controller.SigninFormController;
import lk.ijse.ahms.controller.deletePassword.DeleteUserFormController;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.dto.tm.UserTm;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.SecurityUtil;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SettingsFormController {
    public JFXTextField txtUsername;
    public JFXTextField txtPassword1;
    public JFXComboBox<String> cmbEmpId;
    public JFXTextField txtCurPassword;
    public JFXTextField txtnewPass1;
    public JFXTextField txtnewPass2;
    public JFXButton btnSaveNewUser;
    public JFXButton btnchangePass;
    public TableView<UserTm> tblUser;
    public TableColumn colUsername;
    public TableColumn colEmpId;
    public JFXButton btnDelete;
    public JFXTextField txtPassword2;

    public String id;

    public int focusedIndex;

    @Setter
    private SigninFormController signinFormController;

    public ObservableList<UserTm> obList = FXCollections.observableArrayList();
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() {
        tblUser.getItems().clear();
        setCellValueFactoryUser();
        loadAllUsers();
        loadEmployee();

    }

    private void loadEmployee() {
        System.out.println("Loading all Employee");

        ObservableList<String> obList1 = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = employeeBO.getAllEmployee();
            for (EmployeeDto dto : idList) {
                obList1.add(dto.getId());
            }
            cmbEmpId.setItems(obList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryUser() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpId"));

    }

    private void loadAllUsers() {
        System.out.println("Loading all Users");

        try {
            List<UserDto> userDtos = userBO.getAllUser();

            for (UserDto dto : userDtos) {

                obList.add(
                        new UserTm(
                                SecurityUtil.decoder(dto.getUsername()),
                                dto.getEmpId()
                        )
                );
            }
            tblUser.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void forgotpassOnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/forgotPassword/forgotpass1_form.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void changePassOnAction(ActionEvent actionEvent) {
        String username = signinFormController.newmail;
        String password = txtCurPassword.getText();
        String newpassword = txtnewPass1.getText();
        String newpassword2 = txtnewPass2.getText();

        if (!password.isEmpty() && !newpassword.isEmpty() && !newpassword2.isEmpty()) {
            try {
                UserDto dto = userBO.searchUser(SecurityUtil.encoder(username));
                if (SecurityUtil.decoder(dto.getPassword()).equals(password)) {
                    if (newpassword.equals(newpassword2)) {
                        boolean isChanged = userBO.changePassword(dto.getUsername(), SecurityUtil.encoder(newpassword));
                        if (isChanged) {
                       //     new Alert(Alert.AlertType.CONFIRMATION, "Password changed!").show();
                            new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Password changed Successfully..!", ButtonType.OK).show();

                            String email = username;
                            String subject = "Animal Hospital System";
                            String message = "Hi \n\nYour password has been changed successfully.";

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
                        }
                    } else {
                        new SystemAlert(Alert.AlertType.ERROR,"Error","Passwords do not match!", ButtonType.OK).show();
                    }
                } else {
                    new SystemAlert(Alert.AlertType.ERROR,"Error","Current password is incorrect!", ButtonType.OK).show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {

        focusedIndex = tblUser.getSelectionModel().getSelectedIndex();
        UserTm selectedItem = (UserTm) tblUser.getSelectionModel().getSelectedItem();

        id = SecurityUtil.encoder(selectedItem.getUsername());


        if (id.equals(SecurityUtil.encoder("sameerams2002@gmail.com"))) {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "You cannot delete Admin!", ButtonType.OK).show();
        } else{

            try {
                UserDto dto = userBO.searchUser(id);

                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/deleteUser/deleteUser_form.fxml"));
                Parent root = fxmlLoader.load();

                DeleteUserFormController deleteform = fxmlLoader.getController();
                deleteform.setSettingsFormController(this);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

            } catch (SQLException | IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void saveUserOnAction(ActionEvent actionEvent) {

        if(Regex.getEmailPattern().matcher(txtUsername.getText()).matches()){

            String username = txtUsername.getText();
            String password = txtPassword1.getText();
            String password2 = txtPassword2.getText();
            String empId = cmbEmpId.getValue();

            if (!username.isEmpty() && !password.isEmpty() && !password2.isEmpty() && !empId.isEmpty()) {
                try {
                    UserDto dto = userBO.searchUser(SecurityUtil.encoder(username));

                    if (dto == null) {
                        if (password.equals(password2)) {

                            String user = SecurityUtil.encoder(username);
                            String pass = SecurityUtil.encoder(password);

                            UserDto dto2 = new UserDto(user, pass, empId);
                            boolean isSaved = userBO.saveUser(dto2);

                            if (isSaved) {
                                //      new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "User saved Successfully..!", ButtonType.OK).show();

                                String email = username;
                                String subject = "Animal Hospital System";
                                String message = "Hi..! \n\n You have been added as a user to the Animal Hospitalsystem. \n Now you can login to your account using your username and password.";

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
                                initialize();
                                clearFields();
                            }
                        } else {
                            //  new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
                            new SystemAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!", ButtonType.OK).show();

                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }else {
            new SystemAlert(Alert.AlertType.ERROR, "Error", "Invalid Username(Email address)!", ButtonType.OK).show();
        }



    }

    private void clearFields() {
        txtUsername.clear();
        txtPassword1.clear();
        txtPassword2.clear();
        cmbEmpId.getSelectionModel().clearSelection();
    }
}
