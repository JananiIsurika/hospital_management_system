package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> {

    boolean add(T payment) throws SQLException;

    boolean update(T payment) throws SQLException;

    T search(String code) throws SQLException;

    ArrayList<T> getAll() throws SQLException;

    String generateNewId() throws SQLException;

}
