package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.EmployeeBO;
import lk.ijse.ahms.controller.dashboard.EmployeeFormController;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;

public class AddEmployeeFormController {
    @FXML
    private JFXTextField empId;
    @FXML
    private JFXTextField empName;
    @FXML
    private JFXTextField empAddress;
    @FXML
    private JFXTextField empTel;
    @FXML
    private JFXTextField empMail;
    @FXML
    private JFXComboBox<String> cmbEmpType;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    public void initialize() {
        generateNextId();
        loadCmbBox();
    }

    private void generateNextId() {
        try {
            String Id = employeeBO.generateNextEmployeeId();
            empId.setText(Id);
            empId.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Setter
    private EmployeeFormController empFormController;


    private void loadCmbBox() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Helper");
        obList.add("Cleaner");

        cmbEmpType.setItems(obList);
    }

    private void clearfields() {
        empId.clear();
        empName.clear();
        empAddress.clear();
        empTel.clear();
        empMail.clear();
        cmbEmpType.getSelectionModel().clearSelection();
    }


    public void saveOnAction(javafx.event.ActionEvent actionEvent) {

        if(Regex.getNamePattern().matcher(empName.getText()).matches()){
            if(Regex.getAddressPattern().matcher(empAddress.getText()).matches()){
                if(Regex.getMobilePattern().matcher(empTel.getText()).matches()){
                    if(Regex.getEmailPattern().matcher(empMail.getText()).matches()){

                        String id = empId.getText();
                        String name = empName.getText();
                        String address = empAddress.getText();
                        String tel = empTel.getText();
                        String mail = empMail.getText();
                        String type = cmbEmpType.getSelectionModel().getSelectedItem();

                        var dto = new EmployeeDto(id, name, address, tel, mail, type);

                        if (!id.isEmpty() && !name.isEmpty() && !address.isEmpty() && !tel.isEmpty() && !mail.isEmpty() && !type.isEmpty()) {
                            try {
                                boolean isSaved = employeeBO.saveEmployee(dto);

                                if (isSaved) {
                                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Employee saved Successfully..!", ButtonType.OK).show();

                                    clearfields();
                                    empFormController.initialize();
                                    initialize();

                                }
                            } catch (SQLException | ClassNotFoundException e) {
                                new SystemAlert(Alert.AlertType.ERROR,"Information",e.getMessage(), ButtonType.OK).show();
                            }
                        } else {
                            new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Fill All Details..!", ButtonType.OK).show();
                        }
                    }else{
                        new SystemAlert(Alert.AlertType.ERROR,"Information","Please Enter Valid Email..!", ButtonType.OK).show();
                    }
                }else{
                    new SystemAlert(Alert.AlertType.ERROR,"Information","Please Enter Valid Mobile..!", ButtonType.OK).show();
                }
            }else{
                new SystemAlert(Alert.AlertType.ERROR,"Information","Please Enter Valid Address..!", ButtonType.OK).show();
            }
        } else {
            new SystemAlert(Alert.AlertType.ERROR,"Information","Please Enter Valid Name..!", ButtonType.OK).show();
        }
    }

    public void clearOnAction(javafx.event.ActionEvent actionEvent) {
        clearfields();
    }

    public void idOnAction(ActionEvent actionEvent) {
        empName.requestFocus();
    }

    public void nameOnAction(ActionEvent actionEvent) {
        empAddress.requestFocus();
    }

    public void addressOnAction(ActionEvent actionEvent) {
        empTel.requestFocus();
    }

    public void telOnAction(ActionEvent actionEvent) {
        empMail.requestFocus();
    }

    public void mailOnAction(ActionEvent actionEvent) {
      //  cmbEmpType.requestFocus();
    }
}
