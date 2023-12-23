package lk.ijse.ahms.controller.info;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.add.AddPetOwnerFormController;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.dto.PetsDto;
import lk.ijse.ahms.dao.custom.impl.PetDAOImpl;
import lk.ijse.ahms.dao.custom.impl.PetOwnerDAOImpl;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InfoPetsFormController {
    public JFXButton btndelete;
    public JFXButton btnUpdate;
    public JFXButton btnEdit;
    public JFXTextField petName;
    public JFXTextField petAge;
    public JFXComboBox<String> cmbOwnerId;
    public JFXTextField petType;
    public JFXButton btnAdd;
    public JFXComboBox<String> cmbPetGender;
    public JFXComboBox<String> cmbPetId;
    @Setter
    private PetsFormController petsFormController;

    public void initialize() {
            loadAllPetId();
    }

    private void loadAllPetId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PetsDto> idList = PetDAOImpl.getAllPets();
            for (PetsDto dto : idList) {
                obList.add(dto.getPetId());
            }
            cmbPetId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = (String) cmbPetId.getValue();


        btndelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                    try {
                        boolean isDelete = PetDAOImpl.deletePet(id);

                        if (isDelete) {
                            new SystemAlert(Alert.AlertType.CONFIRMATION,"deleted","Pet Deleted successfully",ButtonType.OK).show();
                            setEdit(false);
                            clearall();
                            loadAllPetId();
                            petsFormController.initialize();
                        }
                    } catch (SQLException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }

            }
        });
    }

    private void clearall() {
        petName.clear();
        petAge.clear();
        petType.clear();
        cmbPetGender.getSelectionModel().clearSelection();
        cmbOwnerId.getSelectionModel().clearSelection();
    }

    public void upDateOnAction(ActionEvent actionEvent) {

        btnUpdate.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                try {
                    String id = (String) cmbPetId.getValue();
                    String name = petName.getText();
                    String age = petAge.getText();
                    String type = petType.getText();
                    String ownerId = cmbOwnerId.getValue();
                    String gender = cmbPetGender.getValue();

                    var dto = new PetsDto(id, name, age, gender, type, ownerId);

                    boolean isSaved = PetDAOImpl.updatePet(dto);

                    if (isSaved) {
                        new SystemAlert(Alert.AlertType.CONFIRMATION,"updated","Pet Updated successfully",ButtonType.OK).show();
                        setEdit(false);
                        clearall();
                        loadAllPetId();
                        petsFormController.initialize();
                    }
                } catch (SQLException a) {
                    new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                }
            }
        });
    }

    public void editOnAction(ActionEvent actionEvent) {

            btnEdit.setOnAction((e) -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Edit?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        setEdit(true);

                        ObservableList<String> obList = FXCollections.observableArrayList();

                        obList.add("Male");
                        obList.add("Female");

                        cmbPetGender.setItems(obList);

                        ObservableList<String> obList2 = FXCollections.observableArrayList();

                        try {
                            List<PetOwnerDto> idList = PetOwnerDAOImpl.getAllOwners();
                            for (PetOwnerDto dto : idList) {
                                obList2.add(dto.getOwnerId());
                            }
                            cmbOwnerId.setItems(obList2);

                        } catch (SQLException e1) {
                            throw new RuntimeException(e1);
                        }

                    }

            });

    }

    public void addOwnerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setInfoPetFormController(this);
        ownr.setPetFormController(petsFormController);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void cmbPetIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbPetId.getValue();

        try {
            if(id!=null){
                PetsDto dto = PetDAOImpl.getPetsDetails(id);

                petName.setText(dto.getName());
                petAge.setText(dto.getAge());
                petType.setText(dto.getType());


                ObservableList<String> obList = FXCollections.observableArrayList();

                obList.add(dto.getGender());

                cmbPetGender.setItems(obList);
                cmbPetGender.setValue(cmbPetGender.getItems().get(0));


                ObservableList<String> obList1 = FXCollections.observableArrayList();

                obList1.add(dto.getOwnerId());

                cmbOwnerId.setItems(obList1);
                cmbOwnerId.setValue(cmbOwnerId.getItems().get(0));


                setEdit(false);
            }
        }
        catch (SQLException e) {
                      throw new RuntimeException(e);
        }
    }

    private void setEdit(boolean b) {
        petName.setEditable(b);
        petAge.setEditable(b);
        petType.setEditable(b);
        cmbPetGender.setEditable(b);
        cmbOwnerId.setEditable(b);

    }
}
