package lk.ijse.ahms.controller.forgotPassword;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.UserBO;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.util.JavaMailUtil;
import lk.ijse.ahms.util.SecurityUtil;
import lombok.SneakyThrows;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Forgotpass1FormController implements Initializable {
    public AnchorPane AnchorPaneFogotPassword;
    public ProgressBar Prograss;
    public ImageView btnClose;
    public AnchorPane root;
    @FXML
    private ComboBox<String> comUserName;
    @FXML
    private Button btnSendOTP;
    public static int OTP;
    public static String userName;
    @FXML
    private Label lblEmail;
    private Button btn;
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserNames();
    }

    @FXML
    void btnSendOTPOnAction(ActionEvent event) throws MessagingException, IOException {
        Object selectedItem1 = comUserName.getSelectionModel().getSelectedItem();
        String Username = (String) selectedItem1;
        userName=SecurityUtil.encoder(Username);
        if (Username!=null){
            System.out.println("OK");
            int otp = new Random().nextInt(9000) + 1000;
            comUserName.setStyle("-fx-background-color: null");
            try {
                UserDto user = userBO.searchUser(SecurityUtil.encoder(Username));
                fillItemFields(user);
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            JavaMailUtil.sendMail(lblEmail.getText(),otp);
            OTP=otp;
            System.out.println(">>>"+otp);

            Parent root = FXMLLoader.load(getClass().getResource("/view/forgotPassword/ForgotPassword2.fxml"));
            Scene scene = btnSendOTP.getScene();
            root.translateXProperty().set(scene.getWidth());

            AnchorPane parentContainer = (AnchorPane) scene.getRoot();
            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(AnchorPaneFogotPassword);
            });
            timeline.play();

        }else {
            comUserName.setStyle("-fx-background-color: Red");
        }
    }

    private void fillItemFields(UserDto user) {
        lblEmail.setText(SecurityUtil.decoder(user.getUsername()));
    }

    public void loadUserNames() throws SQLException {
        try{
            List<String> name = userBO.getUserName();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String un : name){
                obList.add(SecurityUtil.decoder(un));
            }
            comUserName.setItems(obList);
        }catch (SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCloseOnAction(MouseEvent mouseEvent) {
        root.getScene().getWindow().hide();
    }

}
