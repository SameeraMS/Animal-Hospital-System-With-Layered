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
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.PetBO;
import lk.ijse.ahms.controller.add.AddPetOwnerFormController;
import lk.ijse.ahms.controller.add.AddPetsFormController;
import lk.ijse.ahms.controller.info.InfoPetOwnerFormController;
import lk.ijse.ahms.controller.info.InfoPetsFormController;
import lk.ijse.ahms.dto.PetsDto;
import lk.ijse.ahms.dto.tm.PetsTm;
import lk.ijse.ahms.dao.custom.impl.PetDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PetsFormController {
    public TableColumn colName;
    public TableView tblPets;
    public TableColumn colId;
    public TableColumn colAge;
    public TableColumn colGender;
    public TableColumn colType;

    PetBO petBO = (PetBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PET);

    public void initialize() {
        tblPets.getItems().clear();
        setCellValueFactoryPets();
        loadAllPets();
    }

    private void setCellValueFactoryPets() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));

    }

    private void loadAllPets() {
        System.out.println("Loading all Pets");
        ObservableList<PetsTm> obList = FXCollections.observableArrayList();

        try {
            List<PetsDto> petsDtos = petBO.getAllPet();

            for (PetsDto dto : petsDtos) {

                obList.add(
                        new PetsTm(
                                dto.getPetId(),
                                dto.getName(),
                                dto.getAge(),
                                dto.getGender(),
                                dto.getType()
                        )
                );
            }
            tblPets.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPetOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPets_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetsFormController ownr =  fxmlLoader.getController();
        ownr.setPetsFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void infoPetOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/info/infoPets_form.fxml"));
        Parent root = fxmlLoader.load();

        InfoPetsFormController ownr =  fxmlLoader.getController();
        ownr.setPetsFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void addPetOwnerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setPetFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void infoPetOwnerOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/info/infoPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        InfoPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setPetsFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
