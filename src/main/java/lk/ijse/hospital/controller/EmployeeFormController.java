package lk.ijse.hospital.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hospital.db.DBConnection;
import lk.ijse.hospital.dto.EmployeeDTO;
import lk.ijse.hospital.dto.tm.EmployeeTM;
import lk.ijse.hospital.model.EmployeeModel;
import lk.ijse.hospital.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private TextField txtEId;

    @FXML
    private TextField txtFname;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtJobR;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableView<EmployeeTM> table2;

    @FXML
    private TableColumn<EmployeeDTO, String> colId;

    @FXML
    private TableColumn<EmployeeDTO, String> colName;

    @FXML
    private TableColumn<EmployeeDTO, String> colContact;

    @FXML
    private TableColumn<EmployeeDTO, String> colJR;

    @FXML
    private TableColumn<EmployeeDTO, String> colGender;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtSearch;

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getDataForTable();
        getAll();
        setEmployeeId();
    }

    public void setEmployeeId(){
        try {
            String id = EmployeeModel.generateNewId();
            txtEId.setText(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colJR.setCellValueFactory(new PropertyValueFactory<>("job_role"));

    }

    void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<EmployeeDTO> EmpList = EmployeeModel.getAll();

            for (EmployeeDTO emplyee : EmpList) {
                obList.add(
                        new EmployeeTM(
                                emplyee.getEmployee_id(),
                                emplyee.getName(),
                                emplyee.getContact(),
                                emplyee.getGender(),
                                emplyee.getJob_role()
                        ));

            }
            table2.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void getDataForTable() {
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        try {
            List<EmployeeDTO> emloyeeDTOS = EmployeeModel.searchAll();
            for (EmployeeDTO employee : emloyeeDTOS) {
                list.add(new EmployeeDTO(employee.getEmployee_id(),employee.getName(),employee.getContact()
                        , employee.getGender(),employee.getJob_role()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String Employee_id = txtSearch.getText();
        try {
            EmployeeDTO employee = EmployeeModel.searchEmployee(Employee_id);
            if (employee != null) {
                txtEId.setText(employee.getEmployee_id());
                txtFname.setText(employee.getName());
                txtContact.setText(employee.getContact());
                txtGender.setText(employee.getGender());
                txtJobR.setText(employee.getJob_role());

            } else {
                new Alert(Alert.AlertType.WARNING, "no Employee found :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        EmployeeDTO employeeTo = collectData();
        String employeePhoneNumber=txtContact.getText();

        if (Regex.validateMobile(employeePhoneNumber) ) {


            try {
                boolean addNewEmployee = EmployeeModel.addNewEmployee(employeeTo);
                if (addNewEmployee) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Added Sucessfully", ButtonType.OK).show();
                    setEmployeeId();
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
            boolean deleteEmployee = EmployeeModel.deleteEmployee(txtEId.getText());
            if (deleteEmployee) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Delete Sucessfully", ButtonType.OK).show();
                getAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.CANCEL).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            boolean updateEmployee = EmployeeModel.updateEmployee(collectData());
            if (updateEmployee) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Update Sucessfully", ButtonType.OK).show();
                getAll();
                clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.CANCEL).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void clear(){
        txtEId.setText(null);
        txtFname.setText(null);
        txtContact.setText(null);
        txtGender.setText(null);
        txtJobR.setText(null);
        txtSearch.setText(null);
    }
    private EmployeeDTO collectData() {
        String e_id= txtEId.getText();
        String e_name = txtFname.getText();
        String e_contact = txtContact.getText();
        String e_gender = txtGender.getText();
        String e_jobR = txtJobR.getText();

        EmployeeDTO employeeTo = new EmployeeDTO(e_id, e_name, e_contact, e_gender, e_jobR);
        return employeeTo ;
    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtEIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtFnameOnAction(ActionEvent event) {

    }

    @FXML
    void txtGenderOnAction(ActionEvent event) {

    }

    @FXML
    void txtJobROnAction(ActionEvent event) {

    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }

    public void btnPrintAllEmployeeOnAction(ActionEvent actionEvent) throws JRException, SQLException {

        JasperDesign jasdi = JRXmlLoader.load("D:\\GDSE 65-JDBC\\jdbc-65\\jdbc-65\\demo\\hospital1\\src\\main\\resources\\report\\Hospital.jrxml");

        JasperReport js = JasperCompileManager.compileReport(jasdi);

        JasperPrint jp = JasperFillManager.fillReport(js, null, DBConnection.getInstance().getConnection());
        // JasperExportManager.exportReportToHtmlFile(jp ,ore);
        //= "G:\\IncomeReports\\" + LocalDate.now().getYear() + LocalDate.now().getMonth().toString() + LocalDate.now().getDayOfMonth() + LocalTime.now().getHour() + LocalTime.now().getMinute() + LocalTime.now().getSecond() + ".pdf";
        //JasperExportManager.exportReportToPdfFile(jp, savePath);
        JasperViewer viewer = new JasperViewer(jp, false);
        viewer.show();
    }
}
