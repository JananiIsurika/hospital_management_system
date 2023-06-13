package lk.ijse.hospital.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.hospital.dto.PatientDTO;
import lk.ijse.hospital.dto.tm.PatientTM;
import lk.ijse.hospital.model.EmployeeModel;
import lk.ijse.hospital.model.LoginModel;
import lk.ijse.hospital.model.PatientModel;
import lk.ijse.hospital.util.Regex;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientFormController implements Initializable {

    @FXML
    private TextField txtPId;

    @FXML
    private TextField txtFname;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtDOB;

    @FXML
    private TextField txtContact;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableView<PatientTM> table1;

    @FXML
    private TableColumn<PatientDTO, String> colId;

    @FXML
    private TableColumn<PatientDTO, String> colName;

    @FXML
    private TableColumn<PatientDTO, String> colContact;

    @FXML
    private TableColumn<PatientDTO, String> colDob;


    @FXML
    private TableColumn<PatientDTO, Integer> colAge;

    @FXML
    private TableColumn<PatientDTO, String> colGender;

    @FXML
    private TableColumn<PatientDTO, String> colEmail;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtSearch;

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getDataForTable();
        getAll();
        setPatientId();
    }

    public  void setPatientId() {
        String id = null;
        try {
            id = PatientModel.generateNewId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtPId.setText(id);
    }

    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    void getAll() {
        try {
            ObservableList<PatientTM> obList = FXCollections.observableArrayList();
            List<PatientDTO> EmpList = PatientModel.getAll();

            for (PatientDTO patient : EmpList) {
                obList.add(
                        new PatientTM(
                        patient.getPatient_id(),
                        patient.getName(),
                        patient.getContact(),
                        patient.getAge(),
                        patient.getGender(),
                        patient.getDob(),
                        patient.getEmail()
                ));

            }
            table1.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void getDataForTable() {
        ArrayList<PatientDTO> list = new ArrayList<>();
        try {
            List<PatientDTO> patientDTOS = PatientModel.searchAll();
            for (PatientDTO patient : patientDTOS) {
                list.add(new PatientDTO(patient.getPatient_id(), patient.getName(), patient.getContact()
                        , patient.getAge(),patient.getGender(),patient.getDob(),patient.getEmail()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private PatientDTO collectData() {
        String p_id = txtPId.getText();
        String p_name = txtFname.getText();
        String p_contact = txtContact.getText();
        int p_age = Integer.parseInt(txtAge.getText());
        String p_gender = txtGender.getText();
        String p_dob = txtDOB.getText();
        String p_email = txtEmail.getText();

        PatientDTO patientTO = new PatientDTO(p_id, p_name, p_contact, p_age, p_gender, p_dob, p_email);
        return patientTO;
    }



    @FXML
    void CheckBoxFemaleOnAction(ActionEvent event) {

    }

    @FXML
    void CheckBoxMaleOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String patient_id = txtSearch.getText();
        try {
            PatientDTO patient = PatientModel.searchPatient(patient_id);
            if (patient != null) {
                txtPId.setText(patient.getPatient_id());
                txtFname.setText(patient.getName());
                txtContact.setText(patient.getContact());
                txtAge.setText(String.valueOf(patient.getAge()));
                txtGender.setText(String.valueOf(patient.getGender()));
                txtDOB.setText(patient.getDob());
                txtEmail.setText(patient.getEmail());
            } else {
                new Alert(Alert.AlertType.WARNING, "no Patient found :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }



    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        PatientDTO PatientTo = collectData();
        String patientPhoneNumber =txtContact.getText();
        String patientEmail =txtEmail.getText();
        if(Regex.validateMobile(patientPhoneNumber) && Regex.validateEmail(patientEmail)) {
            try {
                boolean addNewPatient = PatientModel.addNewPatient(PatientTo);
                if (addNewPatient) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient Added Sucessfully", ButtonType.OK).show();
                    setPatientId();
                    getDataForTable();
                    getAll();
                    clear();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Error", ButtonType.CANCEL).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Input", ButtonType.CANCEL).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean deletePatient = PatientModel.deletePatient(txtPId.getText());
            if (deletePatient) {
                new Alert(Alert.AlertType.CONFIRMATION, "Patient Delete Sucessfully", ButtonType.OK).show();
                getAll();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.CANCEL).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String patientPhoneNumber = txtContact.getText();
        String patientEmail = txtEmail.getText();
        if (Regex.validateMobile(patientPhoneNumber) && Regex.validateEmail(patientEmail)) {
            try {
                boolean updatePatient = PatientModel.updatePatient(collectData());
                if (updatePatient) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient Update Sucessfully", ButtonType.OK).show();
                    getAll();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "something error", ButtonType.CANCEL).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Input", ButtonType.CANCEL).show();
        }
    }
    public void clear(){
        txtPId.setText(null);
        txtFname.setText(null);
        txtContact.setText(null);
        txtAge.setText(null);
        txtGender.setText(null);
        txtDOB.setText(null);
        txtEmail.setText(null);
        txtSearch.setText(null);
    }


    @FXML
    void txtAgeOnAction(ActionEvent event) {

    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtDOBOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtFnameOnAction(ActionEvent event) {

    }

    @FXML
    void txtPIdOnAction(ActionEvent event) {

    }

    public void txtGenderOnAction(ActionEvent actionEvent) {
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }
}
