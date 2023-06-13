package lk.ijse.hospital.model;

import lk.ijse.hospital.db.DBConnection;
import lk.ijse.hospital.dto.EmployeeDTO;
import lk.ijse.hospital.dto.PatientDTO;
import lk.ijse.hospital.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static boolean addNewEmployee(EmployeeDTO ob) throws SQLException {
        String sql = "INSERT INTO employee (employee_id,name,contact,gender,job_role) VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql, ob.getEmployee_id(), ob.getName(), ob.getContact(), ob.getGender(),ob.getJob_role());
    }

    public static boolean updateEmployee(EmployeeDTO ob) throws SQLException {
        String sql = "UPDATE employee SET name=?,contact=?,gender=?,job_role=? WHERE employee_id=?";
        return CrudUtil.execute(sql, ob.getName(), ob.getContact(),ob.getGender(),ob.getJob_role(),ob.getEmployee_id());
    }


    public static boolean deleteEmployee(String id) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id=?";
        return CrudUtil.execute(sql,id);
    }

    public static EmployeeDTO searchEmployee(String id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, id);
        if (resultSet.next()){
            String employee_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String gender = resultSet.getString(4);
            String job_role = resultSet.getString(5);

            return new EmployeeDTO(employee_id,name,contact,gender,job_role);
        }
        return null;
    }


    public static List<EmployeeDTO> searchAll() throws SQLException {
        String sql = "SELECT * FROM employee";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<EmployeeDTO> dataList = new ArrayList<>();
        while (resultSet.next()){
            String employee_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);
            String gender = resultSet.getString(4);
            String job_role = resultSet.getString(5);

            var employee_To = new EmployeeDTO(employee_id,name,contact,gender,job_role);
            dataList.add(employee_To);
        }
        return dataList;
    }

    public static List<EmployeeDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        List<EmployeeDTO> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new EmployeeDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static EmployeeDTO searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee WHERE employee_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new EmployeeDTO(
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

        String sql = "SELECT employee FROM employee_id";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static String generateNewId() throws SQLException {
        String lastId = getLastId();
        if(lastId==null){
            return "E-001";
        }
        String[] split = lastId.split("[E][-]");
        String s = split[1];
        int num = Integer.parseInt(s);
        num++;
        String newId = String.format("E-%03d", num);
        return newId;
    }


    private static String getLastId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT employee_id FROM EMPLOYEE ORDER BY employee_id DESC LIMIT 1");
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }
}
