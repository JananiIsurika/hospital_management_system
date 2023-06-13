package lk.ijse.hospital.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hospital.dto.AppointmentDTO;
import lk.ijse.hospital.dto.DoctorDTO;
import lk.ijse.hospital.dto.tm.AppointmentTM;
import lk.ijse.hospital.dto.tm.DoctorTM;
import lk.ijse.hospital.model.AppointmentModel;
import lk.ijse.hospital.model.DoctorModel;
import lk.ijse.hospital.model.EmployeeModel;
import lk.ijse.hospital.util.Regex;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorFormController implements Initializable {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtDOB;

    @FXML
    private TextField txtCon;

    @FXML
    private TextField txtDId;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnDelete;


    @FXML
    private TableView<DoctorTM> table3;

    @FXML
    private TableColumn<DoctorDTO, String> colId;

    @FXML
    private TableColumn<DoctorDTO, String> colName;

    @FXML
    private TableColumn<DoctorDTO, String> coContact;

    @FXML
    private TableColumn<DoctorDTO, String> colDob;

    @FXML
    private TableColumn<DoctorDTO, Integer> colAge;

    @FXML
    private TableColumn<DoctorDTO, String> colGender;

    @FXML
    private TableColumn<DoctorDTO, String> colSection;


    @FXML
    private JFXButton btnSearch;

    @FXML
    private ComboBox<String> comService2;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtSearch;

    @SneakyThrows
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getDataForTable();
        getAll();
        setDoctorId();

    }

    public void setDoctorId() throws SQLException {
        String id = DoctorModel.generateNewId();
        txtDId.setText(id);

    }

    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        coContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));


    }

    void getAll() {
        try {
            ObservableList<DoctorTM> obList = FXCollections.observableArrayList();
            List<DoctorDTO> EmpList = DoctorModel.getAll();

            for (DoctorDTO doctor : EmpList) {
                obList.add(
                        new DoctorTM(
                                doctor.getDoctor_id(),
                                doctor.getName(),
                                doctor.getContact(),
                                doctor.getAge(),
                                doctor.getGender()
                        ));

            }
            table3.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void getDataForTable() {
        ArrayList<DoctorDTO> list = new ArrayList<>();
        try {
            List<DoctorDTO> doctorDTOS = DoctorModel.searchAll();
            for (DoctorDTO doctor : doctorDTOS) {
                list.add(new DoctorDTO(doctor.getDoctor_id(), doctor.getName(), doctor.getContact()
                        , doctor.getAge(),doctor.getGender()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String Doctor_id = txtSearch.getText();
        try {
            DoctorDTO doctor = DoctorModel.searchDoctor(Doctor_id);
            if (doctor != null) {
                txtDId.setText(doctor.getDoctor_id());
                txtName.setText(doctor.getName());
                txtCon.setText(doctor.getContact());
                txtAge.setText(String.valueOf(doctor.getAge()));
                txtGender.setText(doctor.getGender());

            } else {
                new Alert(Alert.AlertType.WARNING, "no Doctor found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }

    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        DoctorDTO doctorTo = collectData();
        String doctorPhoneNumber = txtCon.getText();

        if (Regex.validateMobile(doctorPhoneNumber) ) {
            try {
                boolean addNewDoctor = DoctorModel.addNewDoctor(doctorTo);
                if (addNewDoctor) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Doctor Added Sucessfully", ButtonType.OK).show();
                    setDoctorId();
                    getDataForTable();
                    getAll();
                    clear();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Something Error", ButtonType.CANCEL).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Input", ButtonType.CANCEL).show();

        }

    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            boolean deleteDoctor = DoctorModel.deleteDoctor(txtDId.getText());
            if (deleteDoctor) {
                new Alert(Alert.AlertType.CONFIRMATION, "Doctor Delete Sucessfully", ButtonType.OK).show();

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
        String doctorPhoneNumber = txtCon.getText();

        if (Regex.validateMobile(doctorPhoneNumber) ) {
            try {
                boolean updateDoctor = DoctorModel.updateDoctor(collectData());
                if (updateDoctor) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Doctor Update Sucessfully", ButtonType.OK).show();
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
        txtDId.setText(null);
        txtName.setText(null);
        txtCon.setText(null);
        txtAge.setText(null);
        txtGender.setText(null);
        txtSearch.setText(null);

    }

    private DoctorDTO collectData() {
        String d_id= txtDId.getText();
        String d_name = txtName.getText();
        String d_contact = txtCon.getText();
        int d_age = Integer.parseInt(txtAge.getText());
        String d_gender = txtGender.getText();

        DoctorDTO doctorTo = new DoctorDTO(d_id, d_name, d_contact,d_age, d_gender);
        return doctorTo ;
    }


    @FXML
    void comService2OnAction(ActionEvent event) {

    }

    @FXML
    void txtAgeOnAction(ActionEvent event) {

    }

    @FXML
    void txtConOnAction(ActionEvent event) {

    }

    @FXML
    void txtDIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtDOBOnAction(ActionEvent event) {

    }

    @FXML
    void txtFnameOnAction(ActionEvent event) {

    }

    @FXML
    void txtGenderOnAction(ActionEvent event) {

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }
}
