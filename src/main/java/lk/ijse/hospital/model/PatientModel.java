package lk.ijse.hospital.model;

import lk.ijse.hospital.db.DBConnection;
import lk.ijse.hospital.dto.DoctorDTO;
import lk.ijse.hospital.dto.PatientDTO;
import lk.ijse.hospital.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientModel {

        public static boolean addNewPatient(PatientDTO ob) throws SQLException {
            String sql = "INSERT INTO patient (patient_id,name,contact,age,gender,dob,email) VALUES (?,?,?,?,?,?,?)";
            return CrudUtil.execute(sql, ob.getPatient_id(), ob.getName(), ob.getContact(), ob.getAge(),ob.getGender(),ob.getDob(),ob.getEmail());
        }

        public static boolean updatePatient(PatientDTO ob) throws SQLException {
            String sql = "UPDATE patient SET name=?,contact=?,age=?,gender=?,dob=?,email=? WHERE patient_id=?";
            return CrudUtil.execute(sql, ob.getName(), ob.getContact(), ob.getAge(),ob.getGender(),ob.getDob(),ob.getEmail(),ob.getPatient_id());
        }


        public static boolean deletePatient(String id) throws SQLException {
            String sql = "DELETE FROM patient WHERE patient_id=?";
            return CrudUtil.execute(sql,id);
        }

        public static PatientDTO searchPatient(String id) throws SQLException {
            String sql = "select * FROM patient WHERE patient_id=?";
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()){
                String patient_id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String contact = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String gender = resultSet.getString(5);
                String dob = resultSet.getString(6);
                String email = resultSet.getString(7);

                return new PatientDTO(patient_id,name,contact,age,gender,dob,email);
            }
            return null;
        }

        public static List<PatientDTO> searchAll() throws SQLException {
            String sql = "SELECT * FROM patient WHERE patient_id";
            ResultSet resultSet = CrudUtil.execute(sql);
            List<PatientDTO> dataList = new ArrayList<>();
            while (resultSet.next()){
                String patient_id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String contact = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String gender = resultSet.getString(5);
                String dob = resultSet.getString(6);
                String email = resultSet.getString(7);


                var patientTo = new PatientDTO(patient_id,name,contact,age,gender,dob,email);
                dataList.add(patientTo);
            }
            return dataList;
        }
    public static List<PatientDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM patient";

        List<PatientDTO> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new PatientDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return data;
    }

    public static PatientDTO searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM patient WHERE patient_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new PatientDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }

    public static String getLastId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT patient_id FROM PATIENT ORDER BY patient_id DESC LIMIT 1");
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }

    public static String generateNewId() throws SQLException {
        String lastId = getLastId();
        if(lastId==null){
            return "P-001";
        }
        String[] split = lastId.split("[P][-]");
        String s = split[1];
        int num = Integer.parseInt(s);
        num++;
        String newId = String.format("P-%03d", num);
        return newId;
    }
}
