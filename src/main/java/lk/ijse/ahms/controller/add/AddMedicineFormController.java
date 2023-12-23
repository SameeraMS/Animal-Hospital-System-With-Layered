package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import lk.ijse.ahms.barcode.Barcode_genarate;
import lk.ijse.ahms.controller.dashboard.MedicineFormcontroller;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dao.custom.impl.MedicineDAOImpl;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;

public class AddMedicineFormController {

    public JFXTextField medqty;
    public DatePicker medExpDate;

    @FXML
    private JFXTextField medId;

    @FXML
    private JFXTextField medName;

    @FXML
    private JFXTextField medPrice;

    @FXML
    private JFXComboBox<String> cmbType;

    @FXML
    private JFXTextField medDesc;


    @Setter
    private MedicineFormcontroller medFormController;

    public void initialize() {
        generatenextId();
        loadCmbBox();
    }

    private void generatenextId() {
        try {
            String payId = MedicineDAOImpl.generateNextMedId();
            medId.setText(payId);
            medId.setEditable(false);
        } catch (SQLException e) {
            new SystemAlert(Alert.AlertType.ERROR,"Error",e.getMessage(), ButtonType.OK).show();
        }
    }

    private void loadCmbBox() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Medicine");
        obList.add("Injection");

        cmbType.setItems(obList);
    }

    public void idOnAction(ActionEvent actionEvent) {
        medName.requestFocus();
    }

    public void priceOnAction(ActionEvent actionEvent) {
        medDesc.requestFocus();
    }

    public void saveOnAction(ActionEvent actionEvent) {

        if(Regex.getDoublePattern().matcher(medPrice.getText()).matches()){
            if(Regex.getIntPattern().matcher(medqty.getText()).matches()){

                String id = medId.getText();
                String name = medName.getText();
                String type = cmbType.getSelectionModel().getSelectedItem();
                String price = medPrice.getText();
                String desc = medDesc.getText();
                String expDate = medExpDate.getValue().toString();
                String qty = medqty.getText();

                var dto = new MedicineDto(id, name, type, qty, price, desc, expDate );

                if(!id.isEmpty() && !name.isEmpty() && !type.isEmpty() && !price.isEmpty() && !desc.isEmpty() && !expDate.isEmpty() && !qty.isEmpty()) {
                    try {
                        boolean isSaved = MedicineDAOImpl.saveMedicine(dto);

                        if (isSaved) {
                            new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Medicine saved Successfully..!", ButtonType.OK).show();

                            Barcode_genarate barcodeGenarate = new Barcode_genarate();
                            barcodeGenarate.createImage(name+".png",id);
                            Image img = barcodeGenarate.getImg();
                            System.out.println("barcode save");

                            clearFields();
                            medFormController.initialize();
                            initialize();


                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        new SystemAlert(Alert.AlertType.ERROR,"Error",e.getMessage(), ButtonType.OK).show();
                    }
                } else {
                    new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Fill All Details..!", ButtonType.OK).show();
                }
            }else{
                new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Enter Valid Quantity..!", ButtonType.OK).show();
            }
        }else{
            new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Enter Valid Price..!", ButtonType.OK).show();
        }

    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        medId.clear();
        medName.clear();
        medPrice.clear();
        cmbType.getSelectionModel().clearSelection();
        medDesc.clear();
        medExpDate.setValue(null);
        medqty.clear();
    }




}
