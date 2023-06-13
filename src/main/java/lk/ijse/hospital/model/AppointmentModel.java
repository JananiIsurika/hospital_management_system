package lk.ijse.hospital.model;

import lk.ijse.hospital.db.DBConnection;
import lk.ijse.hospital.dto.AppointmentDTO;
import lk.ijse.hospital.dto.DoctorDTO;
import lk.ijse.hospital.dto.EmployeeDTO;
import lk.ijse.hospital.dto.PaymentDTO;
import lk.ijse.hospital.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentModel {
    public static boolean addNewAppointment(AppointmentDTO ob) throws SQLException {
        System.out.println(ob);
        String sql = "INSERT INTO appointments (appointment_id,doctor_id,patient_id,date,service) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, ob.getAppointment_id(), ob.getDoctor_id(), ob.getPatient_id(), ob.getDate(),ob.getService());
    }

    public static boolean addAppointment(AppointmentDTO ob) throws SQLException {
        DBConnection.getInstance().getConnection().setAutoCommit(false);
        try {
            boolean addNewAppointment = addNewAppointment(ob);
            if(addNewAppointment){ 
                boolean isAdded = PaymentModel.addNewPayment(new PaymentDTO(PaymentModel.generateNewId(), 0, ob.getAppointment_id()));
                if(isAdded){
                    DBConnection.getInstance().getConnection().commit();
                    return true;
                }
            }
            DBConnection.getInstance().getConnection().rollback();
            return false;
        }catch (SQLException e){
            DBConnection.getInstance().getConnection().rollback();
            throw e;
        }
    }


    public static List<AppointmentDTO> searchAll() throws SQLException {
        String sql = "SELECT * FROM appointment";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<AppointmentDTO> dataList = new ArrayList<>();
        while (resultSet.next()){
            String appointment_id = resultSet.getString(1);
            String doctor_id = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String date = resultSet.getString(4);
            String service = resultSet.getString(5);

            var appointment_To = new AppointmentDTO(appointment_id,doctor_id,patient_id,date,service);
            dataList.add(appointment_To);
        }
        return dataList;
    }

    public static List<AppointmentDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM appointments";

        List<AppointmentDTO> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new AppointmentDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static AppointmentDTO searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new AppointmentDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }


    public static List<String> getCodes() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT appointment_id FROM appointments ";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static String getLastId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT appointment_id FROM APPOINTMENTS ORDER BY appointment_id DESC LIMIT 1");
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }

    public static String generateNewId() throws SQLException {
        String lastId = getLastId();
        if(lastId==null){
            return "A-001";
        }
        String[] split = lastId.split("[A][-]");
        String s = split[1];
        int num = Integer.parseInt(s);
        num++;
        String newId = String.format("A-%03d", num);
        return newId;
    }
}

