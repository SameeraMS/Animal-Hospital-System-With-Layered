package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.UserDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User search(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE user_name = ?", name);
        if(resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }
    @Override
    public List<String> getUserName() throws SQLException, ClassNotFoundException {

        List<String> username = new ArrayList<>();

        ResultSet resultSet = SQLUtil.execute("SELECT user_name FROM user");
        while (resultSet.next()) {
            username.add(resultSet.getString(1));
        }
        return username;
    }
    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user");

        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            userList.add(
                    new User(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return userList;
    }

    @Override
    public boolean update(User ent) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE user SET password = ? WHERE user_name = ?",
                ent.getPassword(), ent.getUsername());
    }
    @Override
    public boolean changePassword(String username, String newpassword) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE user SET password=? WHERE user_name=?",
                newpassword, username);
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM user WHERE user_name=?", id);
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String splitId(String id) {
        return null;
    }

    @Override
    public boolean save(User ent) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO user VALUES(?, ?, ?)",
                ent.getUsername(), ent.getPassword(), ent.getEmpId());
    }
    @Override
    public ResultSet checkCredentials(String username, String password) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE user_name=?", username);
        return resultSet;
    }


}
