package lk.ijse.hospital.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hospital.model.LoginModel;
import lk.ijse.hospital.util.Regex;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFrom {
    public TextField txtUserName;
    public TextField txtPassword;
    @FXML
    private AnchorPane login;
    

    public void LogOnAction(ActionEvent actionEvent) throws IOException {
        String username = txtUserName.getText();
        String password = txtPassword.getText();
       // if (Regex.validateUsername(username)&& Regex.validatePassword(password)) {
            try {
                boolean isUserVerified = LoginModel.userVerified(username, password);
                if (isUserVerified) {
                    Stage stage=(Stage)login.getScene().getWindow();
                    Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/view/dashBoardForm.fxml")));
                    stage.setScene(scene);
                    stage.centerOnScreen();
                } else {
                    new Alert(Alert.AlertType.WARNING, "User Not Verified!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Oops something wrong!!!").show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        }else {
//            new Alert(Alert.AlertType.INFORMATION, "Hint : [aA-zZ0-9@]{8,20}").show();
//        }
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
    }
}
