package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl {

    public static UserDto searchByName(String name) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE user_name = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }

    public static List<String> getUserName() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT user_name FROM user";

        List<String> username = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            username.add(resultSet.getString(1));
        }
        return username;
    }

    public static List<UserDto> getAllUsers() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user";
        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

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

    public static boolean changePassword(String username, String newpassword) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE user SET password=? WHERE user_name=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, newpassword);
        pstm.setString(2, username);

        boolean ischanged = pstm.executeUpdate() > 0;

        return ischanged;
    }

    public static boolean deleteUser(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM user WHERE user_name=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        boolean isDeleted = pstm.executeUpdate() > 0;

        return isDeleted;
    }

    public static boolean saveUser(UserDto dto2) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto2.getUsername());
        pstm.setString(2, dto2.getPassword());
        pstm.setString(3, dto2.getEmpId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public ResultSet checkCredentials(String username, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE user_name=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,username);
        ResultSet resultSet = pstm.executeQuery();
        return resultSet;
    }


}
