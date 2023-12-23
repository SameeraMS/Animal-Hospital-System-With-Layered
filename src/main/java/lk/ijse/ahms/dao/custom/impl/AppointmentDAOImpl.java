package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dao.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl {
    public static List<AppointmentDto> getAllAppointments() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM appointment");
        ArrayList<AppointmentDto> appointmentDtos = new ArrayList<>();

        while(resultSet.next()){
            appointmentDtos.add(
                    new AppointmentDto(
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

    public static boolean saveAppointment(AppointmentDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO appointment VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                dto.getAppointmentId(),dto.getAmount(),dto.getDate(),
                dto.getTime(),dto.getDescription(),dto.getDoctorId(),
                dto.getDoctorName(),dto.getPetOwnerId(),dto.getPetOwnerName(),
                dto.getPetId(),dto.getPetName());

    }

    public static List<AppointmentDto> searchAppointments(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM appointment WHERE appointment_id=?", id);
        ArrayList<AppointmentDto> appointmentDtos = new ArrayList<>();

        while(resultSet.next()){
            appointmentDtos.add(
                    new AppointmentDto(
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


    public static boolean deleteAppoint(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM appointment WHERE appointment_id=?", id);

    }

    public static String generateNextAppointId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT appointment_id FROM appointment ORDER BY appointment_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitAppId(resultSet.getString(1));
        }
        return splitAppId(null);
    }

    private static String splitAppId(String currentAppId) {
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

    public static AppointmentDto searchOwnerId(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM appointment WHERE appointment_id=?", id);

        if(resultSet.next()){
            return new AppointmentDto(
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
