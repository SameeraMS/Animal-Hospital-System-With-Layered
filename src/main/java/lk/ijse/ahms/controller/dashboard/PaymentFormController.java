package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
import lk.ijse.ahms.bo.custom.*;
import lk.ijse.ahms.controller.barcode.BarcodeReadController;
import lk.ijse.ahms.dao.custom.impl.*;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.*;
import lk.ijse.ahms.dto.tm.CartTm;
import lk.ijse.ahms.qr.QrScanController;
import lk.ijse.ahms.smtp.Mail;
import lk.ijse.ahms.util.SystemAlert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class PaymentFormController {
    public Label lbldate;
    public static String scan;

    public Label lbltime;
    public JFXComboBox<String> cmbAppId;
    public JFXTextField txtPetOwner;
    public JFXTextField txtPet;
    public JFXTextField txtpaymentId;
    public JFXComboBox<String> cmbpresid;
    public JFXComboBox<String> cmbmedid;
    public JFXTextField txtmedname;
    public Label lblqtyOnHand;
    public JFXTextField txtqty;
    public Label lblamount;
    public Label lbltype;
    public TableView tblcart;
    public TableColumn colid;
    public TableColumn colname;
    public TableColumn colunitprice;
    public TableColumn colqty;
    public TableColumn coltotal;
    public Label lbltotal;
    public Label lblappointmentAmount;
    public JFXButton btndelete;
    public JFXButton btnaddtocart;
    public JFXButton btnplaceorder;
    public JFXButton btnqr;
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    MedicineBO medicineBO = (MedicineBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MEDICINE);
    PrescriptionDetailsBO presDetailsBO = (PrescriptionDetailsBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRESCRIPTION_DETAIL);
    AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.APPOINTMENT);
    PrescriptionBO prescriptionBO = (PrescriptionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRESCRIPTION);
    PetOwnerBO petOwnerBO = (PetOwnerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET_OWNER);


    public void initialize() {
        setCellValueFactory();
        generateNextPayId();
        loadAllAppointments();
        loadAllMedicine();
        setDateTime();

    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("medId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
    }

    private void generateNextPayId() {
        try {
            String payId = paymentBO.generateNextPaymentId();
            txtpaymentId.setText(payId);
            txtpaymentId.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDateTime() {
        lbldate.setText(String.valueOf(LocalDate.now()));

        //time
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
                    lbltime.setText(timenow);
                });
            }
        });
        thread.start();
    }

    private void loadAllMedicine() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<MedicineDto> idList = medicineBO.getAllMedicine();

            for (MedicineDto dto : idList) {
                obList.add(dto.getMedId());
            }

            cmbmedid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllAppointments() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<AppointmentDto> idList = appointmentBO.getAllAppointment();

            for (AppointmentDto dto : idList) {
                obList.add(dto.getAppointmentId());
            }

            cmbAppId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void appointmentIdOnAction(ActionEvent actionEvent) {
        String AppId = cmbAppId.getValue();

        try {
            if (AppId != null) {
                AppointmentDto dto = appointmentBO.searchAppointment(AppId);

                txtPetOwner.setText(dto.getPetOwnerName());
                txtPet.setText(dto.getPetName());
                lblappointmentAmount.setText(dto.getAmount());
                lbltotal.setText(dto.getAmount());

                PrescriptionDto presDto = prescriptionBO.searchPrescriptionbyAppId(AppId);

                ObservableList<String> obList = FXCollections.observableArrayList();

                if (presDto != null) {
                    obList.add(presDto.getPrescriptionId());
                    cmbpresid.setItems(obList);
                } else {
                    obList.add("No Prescription");
                    cmbpresid.setItems(obList);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void medIdOnAction(ActionEvent actionEvent) {
        String id = cmbmedid.getValue();

        try {
            if (id != null) {
                MedicineDto dto = medicineBO.searchMedicine(id);

                txtmedname.setText(dto.getName());
                lblqtyOnHand.setText(dto.getQty());
                lbltype.setText(dto.getType());
                lblamount.setText(dto.getPrice());
            }

            txtqty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        String medid = cmbmedid.getValue();
        String medname = txtmedname.getText();
        int qty = Integer.parseInt(txtqty.getText());
        double unitPrice = Double.parseDouble(lblamount.getText());
        double tot = unitPrice * qty;

        if (qty <= Integer.parseInt(lblqtyOnHand.getText())) {

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblcart.getItems().size(); i++) {
                    if (colid.getCellData(i).equals(medid)) {
                        int col_qty = Integer.valueOf(String.valueOf(colqty.getCellData(i)));
                        qty += col_qty;
                        tot = unitPrice * qty;

                        obList.get(i).setQty(String.valueOf(qty));
                        obList.get(i).setTotal(String.valueOf(tot));

                        calculateTotal();
                        tblcart.refresh();

                        return;
                    }
                }
            }


            var cartTm = new CartTm(medid, medname, String.valueOf(unitPrice), String.valueOf(qty), String.valueOf(tot));

            obList.add(cartTm);

            tblcart.setItems(obList);
            calculateTotal();
            txtqty.clear();

        } else {
            new SystemAlert(Alert.AlertType.ERROR, "Out of stock", "Out of stock", ButtonType.OK).show();
        }


    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblcart.getItems().size(); i++) {
            total += Double.parseDouble((String) coltotal.getCellData(i));
        }
        total = total + Double.parseDouble(lblappointmentAmount.getText());
        lbltotal.setText(String.valueOf(total));
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        btndelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblcart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblcart.refresh();
                calculateTotal();
            }
        });
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, JRException, InterruptedException {
        String payId = txtpaymentId.getText();
        String date = lbldate.getText();
        String appointId = cmbAppId.getValue();
        String total = lbltotal.getText();
        String appFee = lblappointmentAmount.getText();


        List<CartTm> cartTmList = new ArrayList<>();
                for (int i = 0; i < tblcart.getItems().size(); i++) {
                    cartTmList.add((CartTm) tblcart.getItems().get(i));
                }
        System.out.println("place order on action -> "+cartTmList);
                var placeOrderDto = new PlaceOrderDto(payId, date, total, appointId, cartTmList);
                boolean isSuccess = false;
                try {
                   // isSuccess = placeOrderModel.placeOrder(placeOrderDto);
                    System.out.println("place order model -> "+placeOrderDto.getCartTmList());
                    Connection connection = null;
                    try {
                        connection = DbConnection.getInstance().getConnection();
                        connection.setAutoCommit(false);

                        boolean isOrderSaved = paymentDAOImpl.savePayment(payId, date, total, appointId);
                        if (isOrderSaved) {
                            boolean isUpdated = medicineDAOImpl.updateMed(placeOrderDto.getCartTmList());
                            if(isUpdated) {
                                boolean isOrderDetailSaved = presDetailsDAOImpl.saveOrderDetails(placeOrderDto.getPayId(), placeOrderDto.getCartTmList());
                                if (isOrderDetailSaved) {
                                    connection.commit();
                                    isSuccess = true;
                                }
                            }
                        }
                    } catch (SQLException e) {
                        connection.rollback();
                    } finally {
                        connection.setAutoCommit(true);
                    }


                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (isSuccess) {
                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Order Placed Successfully..!",ButtonType.OK).show();

                    String outputPath = printBill(payId, total, appointId,appFee);
                    sendBill(appointId,outputPath);
                    clearall();
                }


    }

    private void sendBill(String appointId, String outputPath) {
        String pdfOutputPath = outputPath;
        String appointmentId = appointId;


        try {
            AppointmentDto dto = appointmentBO.searchAppointment(appointmentId);
            String ownerId = dto.getPetOwnerId();

            PetOwnerDto ownerDto = petOwnerBO.searchPetOwner(ownerId);
            String email = ownerDto.getEmail();

            String subject = "Payment Done Successfully";
            String message = "Thank you for joining us.\n\n We have received your payment successfully. \n\nYour Bill is attached here. \n\nRegards,\nAnimal Hospital System";

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private String printBill(String payId, String total, String appointId, String appFee) throws JRException, SQLException {


        HashMap hashMap = new HashMap();
        hashMap.put("parameterPaymentId", payId);
        hashMap.put("appointmentId", appointId);
        hashMap.put("netTotal", total);
        hashMap.put("appfee", appFee);

        InputStream resourceAsStream = getClass().getResourceAsStream("/report/Bill.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        /*
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("select\n" +
                "    p.payment_id,\n" +
                "    p.med_id,\n" +
                "    p.med_name,\n" +
                "    p.qty,\n" +
                "    p.unit_price,\n" +
                "    p.amount\n" +
                "from print p\n" +
                "         join payment pa\n" +
                "              on p.payment_id = pa.payment_id\n" +
                "where p.payment_id ='"+ payId +"'");
        load.setQuery(jrDesignQuery);

         */

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
      //  JasperPrint jasperPrint = JasperFillManager.fillReport(
       //         jasperReport, hashMap, new JREmptyDataSource());
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                hashMap,
                DbConnection.getInstance().getConnection());

        JasperViewer.viewReport(jasperPrint, false);

        String filePath = "/Users/sameeramadushan/Documents/final project/reports/";

        JasperExportManager.exportReportToPdfFile(jasperPrint, filePath + payId + ".pdf");
        System.out.println("report save done");

        String pdfOutputPath = filePath + payId + ".pdf";

        return pdfOutputPath;

    }

    private void clearall() {
        txtmedname.clear();
        txtqty.clear();
        txtmedname.requestFocus();
        tblcart.getItems().clear();
        lbltotal.setText("0");
        lblappointmentAmount.setText("0");
        cmbpresid.getItems().clear();
        cmbmedid.getItems().clear();
        cmbAppId.getItems().clear();
        initialize();
    }

    public void qtyOnAction(ActionEvent actionEvent) {
        addToCartOnAction(actionEvent);
    }

    public void qrOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/qr/QrScanForm.fxml"));
        Parent root = fxmlLoader.load();

         QrScanController qr =  fxmlLoader.getController();
         qr.setPaymentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();


    }

    public void setAppId(String c) {
        cmbAppId.setValue(c);
        appointmentIdOnAction(c);
    }

    public void appointmentIdOnAction(String c) {
        String AppId = c;

        try {
            if (AppId != null) {
                AppointmentDto dto = appointmentBO.searchAppointment(AppId);

                txtPetOwner.setText(dto.getPetOwnerName());
                txtPet.setText(dto.getPetName());
                lblappointmentAmount.setText(dto.getAmount());

                PrescriptionDto presDto = prescriptionBO.searchPrescriptionbyAppId(AppId);

                ObservableList<String> obList = FXCollections.observableArrayList();

                if (presDto != null) {
                    obList.add(presDto.getPrescriptionId());
                    cmbpresid.setItems(obList);
                } else {
                    obList.add("No Prescription");
                    cmbpresid.setItems(obList);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void barcodeOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/barcodeReader/BarcodeRead.fxml"));
        Parent root = fxmlLoader.load();

        BarcodeReadController bc =  fxmlLoader.getController();
        bc.setPaymentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
