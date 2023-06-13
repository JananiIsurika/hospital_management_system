package dao.daos;

import dao.CrudDAO;
import entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment> {

    ArrayList<String> getUnpaidPayments() throws SQLException;
}
