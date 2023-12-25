package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.custom.AppointmentDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.entity.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {
    @Override
    public List<Appointment> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM appointment");
        ArrayList<Appointment> appointmentDtos = new ArrayList<>();

        while(resultSet.next()){
            appointmentDtos.add(
                    new Appointment(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getString(10),
                            resultSet.getString(11)
                    )
            );
        }


        return appointmentDtos;
    }



    @Override
    public boolean update(Appointment dto) throws SQLException, ClassNotFoundException {
        return false;
    }
    @Override
    public boolean save(Appointment dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO appointment VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                dto.getAppointmentId(),dto.getAmount(),dto.getDate(),
                dto.getTime(),dto.getDescription(),dto.getDoctorId(),
                dto.getDoctorName(),dto.getPetOwnerId(),dto.getPetOwnerName(),
                dto.getPetId(),dto.getPetName());

    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM appointment WHERE appointment_id=?", id);

    }
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT appointment_id FROM appointment ORDER BY appointment_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }
    @Override
    public String splitId(String currentAppId) {
        if(currentAppId != null) {
            String[] split = currentAppId.split("A0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "A00" + id;
            } else {
                return "A0" + id;
            }
        } else {
            return "A001";
        }
    }
    @Override
    public Appointment search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM appointment WHERE appointment_id=?", id);

        if(resultSet.next()){
            return new Appointment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11)
            );
        }else {
            return null;
        }
    }
}
