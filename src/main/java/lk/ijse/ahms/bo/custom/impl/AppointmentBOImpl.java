package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.AppointmentDAO;

public class AppointmentBOImpl {
    AppointmentDAO appointmentDAO = (AppointmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.APPOINMENT);
}
