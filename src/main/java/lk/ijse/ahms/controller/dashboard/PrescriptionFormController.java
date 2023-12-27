package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.AppointmentBO;
import lk.ijse.ahms.bo.custom.PetOwnerBO;
import lk.ijse.ahms.bo.custom.PrescriptionBO;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.SystemAlert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class PrescriptionFormController {

    public JFXTextField txtPresId;
    public JFXComboBox<String> cmbAppointment;
    public JFXTextArea txtDesc;
    public JFXButton btnSave;
    public JFXTextField txtsearch;

    public JFXButton btnupdate;
    public JFXButton btndelete;
    public JFXButton btnClear;
    public JFXComboBox cmbPres;
    PrescriptionBO prescriptionBO = (PrescriptionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRESCRIPTION);
    AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.APPOINTMENT);
    PetOwnerBO petOwnerBO = (PetOwnerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET_OWNER);


    public  void initialize() {
        generateNextId();
        loadAllAppId();
        loadAllPresId();
    }

    private void loadAllPresId() {

        try {
            List<PrescriptionDto> dto = prescriptionBO.getAllPrescription();

            for (PrescriptionDto d : dto) {
                cmbPres.getItems().add(d.getPrescriptionId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextId() {
        try {
            String presId = prescriptionBO.generateNextPrescriptionId();
            txtPresId.setText(presId);
            txtPresId.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllAppId() {

        try {
            List<AppointmentDto> dto = appointmentBO.getAllAppointment();

            for (AppointmentDto d : dto) {
                cmbAppointment.getItems().add(d.getAppointmentId());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String appointmentId = cmbAppointment.getValue();
        String description = txtDesc.getText();
        String prescriptionId = txtPresId.getText();

        var dto = new PrescriptionDto(prescriptionId, description, appointmentId);

        if (!prescriptionId.isEmpty() && !description.isEmpty() && !appointmentId.isEmpty()) {
            try {
                boolean isSaved = prescriptionBO.savePrescription(dto);

                if (isSaved) {
                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Prescription Saved Successfully..!", ButtonType.OK).show();
                    initialize();
                    clearFields();
                } else {
                    new SystemAlert(Alert.AlertType.ERROR,"Error","Prescription Not Saved..!", ButtonType.OK).show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void clearFields() {
        txtPresId.clear();
        txtDesc.clear();
        cmbAppointment.getSelectionModel().clearSelection();

    }

    public void searchOnAction(ActionEvent actionEvent) {
        searchPrescription();
    }

    private void searchPrescription() {
        String presid = txtsearch.getText();

        try {
            PrescriptionDto dto = prescriptionBO.searchPrescription(presid);

            if (dto == null) {
                new SystemAlert(Alert.AlertType.ERROR,"Error","Prescription Not Found..!", ButtonType.OK).show();
            } else {
                txtDesc.setText(dto.getDescription());
                txtPresId.setText(dto.getPrescriptionId());
                cmbAppointment.setValue(dto.getAppointmentId());
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String presid = txtPresId.getText();
        String description = txtDesc.getText();
        String appointmentId = cmbAppointment.getValue();

        var dto = new PrescriptionDto(presid, description, appointmentId);

        if (!presid.isEmpty() && !description.isEmpty() && !appointmentId.isEmpty()) {
            try {
                boolean isUpdate = prescriptionBO.updatePrescription(dto);

                if (isUpdate) {
                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Prescription Updated Successfully..!", ButtonType.OK).show();
                    initialize();
                    clearFields();
                } else {
                    new SystemAlert(Alert.AlertType.ERROR,"Error","Prescription Not Updated..!", ButtonType.OK).show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String presid = txtPresId.getText();

        try {
            boolean isDelete = prescriptionBO.deletePrescription(presid);

            if (isDelete) {
                new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Prescription Deleted Successfully..!", ButtonType.OK).show();
                initialize();
                clearFields();
            } else {
                new SystemAlert(Alert.AlertType.ERROR,"Error","Prescription Not Deleted..!", ButtonType.OK).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtsearchOnAction(ActionEvent actionEvent) {
        searchPrescription();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
        generateNextId();
    }

    public void printOnAction(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {

        String appointmentId = cmbAppointment.getValue();
        String description = txtDesc.getText();
        String prescriptionId = txtPresId.getText();


        if (!appointmentId.isEmpty() && !description.isEmpty() && !prescriptionId.isEmpty()) {

            HashMap hashMap = new HashMap();
            hashMap.put("AppointNo", appointmentId);
            hashMap.put("PresId", prescriptionId);
            hashMap.put("description", description);

            InputStream resourceAsStream = getClass().getResourceAsStream("/report/Prescription.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, hashMap, new JREmptyDataSource());

            JasperViewer.viewReport(jasperPrint, false);

            String filePath = "/Users/sameeramadushan/Documents/final project/reports/";

            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath + prescriptionId + ".pdf");
            System.out.println("report done");

            String pdfOutputPath = filePath + prescriptionId + ".pdf";

            AppointmentDto dto = appointmentBO.searchAppointment(appointmentId);
            String petOwnerId = dto.getPetOwnerId();

            PetOwnerDto ownerDetails = petOwnerBO.searchPetOwner(petOwnerId);

            String email = ownerDetails.getEmail();

            String subject = "Animal Hospital";
            String message = "Your Prescription is attached here. \n\nRegards,\nAnimal Hospital System";

            Mail nmail = new Mail(email, message, subject, new File(pdfOutputPath));
            Thread thread = new Thread(nmail);

            nmail.valueProperty().addListener((a, oldValue, newValue) -> {
                if (newValue) {
                    new SystemAlert(Alert.AlertType.INFORMATION, "Email", "Mail sent successfully", ButtonType.OK).show();
                } else {
                    new SystemAlert(Alert.AlertType.NONE, "Connection Error", "Connection Error!", ButtonType.OK).show();
                }
            });

            thread.setDaemon(true);
            thread.start();

        } else {
            new SystemAlert(Alert.AlertType.ERROR,"Error","Please Fill All Fields..!", ButtonType.OK).show();
        }

    }

    public void cmbpresOnAction(ActionEvent actionEvent) {
        String presId = (String) cmbPres.getValue();

        try {
            PrescriptionDto dto = prescriptionBO.searchPrescription(presId);

                txtDesc.setText(dto.getDescription());
                txtPresId.setText(dto.getPrescriptionId());
                cmbAppointment.setValue(dto.getAppointmentId());


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
