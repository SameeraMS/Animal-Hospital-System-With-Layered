package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.MedicineBO;
import lk.ijse.ahms.controller.add.AddMedicineFormController;
import lk.ijse.ahms.controller.info.InfoMedicineFormController;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.tm.MedicineTm;
import lk.ijse.ahms.dao.custom.impl.MedicineDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MedicineFormcontroller {
    public TableView<MedicineTm> tblMed;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colPrice;
    public TableColumn colDesc;
    public TableColumn colExp;
    public TableColumn colQty;
    public JFXTextField txtsearchid;
    public JFXButton btnsearch;
    MedicineBO medicineBO = (MedicineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MEDICINE);


    public void initialize() {
        tblMed.getItems().clear();
        setCellValueFactoryMedicine();
        loadAllMedicine();
    }

    private void setCellValueFactoryMedicine() {
        colId.setCellValueFactory(new PropertyValueFactory<>("MedId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colExp.setCellValueFactory(new PropertyValueFactory<>("ExpDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
    }

    private void loadAllMedicine() {
        System.out.println("Loading all Medicine");
        ObservableList<MedicineTm> obList = FXCollections.observableArrayList();

        try {
            List<MedicineDto> medicineDtos = medicineBO.getAllMedicine();

            for (MedicineDto dto : medicineDtos) {

                obList.add(
                        new MedicineTm(
                                dto.getMedId(),
                                dto.getName(),
                                dto.getType(),
                                dto.getPrice(),
                                dto.getDescription(),
                                dto.getExpdate(),
                                dto.getQty()
                        )
                );
            }
            tblMed.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMedOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addMedicine_form.fxml"));
        Parent root = fxmlLoader.load();

        AddMedicineFormController med =  fxmlLoader.getController();
        med.setMedFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void infoMedOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/info/infoMedicine_form.fxml"));
        Parent root = fxmlLoader.load();

        InfoMedicineFormController med =  fxmlLoader.getController();
        med.setMedFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void serachidOnAction(ActionEvent actionEvent) {
        searchOnAction(actionEvent);

    }

    public void searchOnAction(ActionEvent actionEvent) {
        String id = txtsearchid.getText();

        try {
            MedicineDto dtos = medicineBO.searchMedicine(id);

            if (dtos == null) {
                new Alert(Alert.AlertType.ERROR, "Medicine not found").show();
            } else {

                ObservableList<MedicineTm> obList1 = FXCollections.observableArrayList();

                    obList1.add(
                            new MedicineTm(
                                    dtos.getMedId(),
                                    dtos.getName(),
                                    dtos.getType(),
                                    dtos.getPrice(),
                                    dtos.getDescription(),
                                    dtos.getExpdate(),
                                    dtos.getQty()
                            )
                    );

                tblMed.setItems(obList1);
            }
            } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void clearsearchOnAction(ActionEvent actionEvent) {
        txtsearchid.clear();
        txtsearchid.requestFocus();
    }
}
