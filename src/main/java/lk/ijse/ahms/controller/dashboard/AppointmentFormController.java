package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.AppointmentBO;
import lk.ijse.ahms.controller.add.AddApointmentFormController;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.tm.AppointmentTm;
import lk.ijse.ahms.dao.custom.impl.AppointmentDAOImpl;
import lk.ijse.ahms.qr.QrScanController;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.SystemAlert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AppointmentFormController {

    public TableView tblAppointments;
    public TableColumn colId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colDesc;
    public TableColumn colDocId;
    public TableColumn colPetOwnerId;
    public JFXTextField txtAppointId;
    public JFXButton btnDelete;
    public JFXButton btnappointment;
    public JFXButton btnsearch;
    public JFXTextField txtrecmail;
    private ObservableList<AppointmentTm> obList = FXCollections.observableArrayList();

    AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.APPOINTMENT);

    public void initialize() {
        cleartable();
        setCellValueFactoryAppointments();
        loadAllAppointments();
    }

    private void cleartable() {
        tblAppointments.getItems().clear();
    }

    private void setCellValueFactoryAppointments() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colDocId.setCellValueFactory(new PropertyValueFactory<>("DocId"));
        colPetOwnerId.setCellValueFactory(new PropertyValueFactory<>("PetOwnerId"));
    }

    private void loadAllAppointments() {
        System.out.println("Loading all Appointments");


        try {
            List<AppointmentDto> AppointmentDtos = appointmentBO.getAllAppointment();

            for (AppointmentDto dto : AppointmentDtos) {

                obList.add(
                        new AppointmentTm(
                                dto.getAppointmentId(),
                                dto.getDate(),
                                dto.getTime(),
                                dto.getDescription(),
                                dto.getDoctorId(),
                                dto.getPetOwnerId()
                        )
                );
            }
            tblAppointments.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeApointmentOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addApointment_form.fxml"));
        Parent root = fxmlLoader.load();

        AddApointmentFormController appointment =  fxmlLoader.getController();
        appointment.setAppointmentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void searchOnAction(ActionEvent actionEvent) {
        String id = txtAppointId.getText();

        try {
            AppointmentDto dto = appointmentBO.searchAppointment(id);

            ObservableList<AppointmentTm> obList1 = FXCollections.observableArrayList();

                obList1.add(
                        new AppointmentTm(
                                dto.getAppointmentId(),
                                dto.getDate(),
                                dto.getTime(),
                                dto.getDescription(),
                                dto.getDoctorId(),
                                dto.getPetOwnerId()
                        )
                );

            tblAppointments.setItems(obList1);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void idOnAction(ActionEvent actionEvent) {
        searchOnAction(actionEvent);
    }

    public void deleteOnAction(ActionEvent actionEvent) {

        btnDelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblAppointments.getSelectionModel().getSelectedIndex();
                AppointmentTm selectedItem = (AppointmentTm) tblAppointments.getSelectionModel().getSelectedItem();

                String id = selectedItem.getId();

                try {
                    boolean isDelete = appointmentBO.deleteAppointment(id);

                    if (isDelete) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Appoint Deleted!").show();
                        obList.remove(focusedIndex);
                        tblAppointments.refresh();
                        initialize();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

    }

    public void allappointmentsOnAction(ActionEvent actionEvent) throws JRException, SQLException {

            InputStream resourceAsStream = getClass().getResourceAsStream("/report/allappointments.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);

    }

    public void sendOnAction(ActionEvent actionEvent) throws JRException, SQLException {

        String mail = txtrecmail.getText();

        if (Regex.getEmailPattern().matcher(mail).matches()) {

            InputStream resourceAsStream = getClass().getResourceAsStream("/report/allappointments.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    DbConnection.getInstance().getConnection());

            JasperViewer.viewReport(jasperPrint, false);

            String filePath = "/Users/sameeramadushan/Documents/final project/reports/";

            JasperExportManager.exportReportToPdfFile(jasperPrint, filePath + mail + ".pdf");
            System.out.println("report done");

            String pdfOutputPath = filePath + mail + ".pdf";

            String email = mail;
            String subject = "All Appointments";
            String message = "All Appointments";

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
            new SystemAlert(Alert.AlertType.ERROR, "Information ", "Invalid Email", ButtonType.OK).show();
        }
    }

    public void clearsearchOnAction(ActionEvent actionEvent) {
        txtAppointId.clear();
        txtAppointId.requestFocus();
    }

    public void clearsendOnAction(ActionEvent actionEvent) {
        txtrecmail.clear();
        txtrecmail.requestFocus();
    }
    public void qrOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/qr/QrScanForm.fxml"));
        Parent root = fxmlLoader.load();

        QrScanController qr =  fxmlLoader.getController();
        qr.setAppointmentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();


    }


}
