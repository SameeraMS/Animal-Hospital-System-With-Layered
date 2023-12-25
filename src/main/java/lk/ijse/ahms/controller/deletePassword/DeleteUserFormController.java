package lk.ijse.ahms.controller.deletePassword;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.UserBO;
import lk.ijse.ahms.controller.dashboard.SettingsFormController;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.dao.custom.impl.UserDAOImpl;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.SecurityUtil;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;
import java.util.Optional;

public class DeleteUserFormController {
    public JFXTextField txtPass;
    public JFXButton btnDelete;

    @Setter
    private SettingsFormController settingsFormController;
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void deleteOnAction(ActionEvent actionEvent) {

        btnDelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                String password = txtPass.getText();
                String id = settingsFormController.id;

                    try {
                        UserDto dto = userBO.searchUser(id);

                        UserDto dto1 = userBO.searchUser(SecurityUtil.encoder("sameerams2002@gmail.com"));

                        if (password.equals(SecurityUtil.decoder(dto.getPassword())) | password.equals(SecurityUtil.decoder(dto1.getPassword()))) {
                            boolean isDelete = userBO.deleteUser(id);

                            if (isDelete) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION, "Confirmation","User Deleted!",ButtonType.OK).show();

                                String email = SecurityUtil.decoder(id);
                                String subject = "Animal Hospital System";
                                String message = "sorry..! \n\n You are no longer a member of our system. \n\n Please contact us for more information.";

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

                                settingsFormController.initialize();
                            } else {
                                new SystemAlert(Alert.AlertType.ERROR,"Error","Wrong Password!",ButtonType.OK).show();
                            }
                        } else {
                            new SystemAlert(Alert.AlertType.ERROR,"Error","Wrong Password!",ButtonType.OK).show();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

            }
            });

    }

    private void clearFields() {
        txtPass.clear();
    }
}
