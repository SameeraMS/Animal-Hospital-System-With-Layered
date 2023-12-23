package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dao.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl {

    public static boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?)",
                dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getEmail(),dto.getType());

        return isSaved;
    }

    public static List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");

        ArrayList<EmployeeDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new EmployeeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        return dtoList;


    }

    public static EmployeeDto getEmployeeDetails(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE employee_id = ?", id);

        if(resultSet.next()) {
            return new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        } else {
            return null;}
    }

    public static boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("UPDATE employee SET employee_name =?, employee_address =?, employee_contact_no =?, employee_email =?, employee_type =? WHERE employee_id =?",
                dto.getName(),dto.getAddress(),dto.getTel(),dto.getEmail(),dto.getType(),dto.getId());

        return isSaved;

    }

    public static boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {

        boolean isDelete = SQLUtil.execute("DELETE FROM employee WHERE employee_id =?",
                id);

        return isDelete;

    }

    public static String generateNextempId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitempId(resultSet.getString(1));
        }
        return splitempId(null);
    }

    private static String splitempId(String currentempId) {
        if(currentempId != null) {
            String[] split = currentempId.split("E0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "E00" + id;
            } else {
                return "E0" + id;
            }
        } else {
            return "E001";
        }
    }
}
