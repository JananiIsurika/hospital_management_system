package lk.ijse.hospital.model;

import lk.ijse.hospital.db.DBConnection;
import lk.ijse.hospital.dto.AppointmentDTO;
import lk.ijse.hospital.dto.PaymentDTO;
import lk.ijse.hospital.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentModel {
    public static boolean addNewPayment(PaymentDTO ob) throws SQLException {
        System.out.println(ob);
        String sql = "INSERT INTO payment (payment_id,price,appointment_id) VALUES (?,?,?)";
        return CrudUtil.execute(sql, ob.getPayment_id(), ob.getPrice(), ob.getAppointment_id());
    }

    public static boolean updatePayment(PaymentDTO ob) throws SQLException {
        System.out.println(ob);
        String sql = "UPDATE payment SET price = ? WHERE payment_id = ?";
        //String sql = "INSERT INTO payment (payment_id,price,appointment_id) VALUES (?,?,?)";
        return CrudUtil.execute(sql,  ob.getPrice(), ob.getPayment_id());
    }

    public static PaymentDTO searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment WHERE payment_id = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new PaymentDTO(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }

    public static List<PaymentDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM payment";

        List<PaymentDTO> data = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            data.add(new PaymentDTO(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3)
            ));
        }
        return data;
    }

    public static HashMap<Integer,Double> getMonthlyIncome() throws SQLException {
        HashMap<Integer,Double> hm = new HashMap<>();
        ResultSet rs = CrudUtil.execute("SELECT MONTH(a.date),SUM(p.price) from appointments a inner join payment p on a.appointment_id = p.appointment_id\n" +
                "WHERE YEAR(a.date) = ? GROUP BY MONTH(a.date)", LocalDate.now().getYear());
        while (rs.next()){
            hm.put(rs.getInt(1),rs.getDouble(2));
        }
        return hm;
    }

    public static String generateNewId() throws SQLException {
        String lastId = getLastId();
        if(lastId==null)return "PY-001";
        String[] split = lastId.split("[P][Y][-]");
        int i = Integer.parseInt(split[1]);
        return String.format("PY-%03d",++i);
    }

    private static String  getLastId() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");
        if(rs.next())return rs.getString(1);
        return null;
    }

    public static ArrayList<String> getUnpaidPayments() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT payment_id FROM payment WHERE price = 0");
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()){
            list.add(rs.getString(1));
        }
        return list;
    }
}
