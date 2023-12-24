package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.PetOwnerBO;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lk.ijse.ahms.controller.info.InfoPetsFormController;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.dao.custom.impl.PetOwnerDAOImpl;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;

public class AddPetOwnerFormController {
    public JFXTextField ownerId;
    public JFXTextField ownerName;
    public JFXTextField ownerEmail;
    public JFXTextField ownerTel;

    @Setter
    private InfoPetsFormController infoPetFormController;

    @Setter
    private PetsFormController PetFormController;

    @Setter
    private AddPetsFormController addPetFormController;

    @Setter
    private AddApointmentFormController addApointmentFormController;

    PetOwnerBO petOwnerBO = (PetOwnerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET_OWNER);

    public void initialize() {
        generateNextId();
    }

    private void generateNextId() {
        try {
            String payId = petOwnerBO.generateNextPetOwnerId();
            ownerId.setText(payId);
            ownerId.setEditable(false);
        } catch (SQLException e) {
            new SystemAlert(Alert.AlertType.ERROR,"Error",e.getMessage(), ButtonType.OK).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void idOnAction(ActionEvent actionEvent) {
        ownerName.requestFocus();
    }

    public void nameOnAction(ActionEvent actionEvent) {
        ownerEmail.requestFocus();
    }

    public void mailOnAction(ActionEvent actionEvent) {
        ownerTel.requestFocus();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        ownerId.clear();
        ownerName.clear();
        ownerEmail.clear();
        ownerTel.clear();
    }

    public void telOnAction(ActionEvent actionEvent) {

    }

    public void saveOnAction(ActionEvent actionEvent) {

        if(Regex.getNamePattern().matcher(ownerName.getText()).matches()){
            if(Regex.getEmailPattern().matcher(ownerEmail.getText()).matches()){
                if(Regex.getMobilePattern().matcher(ownerTel.getText()).matches()){

                    String id = ownerId.getText();
                    String name = ownerName.getText();
                    String email = ownerEmail.getText();
                    String tel = ownerTel.getText();

                    var dto = new PetOwnerDto(id, name, email, tel);

                    if(!id.isEmpty() && !name.isEmpty() && !email.isEmpty() && !tel.isEmpty()) {
                        try {
                            boolean isSaved = petOwnerBO.savePetOwner(dto);

                            if (isSaved) {
                                new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Pet Owner saved Successfully..!", ButtonType.OK).show();
                                clearFields();


                                if(infoPetFormController != null) {
                                    infoPetFormController.initialize();
                                } else if(addPetFormController != null) {
                                    addPetFormController.initialize();
                                } else if(addApointmentFormController != null) {
                                    addApointmentFormController.initialize();
                                } else if(PetFormController != null) {
                                    PetFormController.initialize();
                                }
                                initialize();
                            }
                        } catch (SQLException e) {
                            new SystemAlert(Alert.AlertType.ERROR,"Error",e.getMessage(), ButtonType.OK).show();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Fill All Details..!", ButtonType.OK).show();
                    }
                }else{
                    new SystemAlert(Alert.AlertType.INFORMATION,"Information","Invalid Phone Number!", ButtonType.OK).show();
                }
            } else {
                new SystemAlert(Alert.AlertType.INFORMATION,"Information","Invalid Email!", ButtonType.OK).show();
            }
        }else{
            new SystemAlert(Alert.AlertType.INFORMATION,"Information","Invalid Name!", ButtonType.OK).show();
        }


    }
}
