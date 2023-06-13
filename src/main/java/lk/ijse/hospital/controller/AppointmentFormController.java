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
import lk.ijse.hospital.dto.DoctorDTO;
import lk.ijse.hospital.dto.EmployeeDTO;
import lk.ijse.hospital.dto.PatientDTO;
import lk.ijse.hospital.dto.tm.AppointmentTM;
import lk.ijse.hospital.dto.tm.DoctorTM;
import lk.ijse.hospital.model.AppointmentModel;
import lk.ijse.hospital.model.DoctorModel;
import lk.ijse.hospital.model.EmployeeModel;
import lk.ijse.hospital.model.PatientModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentFormController implements Initializable {

    public TableColumn<AppointmentTM,String> colDate;
    public DatePicker datePicker;

    @FXML
    private TextField txtApId;

    @FXML
    private TextField txtDate;

    @FXML
    private TableView<AppointmentTM> table4;

    @FXML
    private TableColumn<AppointmentTM, String> colAid;

    @FXML
    private TableColumn<AppointmentTM, String> colPid;

    @FXML
    private TableColumn<AppointmentTM, String> colDid;

    @FXML
    private TableColumn<AppointmentTM, String> colService;

    @FXML
    private JFXComboBox<String> cmbDS;

    @FXML
    private JFXComboBox<String> cmbPId;

    @FXML
    private ComboBox<String> comService;

    private ObservableList<AppointmentTM> obList = FXCollections.observableArrayList();

    public void initialize (URL url, ResourceBundle resourceBundle){
        loadDoctor();
        loadPatient();
        setCellValueFactories();
        setTable();
        ObservableList<String> strings = FXCollections.observableArrayList();
        strings.add("eye");
        strings.add("skin");
        strings.add("dental");
        strings.add("optical");
        strings.add("donation");
        comService.setItems(strings);
        setAppointmentId();
    }

    public void setAppointmentId()  {
        String id = null;
        try {
            id = AppointmentModel.generateNewId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtApId.setText(id);
    }

    public void setCellValueFactories(){
        colAid.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        colPid.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        colDid.setCellValueFactory(new PropertyValueFactory<>("doctor_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colService.setCellValueFactory(new PropertyValueFactory<>("service"));
    }

    public void setTable(){
        try {
            List<AppointmentDTO> all = AppointmentModel.getAll();
            ObservableList<AppointmentTM> objects = FXCollections.observableArrayList();
            System.out.println(all.size());
            System.out.println(objects.size());
            for(AppointmentDTO ob : all){
                objects.add(new AppointmentTM(ob.getAppointment_id(), ob.getDoctor_id(), ob.getPatient_id(), ob.getDate(),"",ob.getService()));
            }

            table4.setItems(objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSubmitOnAction(ActionEvent event) {
        AppointmentDTO appointmentDTO = collectData();
        try {
            boolean addNewAppointment = AppointmentModel.addAppointment(appointmentDTO);
            if (addNewAppointment) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appointment Added Sucessfully", ButtonType.OK).show();
                setTable();
                setAppointmentId();

            } else {
                new Alert(Alert.AlertType.ERROR, "Something Error", ButtonType.CANCEL).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private AppointmentDTO collectData() {
        String a_id= txtApId.getText();
        String d_id = (String) cmbDS.getValue();
        String p_id = (String) cmbPId.getValue();
        String date = datePicker.getValue().toString();
        String service = (String) comService.getValue();

        AppointmentDTO appointmentDTO = new AppointmentDTO(a_id,d_id,p_id,date,service);
        return appointmentDTO ;
    }

    @FXML
    void cmbDSOnAction(ActionEvent event) {
        String code = (String) cmbDS.getSelectionModel().getSelectedItem();

        try {
            AppointmentDTO appointmentDTO = AppointmentModel.searchById(code);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
        private void loadDoctor() {
            try {
                ObservableList<String> obList = FXCollections.observableArrayList();
                List<String> codes = DoctorModel.getCodes();

                for (String code : codes) {
                    obList.add(code);
                }
                cmbDS.setItems(obList);
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }


    @FXML
    void cmbPIdOnAction(ActionEvent event) {
        String code = (String) cmbPId.getSelectionModel().getSelectedItem();

        try {
            PatientDTO patientDTO = PatientModel.searchById(code);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }
    private void loadPatient() {
        try {
            List<PatientDTO> all = PatientModel.getAll();
            ObservableList<String> obList = FXCollections.observableArrayList();


            for (PatientDTO ob : all) {
                obList.add(ob.getPatient_id());
            }
            cmbPId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void comServiceOnAction(ActionEvent event) {


    }

    @FXML
    void txtApIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtDateOnAction(ActionEvent event) {

    }


}
