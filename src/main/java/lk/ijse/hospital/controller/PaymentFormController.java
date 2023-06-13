package lk.ijse.hospital.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hospital.dto.AppointmentDTO;
import lk.ijse.hospital.dto.PaymentDTO;
import lk.ijse.hospital.dto.tm.AppointmentTM;
import lk.ijse.hospital.dto.tm.PaymentTM;
import lk.ijse.hospital.model.AppointmentModel;
import lk.ijse.hospital.model.DoctorModel;
import lk.ijse.hospital.model.PatientModel;
import lk.ijse.hospital.model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    @FXML
    private TextField txtpayment;

    @FXML
    private JFXButton Payment;

    @FXML
    private JFXComboBox<String> cmbApp;

    @FXML
    private TextField txtPrice;

    @FXML
    private TableView<PaymentTM> table6;

    @FXML
    private TableColumn<PaymentTM, String> colAID;

    @FXML
    private TableColumn<PaymentTM, String> colPID;

    @FXML
    private TableColumn<PaymentTM, Double> colPrice;

    public void initialize (URL url, ResourceBundle resourceBundle){
        loadAppointment();
        setCellValueFactories();
        setTable();
        txtpayment.setEditable(false);
    }

    public void setCellValueFactories(){
        colPID.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAID.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
    }

    public void setTable(){
        try {
            List<PaymentDTO> all = PaymentModel.getAll();
            ObservableList<PaymentTM> objects = FXCollections.observableArrayList();
            System.out.println(all.size());
            System.out.println(objects.size());
            for(PaymentDTO ob : all){
                objects.add(new PaymentTM(ob.getPayment_id(),ob.getPrice(),ob.getAppointment_id()));
            }

            table6.setItems(objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void PaymentOnAction(ActionEvent event) {
        PaymentDTO paymentDTO = collectData();
        try {
            boolean isUpdated = PaymentModel.updatePayment(paymentDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Added Sucessfully", ButtonType.OK).show();
                setTable();
                txtpayment.clear();
                txtPrice.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something Error", ButtonType.CANCEL).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private PaymentDTO collectData() {
        String p_id= txtpayment.getText();
        double price = Double.parseDouble(txtPrice.getText());
        String a_id = (String) cmbApp.getValue();

        PaymentDTO paymentDTO = new PaymentDTO(p_id,price,a_id);
        return paymentDTO ;
    }


    @FXML
    void cmbAppOnAction(ActionEvent event) {
        String code = (String) cmbApp.getSelectionModel().getSelectedItem();
        txtpayment.setText(code);
    }
    private void loadAppointment() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = PaymentModel.getUnpaidPayments();

            for (String code : codes) {
                obList.add(code);
            }
            cmbApp.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    

    @FXML
    void txtPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {

    }

}
