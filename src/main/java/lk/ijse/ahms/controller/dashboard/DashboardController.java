package lk.ijse.ahms.controller.dashboard;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.DoctorBO;
import lk.ijse.ahms.bo.custom.EmployeeBO;
import lk.ijse.ahms.bo.custom.PetBO;
import lk.ijse.ahms.bo.custom.PetOwnerBO;
import lk.ijse.ahms.dao.custom.impl.DoctorDAOImpl;
import lk.ijse.ahms.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.ahms.dao.custom.impl.PetDAOImpl;
import lk.ijse.ahms.dao.custom.impl.PetOwnerDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DashboardController {

    public Label lblpet;
    public Label lblpetowner;
    public Label lbldoc;
    public Label lblemp;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane calanderpain;
    public PieChart piechart;
    public LineChart linechart;

    PetBO petBO = (PetBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET);
    PetOwnerBO petOwnerBO = (PetOwnerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET_OWNER);
    DoctorBO doctorBO = (DoctorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DOCTOR);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);


    public void initialize() throws SQLException, IOException {
        setValues();
        lblDate.setText(String.valueOf(LocalDate.now()));
        settime();
        setchart();

        URL resource = getClass().getResource("/view/Calender.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        calanderpain.getChildren().clear();
        calanderpain.getChildren().add(load);


    }

    private void setchart() throws SQLException {

        //pie chart

        PieChart.Data slice1 = new PieChart.Data("Pets", petBO.getAllPets().size());
        PieChart.Data slice2 = new PieChart.Data("Pet Owners", petOwnerBO.getAllOwners().size());
        PieChart.Data slice3 = new PieChart.Data("Doctors", doctorBO.getAllDoctor().size());
        PieChart.Data slice4 = new PieChart.Data("Employees", employeeBO.getAllEmployee().size());

        piechart.getData().addAll(slice1, slice2, slice3, slice4);



        //line chart

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Details");

        series.getData().add(new XYChart.Data<>("Pets", petBO.getAllPets().size()));
        series.getData().add(new XYChart.Data<>("Pet Owners", petOwnerBO.getAllOwners().size()));
        series.getData().add(new XYChart.Data<>("Doctors", doctorBO.getAllDoctor().size()));
        series.getData().add(new XYChart.Data<>("Employees", employeeBO.getAllEmployee().size()));

        linechart.getData().add(series);
        
    }

    private void settime() {
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


    private void setValues() throws SQLException {
        lblpet.setText(String.valueOf(petBO.getAllPets().size()));
        lblpetowner.setText(String.valueOf(petOwnerBO.getAllOwners().size()));
        lbldoc.setText(String.valueOf(doctorBO.getAllDoctor().size()));
        lblemp.setText(String.valueOf(employeeBO.getAllEmployee().size()));


    }
}
