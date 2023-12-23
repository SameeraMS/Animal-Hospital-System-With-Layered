package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.UserDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public static UserDto searchByName(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE user_name = ?", name);
        if(resultSet.next()) {
            return new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }

    public static List<String> getUserName() throws SQLException, ClassNotFoundException {

        List<String> username = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT user_name FROM user");
        while (resultSet.next()) {
            username.add(resultSet.getString(1));
        }
        return username;
    }

    public static List<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user");

        List<UserDto> userList = new ArrayList<>();

        while (resultSet.next()) {
            userList.add(
                    new UserDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return userList;
    }

    public static boolean changePassword(String username, String newpassword) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE user SET password=? WHERE user_name=?",
                newpassword, username);
    }

    public static boolean deleteUser(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM user WHERE user_name=?", id);
    }

    public static boolean saveUser(UserDto dto2) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?)",
                dto2.getUsername(), dto2.getPassword(), dto2.getEmpId());
    }

    public ResultSet checkCredentials(String username, String password) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE user_name=?", username);
        return resultSet;
    }


}
