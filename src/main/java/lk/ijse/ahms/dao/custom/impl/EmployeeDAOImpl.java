package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee ent) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?)",
                ent.getId(),ent.getName(),ent.getAddress(),ent.getTel(),ent.getEmail(),ent.getType());

        return isSaved;
    }
    @Override
    public List<Employee> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee");

        ArrayList<Employee> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new Employee(
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
    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE employee_id = ?", id);

        if(resultSet.next()) {
            return new Employee(
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
    @Override
    public boolean update(Employee ent) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("UPDATE employee SET employee_name =?, employee_address =?, employee_contact_no =?, employee_email =?, employee_type =? WHERE employee_id =?",
                ent.getName(),ent.getAddress(),ent.getTel(),ent.getEmail(),ent.getType(),ent.getId());

        return isSaved;

    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        boolean isDelete = SQLUtil.execute("DELETE FROM employee WHERE employee_id =?",
                id);

        return isDelete;

    }
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }
    @Override
    public String splitId(String currentempId) {
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
