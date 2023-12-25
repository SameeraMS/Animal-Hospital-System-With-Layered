package lk.ijse.ahms.controller.add;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.AppointmentBO;
import lk.ijse.ahms.bo.custom.DoctorBO;
import lk.ijse.ahms.bo.custom.PetBO;
import lk.ijse.ahms.bo.custom.PetOwnerBO;
import lk.ijse.ahms.controller.dashboard.AppointmentFormController;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.dto.PetsDto;
import lk.ijse.ahms.qr.QRGenerator;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.PngToPdfConverter;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class AddApointmentFormController {

    public JFXTextField appointmentId;
    public JFXComboBox<String> cmbDocId;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField petOwnerName;
    public JFXComboBox<String> cmbPetId;
    public JFXComboBox<String> cmbPetOwnerId;
    public JFXTextField petName;

    public JFXTextArea desc;
    public JFXTextField docName;
    public JFXTextField lblAmount;
    public DatePicker datepik;
    public JFXTimePicker timepik;
    @Setter
    private AppointmentFormController appointmentFormController;
    AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.APPOINTMENT);
    PetBO petBO = (PetBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET);
    PetOwnerBO petOwnerBO = (PetOwnerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET_OWNER);
    DoctorBO doctorBO = (DoctorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DOCTOR);


    public void initialize() {
        generateNextId();
        setDateandTime();
        loadAllDoc();
        loadAllPetOwner();
        loadAllPet();
        setEdit(false);

    }

    private void generateNextId() {
        try {
            String appId = appointmentBO.generateNextAppointmentId();
            appointmentId.setText(appId);
            appointmentId.setEditable(false);
        } catch (SQLException e) {
            new SystemAlert(Alert.AlertType.ERROR,"Error", e.getMessage(),ButtonType.OK).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEdit(boolean b) {
        petOwnerName.setEditable(b);
        petName.setEditable(b);
        docName.setEditable(b);
    }


    private void loadAllPet() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PetsDto> idList = petBO.getAllPet();

            for (PetsDto dto : idList) {
                obList.add(dto.getPetId());
            }
            cmbPetId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllPetOwner() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PetOwnerDto> idList = petOwnerBO.getAllPetOwner();

            for (PetOwnerDto dto : idList) {
                obList.add(dto.getOwnerId());
            }
            cmbPetOwnerId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllDoc() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<DoctorDto> idList = doctorBO.getAllDoctor();

            for (DoctorDto dto : idList) {
                obList.add(dto.getDocId());
            }
            cmbDocId.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDateandTime() {
        lblDate.setText(String .valueOf(LocalDate.now()));

        //running time
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(true) {
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    lblTime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    public void DocIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbDocId.getValue();

        try {
            if (id != null) {
                DoctorDto dto = doctorBO.searchDoctor(id);
                docName.setText(dto.getName());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbPetIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbPetId.getValue();

        try {
            if (id != null) {
                PetsDto dto = petBO.searchPet(id);
                petName.setText(dto.getName());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbPetOwnerIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbPetOwnerId.getValue();

        try {
            if (id != null) {
                PetOwnerDto dto = petOwnerBO.searchPetOwner(id);
                petOwnerName.setText(dto.getName());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeAppointmentOnAction(ActionEvent actionEvent) throws WriterException {

        if(Regex.getDoublePattern().matcher(lblAmount.getText()).matches()){

            String id = appointmentId.getText();
            String docname = docName.getText();
            String petname = petName.getText();
            String petownername = petOwnerName.getText();
            String description = desc.getText();
            String amount = lblAmount.getText();
            String date = datepik.getValue().toString();
            String time = timepik.getValue().toString();
            String docid = cmbDocId.getValue();
            String petid = cmbPetId.getValue();
            String petownerid = cmbPetOwnerId.getValue();

            var dto = new AppointmentDto(id, amount, date, time, description, docid, docname, petownerid, petownername, petid, petname);

            if(!id.isEmpty() && !amount.isEmpty() && !date.isEmpty() && !time.isEmpty() && !description.isEmpty() && !docid.isEmpty() && !petid.isEmpty() && !petownerid.isEmpty()) {
                try {
                    boolean isSaved = appointmentBO.saveAppointment(dto);

                    if (isSaved) {
                        appointmentFormController.initialize();

                        //make Qr
                        String filepath = "/Users/sameeramadushan/Documents/final project/Appointment QR/"+ id + " - " + petownername + ".png";
                        boolean isGenerated = QRGenerator.generateQrCode(id, 1250, 1250, filepath);
                        if (isGenerated){

                            String[] imagePath = new String[]{filepath};
                            String pdfOutputPath = "/Users/sameeramadushan/Documents/final project/Appointment QR/"+ id + " - " + petownername + ".pdf";

                            try {
                                PngToPdfConverter.convertImagesToPDF(imagePath, pdfOutputPath);
                                System.out.println("Conversion successful!");
                            } catch (IOException e) {
                                System.err.println("Error during conversion: " + e.getMessage());
                                e.printStackTrace();
                            }

                            PetOwnerDto newdto = petOwnerBO.searchPetOwner(petownerid);

                            assert newdto != null;
                            String email = newdto.getEmail();
                            String subject = "Appointment Placed";
                            String message = "Hi "+petownername+"\nYour appointment has placed on "+date+" at "+time+". "+"Your appointment fee is "+"LKR "+amount+".\nYou can cancel appointment by contact us.";

                            Mail mail = new Mail(email, message, subject, new File(pdfOutputPath));
                            Thread thread = new Thread(mail);

                            mail.valueProperty().addListener((a, oldValue, newValue) -> {
                                if (newValue){
                                    new SystemAlert(Alert.AlertType.INFORMATION,"Email","Mail sent successfully",ButtonType.OK).show();
                                }else {
                                    new SystemAlert(Alert.AlertType.NONE,"Connection Error","Connection Error!",ButtonType.OK).show();
                                }
                            });

                            thread.setDaemon(true);
                            thread.start();

                            new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Appointment Successfully saved..! \n\n QR code in '"+filepath+"'.", ButtonType.OK).show();

                        }

                        initialize();
                        clearFields();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Fill All Details..!", ButtonType.OK).show();
            }
        }else {
            new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please enter a valid amount", ButtonType.OK).show();
        }


    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        appointmentId.clear();
        desc.clear();
        docName.clear();
        petName.clear();
        petOwnerName.clear();
        cmbPetId.getSelectionModel().clearSelection();
        cmbPetOwnerId.getSelectionModel().clearSelection();
        cmbDocId.getSelectionModel().clearSelection();
        lblAmount.clear();
        timepik.setValue(null);
        datepik.setValue(null);
    }


    public void newPetOwnerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setAddApointmentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void newPetOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPets_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetsFormController ownr =  fxmlLoader.getController();
        ownr.setAddApointmentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
