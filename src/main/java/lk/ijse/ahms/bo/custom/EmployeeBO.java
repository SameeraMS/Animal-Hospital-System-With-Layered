package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    String generateNextEmployeeId() throws SQLException, ClassNotFoundException;
    EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException;
}
