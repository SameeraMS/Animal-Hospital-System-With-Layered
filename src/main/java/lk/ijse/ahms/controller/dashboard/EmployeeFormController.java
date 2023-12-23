package lk.ijse.ahms.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.add.AddDocFormcontroller;
import lk.ijse.ahms.controller.add.AddEmployeeFormController;
import lk.ijse.ahms.controller.info.InfoDoctorFormController;
import lk.ijse.ahms.controller.info.InfoEmployeeFormController;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dto.tm.DoctorTm;
import lk.ijse.ahms.dto.tm.EmployeeTm;
import lk.ijse.ahms.dao.custom.impl.DoctorDAOImpl;
import lk.ijse.ahms.dao.custom.impl.EmployeeDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    public TableView<EmployeeTm> tblEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colTel;
    public TableColumn colType;
    public TableView tblDoc;
    public TableColumn colDocId;
    public TableColumn colDocName;
    public TableColumn colDocTel;
    private EmployeeDAOImpl employeeDAOImpl = new EmployeeDAOImpl();

    public  void initialize() {
        tblDoc.getItems().clear();
        tblEmployee.getItems().clear();
        setCellValueFactoryEmployee();
        loadAllEmployee();

        setCellValueFactoryDoctor();
        loadAllDoctors();
    }

    private void setCellValueFactoryDoctor() {
        colDocId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDocName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDocTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
    }

    public void loadAllDoctors() {
        System.out.println("Loading all doctors");
        ObservableList<DoctorTm> obList = FXCollections.observableArrayList();

        try {
            List<DoctorDto> doctorDtos = DoctorDAOImpl.getAllDoctor();

            for (DoctorDto dto : doctorDtos) {

                obList.add(
                        new DoctorTm(
                                dto.getDocId(),
                                dto.getName(),
                                dto.getTel()
                        )
                );
            }
            tblDoc.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryEmployee() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }

    public void loadAllEmployee() {
        System.out.println("Loading all employees");
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> employeeDtos = employeeDAOImpl.getAllEmployee();

            for (EmployeeDto dto : employeeDtos) {

                obList.add(
                        new EmployeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getTel(),
                                dto.getType()
                        )
                );
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmpOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addEmployee_form.fxml"));
        Parent root = fxmlLoader.load();

        AddEmployeeFormController emp =  fxmlLoader.getController();
        emp.setEmpFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void infoEmpOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/info/infoEmployee_form.fxml"));
        Parent root = fxmlLoader.load();

        InfoEmployeeFormController emp =  fxmlLoader.getController();
        emp.setEmpFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void addDocOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addDoc_form.fxml"));
        Parent root = fxmlLoader.load();

        AddDocFormcontroller emp =  fxmlLoader.getController();
        emp.setEmpFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void infoDocOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/info/infoDoctor_form.fxml"));
        Parent root = fxmlLoader.load();

        InfoDoctorFormController emp =  fxmlLoader.getController();
        emp.setEmpFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
}
