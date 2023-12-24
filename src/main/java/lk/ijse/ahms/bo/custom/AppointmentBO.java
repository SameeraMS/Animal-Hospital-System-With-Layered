package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.AppointmentDAO;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.entity.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface AppointmentBO extends SuperBO {
     List<AppointmentDto> getAllAppointment() throws SQLException, ClassNotFoundException;
     boolean updateAppointment(AppointmentDto dto) throws SQLException, ClassNotFoundException;
     boolean saveAppointment(AppointmentDto dto) throws SQLException, ClassNotFoundException;
     boolean deleteAppointment(String id) throws SQLException, ClassNotFoundException;
     String generateNextAppointmentId() throws SQLException, ClassNotFoundException;
    AppointmentDto searchAppointment(String id) throws SQLException, ClassNotFoundException;
}
