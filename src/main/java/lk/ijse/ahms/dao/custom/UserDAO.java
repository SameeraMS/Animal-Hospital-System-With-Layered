package lk.ijse.ahms.dao.custom;

import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {
      List<String> getUserName() throws SQLException, ClassNotFoundException;
      boolean changePassword(String username, String newpassword) throws SQLException, ClassNotFoundException;
      ResultSet checkCredentials(String username, String password) throws SQLException, ClassNotFoundException;


}
