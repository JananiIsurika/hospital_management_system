package lk.ijse.hospital.model;

import lk.ijse.hospital.db.DBConnection;
import lk.ijse.hospital.dto.AppointmentDTO;
import lk.ijse.hospital.dto.TestDTO;
import lk.ijse.hospital.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestModel {
    public static boolean addNewTest(TestDTO ob) throws SQLException {
        System.out.println(ob);
        String sql = "INSERT INTO test (test_id,test_name,treatments,description,patient_id) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, ob.getTest_id(), ob.getTest_name(), ob.getTreatments(), ob.getDescription(),ob.getPatient_id());
    }
    public static TestDTO searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM test WHERE testId = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new TestDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static List<TestDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM test";

        List<TestDTO> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new TestDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
}
