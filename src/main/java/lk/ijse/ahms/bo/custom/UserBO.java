package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException;
    List<UserDto> getAllUser() throws SQLException, ClassNotFoundException;
    boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;
    String generateNextUserId() throws SQLException, ClassNotFoundException;
    UserDto searchUser(String id) throws SQLException, ClassNotFoundException;
    List<String> getUserName() throws SQLException, ClassNotFoundException;
    boolean changePassword(String username, String newpassword) throws SQLException, ClassNotFoundException;
    ResultSet checkCredentials(String username, String password) throws SQLException, ClassNotFoundException;
}
