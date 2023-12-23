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
import lk.ijse.ahms.controller.dashboard.EmployeeFormController;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dao.custom.impl.DoctorDAOImpl;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InfoDoctorFormController {
    public JFXButton btndelete;

    public JFXButton btnupdate;
    public JFXComboBox<String> cmbDocId;
    public JFXTextField txtname;
    public JFXTextField txttel;
    public JFXTextField txtemail;
    public JFXButton btnedit;

    @Setter
    private EmployeeFormController empFormController;

    public void initialize(){
        setAllDocId();
    }

    private void setAllDocId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<DoctorDto> idList = DoctorDAOImpl.getAllDoctor();

            for (DoctorDto dto : idList) {
                obList.add(dto.getDocId());
            }

            cmbDocId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = (String) cmbDocId.getValue();

        btndelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                    try {
                        boolean isDelete = DoctorDAOImpl.deleteDoctor(id);

                        if (isDelete) {
                            new SystemAlert(Alert.AlertType.CONFIRMATION,"Deleted", "Doctor Deleted!",ButtonType.OK).show();
                            setEdit(false);
                            clearall();
                            setAllDocId();
                            empFormController.initialize();
                        }
                    } catch (SQLException | ClassNotFoundException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }

            }
        });
    }

    public void updateOnAction(ActionEvent actionEvent) {


        btnupdate.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                try {
                    String id = (String) cmbDocId.getValue();
                    String name = txtname.getText();
                    String tel = txttel.getText();
                    String mail = txtemail.getText();

                    var dto = new DoctorDto(id, name, mail, tel);

                    boolean isSaved = DoctorDAOImpl.updateDoctor(dto);

                    if (isSaved) {
                        new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Doctor updated Successfully..!", ButtonType.OK).show();
                        setEdit(false);
                        clearall();
                        setAllDocId();
                        empFormController.initialize();
                    }
                } catch (SQLException | ClassNotFoundException a) {
                    new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                }
            }
        });
    }

    private void clearall() {
        txtname.clear();
        txttel.clear();
        txtemail.clear();
    }

    public void editOnAction(ActionEvent actionEvent) {

        btnedit.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Edit?", yes, no).showAndWait();


                if (type.orElse(no) == yes) {
                    setEdit(true);
                }

        });
    }

    public void cmbDocIdOnAction(ActionEvent actionEvent) {

        String id = (String) cmbDocId.getValue();

        try {
            if(id!=null) {
                DoctorDto dto = DoctorDAOImpl.getDocDetails(id);

                txtname.setText(dto.getName());
                txttel.setText(dto.getTel());
                txtemail.setText(dto.getEmail());

                setEdit(false);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEdit(boolean b) {
        txtname.setEditable(b);
        txttel.setEditable(b);
        txtemail.setEditable(b);
    }

}
