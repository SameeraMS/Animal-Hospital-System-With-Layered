package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.EmployeeBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(
                dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getEmail(),dto.getType()));
    }
    @Override
    public List<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        List<Employee> all = employeeDAO.getAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();

        for (Employee employee : all) {
            employeeDtos.add(new EmployeeDto(
                    employee.getId(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getEmail(),employee.getType()
            ));
        }
        return employeeDtos;
    }
    @Override
    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(
                dto.getId(),dto.getName(),dto.getAddress(),dto.getTel(),dto.getEmail(),dto.getType()
        ));
    }
    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }
    @Override
    public String generateNextEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNextId();
    }
    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        return new EmployeeDto(
                employee.getId(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getEmail(),employee.getType()
        );
    }
}
