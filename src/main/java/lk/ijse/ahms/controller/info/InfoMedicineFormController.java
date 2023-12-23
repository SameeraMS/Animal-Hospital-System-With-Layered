package lk.ijse.ahms.controller.info;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import lk.ijse.ahms.controller.dashboard.MedicineFormcontroller;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dao.custom.impl.MedicineDAOImpl;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InfoMedicineFormController {
    public JFXTextField medName;
    public JFXTextField medQty;
    public JFXComboBox medType;
    public JFXComboBox medId;
    public JFXTextField medPrice;
    public JFXTextField medDesc;
    public DatePicker medExpDate;
    public JFXButton btnEdit;
    public JFXButton btnUpdate;
    public JFXButton btndelete;

    @Setter
    private MedicineFormcontroller medFormController;

    public void initialize(){
        setAllMedId();
    }

    private void setAllMedId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<MedicineDto> idList = MedicineDAOImpl.getAllMedicine();
            for (MedicineDto dto : idList) {
                obList.add(dto.getMedId());
            }
            medId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void medIdOnAction(ActionEvent actionEvent) {

        String id = (String) medId.getValue();

        try {
            if(id!=null){
                MedicineDto dto = MedicineDAOImpl.getMedicineDetails(id);

                medName.setText(dto.getName());
                medQty.setText(dto.getQty());
                medPrice.setText(dto.getPrice());
                medDesc.setText(dto.getDescription());
                medExpDate.setValue(LocalDate.parse(dto.getExpdate()));

                ObservableList<String> obList = FXCollections.observableArrayList();

                obList.add(dto.getType());

                medType.setItems(obList);
                medType.setValue(medType.getItems().get(0));

                setEdit(false);
            }
        }
        catch (SQLException e) {
            //           throw new RuntimeException(e);
        }
    }

    private void setEdit(boolean b) {
        medName.setEditable(b);
        medQty.setEditable(b);
        medPrice.setEditable(b);
        medDesc.setEditable(b);
        medExpDate.setEditable(b);
        medType.setEditable(b);
    }

    public void editOnAction(ActionEvent actionEvent) {

        String medtype = (String)medType.getValue();

        if(medtype!=null){
            btnEdit.setOnAction((e) -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Edit?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        setEdit(true);

                        ObservableList<String> obList = FXCollections.observableArrayList();

                        obList.add("Medicine");
                        obList.add("Injection");

                        medType.setItems(obList);
                    }

            });
        }
    }


    public void upDateOnAction(ActionEvent actionEvent) {

        btnUpdate.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                try {
                    String id = (String) medId.getValue();
                    String name = medName.getText();
                    String qty = medQty.getText();
                    String price = medPrice.getText();
                    String desc = medDesc.getText();
                    String expdate = medExpDate.getValue().toString();
                    String type = (String) medType.getValue();

                    var dto = new MedicineDto(id, name, type, qty, price, desc, expdate);

                    boolean isSaved = MedicineDAOImpl.updateMedicine(dto);

                    if (isSaved) {
                        new SystemAlert(Alert.AlertType.CONFIRMATION,"updated","Medicine updated Successfully..",ButtonType.OK).show();
                        setEdit(false);
                        clearall();
                        setAllMedId();
                        medFormController.initialize();
                    }
                } catch (SQLException | ClassNotFoundException a) {
                    new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                }
            }
        });
    }

    private void clearall() {
        medName.clear();
        medQty.clear();
        medPrice.clear();
        medDesc.clear();
        medExpDate.setValue(null);
        medType.getSelectionModel().clearSelection();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = (String) medId.getValue();

        btndelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {

                    try {
                        boolean isDelete = MedicineDAOImpl.deleteMedicine(id);

                        if (isDelete) {
                            new SystemAlert(Alert.AlertType.CONFIRMATION,"deleted","Medicine Deleted Successfully..",ButtonType.OK).show();
                            setEdit(false);
                            clearall();
                            setAllMedId();
                            medFormController.initialize();
                        }
                    } catch (SQLException | ClassNotFoundException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }

            }
        });
    }
}
