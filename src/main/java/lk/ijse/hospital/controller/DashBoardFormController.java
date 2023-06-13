package lk.ijse.hospital.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {
    public AnchorPane HOME;

    public void LogoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/login_from.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);

    }

    public void DashboardOnAction(ActionEvent actionEvent) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/homePageForm.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);

    }

    public void AppointmentOnAction(ActionEvent actionEvent) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/appointmentForm.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);

    }

    public void ServicesOnAction(ActionEvent actionEvent) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/serviceForm.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);

    }

    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/employee_Form.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);
    }

    public void DoctorOnAction(ActionEvent actionEvent) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/doctor_Form.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);
    }

    public void PatientOnAction(ActionEvent actionEvent) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/patient_Form.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);

    }
    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        Parent node=FXMLLoader.load(getClass().getResource("/view/payment_Form.fxml"));
        HOME.getChildren().clear();
        HOME.getChildren().addAll(node);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Node node=null;

        try {
            node = FXMLLoader.load(getClass().getResource("/view/homePageForm.fxml"));
            HOME.getChildren().clear();
            HOME.getChildren().addAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnTestOnAction(ActionEvent actionEvent) {
        Node node=null;

        try {
            node = FXMLLoader.load(getClass().getResource("/view/test_Form.fxml"));
            HOME.getChildren().clear();
            HOME.getChildren().addAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   

}