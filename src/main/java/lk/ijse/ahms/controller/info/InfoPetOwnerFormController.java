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
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.PetOwnerBO;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.dao.custom.impl.PetOwnerDAOImpl;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InfoPetOwnerFormController {
    public JFXButton btndelete;
    public JFXButton btnUpdate;
    public JFXButton btnEdit;
    public JFXComboBox<String> cmbOwnerId;
    public JFXTextField ownerName;
    public JFXTextField ownerMail;
    public JFXTextField ownerTel;
    PetOwnerBO petOwnerBO = (PetOwnerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET_OWNER);

    @Setter
    private PetsFormController petsFormController;

    public void initialize() {
        loadAllOwners();
        editAccess(false);
    }

    private void loadAllOwners() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PetOwnerDto> idList = petOwnerBO.getAllPetOwner();

            for (PetOwnerDto dto : idList) {
                obList.add(dto.getOwnerId());
            }
            cmbOwnerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = (String) cmbOwnerId.getValue();

        btndelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                    try {
                        boolean isDelete = petOwnerBO.deletePetOwner(id);

                        if (isDelete) {
                            new SystemAlert(Alert.AlertType.CONFIRMATION,"deleted","PetOwner Deleted Successfully..",ButtonType.OK).show();
                            editAccess(false);
                            clearall();
                            loadAllOwners();
                            petsFormController.initialize();
                        }
                    } catch (SQLException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

            }
        });

    }

    private void clearall() {
        ownerName.clear();
        ownerMail.clear();
        ownerTel.clear();
        cmbOwnerId.getSelectionModel().clearSelection();
    }

    public void editOnAction(ActionEvent actionEvent) {

            btnEdit.setOnAction((e) -> {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Edit?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        editAccess(true);
                    }

            });


    }

    private void editAccess(boolean b) {
        ownerName.setEditable(b);
        ownerMail.setEditable(b);
        ownerTel.setEditable(b);
    }

    public void upDateOnAction(ActionEvent actionEvent) {

        btnUpdate.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                try {
                    String id = (String) cmbOwnerId.getValue();
                    String name = ownerName.getText();
                    String mail = ownerMail.getText();
                    String tel = ownerTel.getText();


                    var dto = new PetOwnerDto(id, name, mail, tel);

                    boolean isSaved = petOwnerBO.updatePetOwner(dto);

                    if (isSaved) {
                        new SystemAlert(Alert.AlertType.CONFIRMATION,"updated","PetOwner Updated Successfully..",ButtonType.OK).show();
                        editAccess(false);
                        clearall();
                        loadAllOwners();
                        petsFormController.initialize();
                    }
                } catch (SQLException a) {
                    new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void cmbOwnerIdOnAction(ActionEvent actionEvent) {

        String id = (String) cmbOwnerId.getValue();

        try {
            if(id!=null){
                PetOwnerDto dto = petOwnerBO.searchPetOwner(id);

                ownerName.setText(dto.getName());
                ownerMail.setText(dto.getEmail());
                ownerTel.setText(dto.getTel());

                editAccess(false);
            }
        }
        catch (SQLException e) {
                       throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
