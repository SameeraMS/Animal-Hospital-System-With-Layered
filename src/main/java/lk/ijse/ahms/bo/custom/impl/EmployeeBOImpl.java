package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.EmployeeDAO;

public class EmployeeBOImpl {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
}
