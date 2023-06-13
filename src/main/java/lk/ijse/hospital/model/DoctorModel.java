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

public class DoctorModel {
    public static boolean addNewDoctor(DoctorDTO ob) throws SQLException {
        String sql = "INSERT INTO doctor (doctor_id,name,contact,age,gender) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, ob.getDoctor_id(), ob.getName(), ob.getContact(), ob.getAge(),ob.getGender());
    }

    public static boolean updateDoctor(DoctorDTO ob) throws SQLException {
        String sql = "UPDATE doctor SET name=?,contact=?,age=?,gender=? WHERE doctor_id=?";
        return CrudUtil.execute(sql, ob.getName(), ob.getContact(), ob.getAge(),ob.getGender(),ob.getDoctor_id());
    }


    public static boolean deleteDoctor(String id) throws SQLException {
        String sql = "DELETE FROM doctor WHERE doctor_id=?";
        return CrudUtil.execute(sql,id);
    }

    public static DoctorDTO searchDoctor(String id) throws SQLException {
        String sql = "SELECT * FROM Doctor WHERE doctor_id=?";
        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()){
            String doctor_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            int age = resultSet.getInt(4);
            String gender = resultSet.getString(5);

            return new DoctorDTO(doctor_id,name,contact,age,gender);
        }
        return null;
    }

    public static List<DoctorDTO> searchAll() throws SQLException {
        String sql = "SELECT * FROM Doctor";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<DoctorDTO> dataList = new ArrayList<>();
        while (resultSet.next()){
            String doctor_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            int age = resultSet.getInt(4);
            String gender = resultSet.getString(5);


            var doctorTO = new DoctorDTO(doctor_id,name,contact,age,gender);
            dataList.add(doctorTO);
        }
        return dataList;
    }

    public static List<DoctorDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM doctor";

        List<DoctorDTO> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new DoctorDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static DoctorDTO searchById(String code) throws SQLException {
            Connection con = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM doctor WHERE doctor_id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                return new DoctorDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)
                );
            }
            return null;
        }

    public static List<String> getCodes() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT doctor_id FROM doctor ";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static String generateNewId() throws SQLException {
        String lastId = getLastId();
        if(lastId==null){
            return "D-001";
        }
        String[] split = lastId.split("[D][-]");
        String s = split[1];
        int num = Integer.parseInt(s);
        num++;
        String newId = String.format("D-%03d", num);
        return newId;
    }

    private static String getLastId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT doctor_id FROM DOCTOR ORDER BY doctor_id DESC LIMIT 1");
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }


}

