package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.DoctorBO;
import lk.ijse.ahms.controller.dashboard.EmployeeFormController;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;

public class AddDocFormcontroller {
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField tel;
    @FXML
    private JFXTextField email;
    @Setter
    private EmployeeFormController empFormController;
    DoctorBO doctorBO = (DoctorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DOCTOR);


    public void initialize() {
        generateNextId();

    }

    private void generateNextId() {
        try {
            String docId = doctorBO.generateNextDocId();
            id.setText(docId);
            id.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void nameOnAction(ActionEvent actionEvent) {
        tel.requestFocus();
    }

    public void telOnAction(ActionEvent actionEvent) {
        email.requestFocus();
    }

    public void emailOnAction(ActionEvent actionEvent) {
      //  saveOnAction(actionEvent);
    }

    public void idOnAction(ActionEvent actionEvent) {
        name.requestFocus();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        claerFields();
    }

    private void claerFields() {
        id.clear();
        name.clear();
        tel.clear();
        email.clear();
    }

    public void saveOnAction(ActionEvent actionEvent) {

        if(Regex.getMobilePattern().matcher(tel.getText()).matches()){
            if(Regex.getEmailPattern().matcher(email.getText()).matches()){
                if (Regex.getNamePattern().matcher(name.getText()).matches()){

                    String nid = id.getText();
                    String nname = name.getText();
                    String ntel = tel.getText();
                    String mail = email.getText();

                    var dto = new DoctorDto(nid, nname, ntel, mail);

                    if (!nid.isEmpty() && !nname.isEmpty() && !ntel.isEmpty() && !mail.isEmpty()) {
                        {
                            try {
                                boolean isSaved = doctorBO.saveDoctor(dto);

                                if (isSaved) {
                                    // new Alert(Alert.AlertType.CONFIRMATION, "Doctor saved!").show();
                                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Doctor saved Successfully..!", ButtonType.OK).show();
                                    claerFields();
                                    initialize();
                                    empFormController.initialize();
                                }
                            } catch (SQLException | ClassNotFoundException e) {
                                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                new SystemAlert(Alert.AlertType.ERROR,"Information",e.getMessage(), ButtonType.OK).show();
                            }
                        }
                    } else {
                        new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Fill All Details..!", ButtonType.OK).show();
                    }

                }else{
                    new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Enter Valid Name..!", ButtonType.OK).show();
                }
            }else{
                new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Enter Valid Email..!", ButtonType.OK).show();
            }
        } else {
            new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Enter Valid Mobile Number..!", ButtonType.OK).show();
        }
    }
}
