package dao.daos.impl;

import dao.daos.PaymentDAO;
import entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean add(Payment payment) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        return false;
    }

    @Override
    public Payment search(String code) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        return null;
    }

    @Override
    public String generateNewId() throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getUnpaidPayments() throws SQLException {
        return null;
    }
}
