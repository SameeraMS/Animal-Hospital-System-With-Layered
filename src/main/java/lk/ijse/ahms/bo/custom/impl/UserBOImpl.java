package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.UserBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.UserDAO;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.entity.User;
import lk.ijse.ahms.util.SecurityUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean saveUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(
                dto.getUsername(),dto.getPassword(),dto.getEmpId()));
    }

    @Override
    public List<UserDto> getAllUser() throws SQLException, ClassNotFoundException {
        List<User> all = userDAO.getAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : all) {
            userDtos.add(new UserDto(
                    user.getUsername(),user.getPassword(),user.getEmpId()
            ));
        }
        return userDtos;
    }

    @Override
    public boolean updateUser(UserDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(
                dto.getUsername(),dto.getPassword(),dto.getEmpId()
        ));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public String generateNextUserId() throws SQLException, ClassNotFoundException {
        return userDAO.generateNextId();
    }

    @Override
    public UserDto searchUser(String id) throws SQLException, ClassNotFoundException {
        User user = userDAO.search(id);
        if (user == null) {
            return null;
        }else {
            return new UserDto(
                    user.getUsername(),user.getPassword(),user.getEmpId()
            );
        }
    }

    @Override
    public List<String> getUserName() throws SQLException, ClassNotFoundException {
        return userDAO.getUserName();
    }

    @Override
    public boolean changePassword(String username, String newpassword) throws SQLException, ClassNotFoundException {
        return userDAO.changePassword(username, newpassword);
    }

    @Override
    public ResultSet checkCredentials(String username, String password) throws SQLException, ClassNotFoundException {
        return userDAO.checkCredentials(username, password);
    }
}
