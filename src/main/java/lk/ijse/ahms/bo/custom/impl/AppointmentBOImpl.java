package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.AppointmentBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.AppointmentDAO;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.entity.Appointment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentBOImpl implements AppointmentBO {
    AppointmentDAO appointmentDAO = (AppointmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.APPOINTMENT);


    @Override
    public List<AppointmentDto> getAllAppointment() throws SQLException, ClassNotFoundException {
        List<Appointment> all = appointmentDAO.getAll();
        List<AppointmentDto> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : all) {
            appointmentDtos.add(new AppointmentDto(
                    appointment.getAppointmentId(),appointment.getAmount(),appointment.getDate(),
                    appointment.getTime(),appointment.getDescription(),appointment.getDoctorId(),
                    appointment.getDoctorName(),appointment.getPetOwnerId(),appointment.getPetOwnerName(),
                    appointment.getPetId(),appointment.getPetName()));
        }

        return appointmentDtos;
    }


    @Override
    public boolean updateAppointment(AppointmentDto dto) throws SQLException, ClassNotFoundException {
        return appointmentDAO.update(new Appointment(
                dto.getAppointmentId(),dto.getAmount(),dto.getDate(),
                dto.getTime(),dto.getDescription(),dto.getDoctorId(),
                dto.getDoctorName(),dto.getPetOwnerId(),dto.getPetOwnerName(),
                dto.getPetId(),dto.getPetName()));
    }

    @Override
    public boolean saveAppointment(AppointmentDto dto) throws SQLException, ClassNotFoundException {
        return appointmentDAO.save(new Appointment(
                dto.getAppointmentId(),dto.getAmount(),dto.getDate(),
                dto.getTime(),dto.getDescription(),dto.getDoctorId(),
                dto.getDoctorName(),dto.getPetOwnerId(),dto.getPetOwnerName(),
                dto.getPetId(),dto.getPetName()
        ));
    }

    @Override
    public boolean deleteAppointment(String id) throws SQLException, ClassNotFoundException {
        return appointmentDAO.delete(id);
    }

    @Override
    public String generateNextAppointmentId() throws SQLException, ClassNotFoundException {
        return appointmentDAO.generateNextId();
    }

    @Override
    public AppointmentDto searchAppointment(String id) throws SQLException, ClassNotFoundException {
        Appointment search = appointmentDAO.search(id);
        return new AppointmentDto(search.getAppointmentId(),search.getAmount(),search.getDate(),
                search.getTime(),search.getDescription(),search.getDoctorId(),
                search.getDoctorName(),search.getPetOwnerId(),search.getPetOwnerName(),
                search.getPetId(),search.getPetName());
    }
}
