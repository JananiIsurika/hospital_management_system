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
import javafx.scene.layout.AnchorPane;
import lk.ijse.hospital.dto.AppointmentDTO;
import lk.ijse.hospital.dto.PatientDTO;
import lk.ijse.hospital.dto.TestDTO;
import lk.ijse.hospital.dto.tm.AppointmentTM;
import lk.ijse.hospital.dto.tm.TestTM;
import lk.ijse.hospital.model.AppointmentModel;
import lk.ijse.hospital.model.PatientModel;
import lk.ijse.hospital.model.TestModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TestFormController implements Initializable {

    @FXML
    private AnchorPane tstForm;

    @FXML
    private TextField txtTid;

    @FXML
    private TextField txtTname;

    @FXML
    private TextField txtTreat;

    @FXML
    private TextArea txtDis;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<String> cmbPID;

    @FXML
    private TableView<TestTM> table7;

    @FXML
    private TableColumn<TestTM, String> colPID;

    @FXML
    private TableColumn<TestTM, String> colTID;

    @FXML
    private TableColumn<TestTM, String> colTname;

    @FXML
    private TableColumn<TestTM, String> colTreat;

    @FXML
    private TableColumn<TestTM, String> colDis;

    public void initialize (URL url, ResourceBundle resourceBundle){
        loadPatient();
        setCellValueFactories();
        setTable();
    }

    public void setCellValueFactories(){
        colTID.setCellValueFactory(new PropertyValueFactory<>("test_id"));
        colTname.setCellValueFactory(new PropertyValueFactory<>("test_name"));
        colTreat.setCellValueFactory(new PropertyValueFactory<>("treatments"));
        colDis.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPID.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
    }

    public void setTable(){
        try {
            List<TestDTO> all = TestModel.getAll();
            ObservableList<TestTM> objects = FXCollections.observableArrayList();
            System.out.println(all.size());
            System.out.println(objects.size());
            for(TestDTO ob : all){
                objects.add(new TestTM(ob.getTest_id(), ob.getTest_name(), ob.getTreatments(), ob.getDescription(),ob.getPatient_id()));
            }

            table7.setItems(objects);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbPID(ActionEvent event) {
        String code = (String) cmbPID.getSelectionModel().getSelectedItem();

        try {
            TestDTO testDTO = TestModel.searchById(code);
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
            cmbPID.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        TestDTO testDTO = collectData();
        try {
            boolean addNewTest = TestModel.addNewTest(testDTO);
            if (addNewTest) {
                new Alert(Alert.AlertType.CONFIRMATION, "Test Added Sucessfully", ButtonType.OK).show();
                setTable();


            } else {
                new Alert(Alert.AlertType.ERROR, "Something Error", ButtonType.CANCEL).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private TestDTO collectData() {
        String t_id= txtTid.getText();
        String t_name = txtTname.getText();
        String treat = txtTreat.getText();
        String dis = txtDis.getText();
        String p_id = (String) cmbPID.getValue();

        TestDTO testDTO = new TestDTO(t_id,t_name,treat,dis,p_id);
        return testDTO ;
    }

}
