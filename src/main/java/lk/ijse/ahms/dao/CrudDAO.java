package lk.ijse.ahms.dao;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.DoctorDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO <T>{
      boolean save(T dto) throws SQLException, ClassNotFoundException;
      List<T> getAll() throws SQLException;
      T getDetails(String id) throws SQLException;
      boolean update(T dto) throws SQLException, ClassNotFoundException;
      boolean delete(String id) throws SQLException, ClassNotFoundException;
      String generateNextId() throws SQLException;
      String splitId(String id);
}
