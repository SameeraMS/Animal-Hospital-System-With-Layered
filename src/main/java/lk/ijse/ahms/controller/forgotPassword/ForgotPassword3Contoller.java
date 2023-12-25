package lk.ijse.ahms.controller.forgotPassword;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.UserBO;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.SecurityUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ForgotPassword3Contoller implements Initializable {
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    private final static String URL = "jdbc:mysql://localhost:3306/animalhospital";
    private final static Properties props = new Properties();

    static{
        props.setProperty("user", "root");
        props.setProperty("password", "Ijse@123");
    }

    public ImageView btnClose;
    public AnchorPane root;

    @FXML
    private JFXTextField txtPassword1;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private ImageView hidePassword;

    @FXML
    private ImageView hidePassword1;

    @FXML
    private JFXTextField txtReEnterPassword1;

    @FXML
    private JFXPasswordField txtReEnterPassword;

    @FXML
    private Button btnSave;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setPasswordDisable();
    }

    @FXML
    void btnSendOTPOnAction(ActionEvent event) {

    }

    @FXML
    void showPasswordOnMousePresseds(MouseEvent event) {
        txtPassword.setVisible(false);
        txtPassword1.setText(txtPassword.getText());
        txtPassword1.setVisible(true);
    }

    @FXML
    void showPasswordOnMousePresseds1(MouseEvent event) {
        txtReEnterPassword.setVisible(false);
        txtReEnterPassword1.setText(txtReEnterPassword.getText());
        txtReEnterPassword1.setVisible(true);
    }

    @FXML
    void showPasswordOnMouseReleased(MouseEvent event) {
        txtPassword.setVisible(true);
        txtPassword1.setVisible(false);
    }

    @FXML
    void showPasswordOnMouseReleased1(MouseEvent event) {
        txtReEnterPassword.setVisible(true);
        txtReEnterPassword1.setVisible(false);
    }

    @FXML
    void txtReEnterPasswordOnInputMethodTextChanged(KeyEvent event) {

    }

    @FXML
    void txtReEnterPasswordOnKeyPresed(KeyEvent event) {

    }

    void setPasswordDisable(){
        txtPassword1.setVisible(false);
        txtReEnterPassword1.setVisible(false);
    }

    public void btnSaveOnAction(ActionEvent event) throws SQLException, IOException {
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();
        String userName = Forgotpass1FormController.userName;

        if (password.equalsIgnoreCase(reEnterPassword)) {

            String npass = SecurityUtil.encoder(password);
            try {
                UserDto user = new UserDto(userName, npass, null);
                boolean isUpdate = userBO.updateUser(user);

                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Password Updated!!").show();

                    String email = SecurityUtil.decoder(userName);
                    String subject = "Animal Hospital System";
                    String message = "Hi..! \n\nYour password has been changed.";

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
                txtReEnterPassword.setStyle("-fx-background-color: none;");
                txtReEnterPassword1.setStyle("-fx-background-color: none;");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            root.getScene().getWindow().hide();
//            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.initStyle(StageStyle.UNDECORATED);
//            stage.show();
        }else {
            txtReEnterPassword.setStyle("-fx-background-color: #e74c3c;");
            txtReEnterPassword1.setStyle("-fx-background-color: #e74c3c;");
        }
    }

    public void btnCloseOnAction(MouseEvent mouseEvent) {
        root.getScene().getWindow().hide();
    }
}
