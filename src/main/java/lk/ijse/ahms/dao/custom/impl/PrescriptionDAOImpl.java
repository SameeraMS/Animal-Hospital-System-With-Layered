package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.PrescriptionDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.PrescriptionDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAOImpl implements PrescriptionDAO {
    public static boolean savePrescription(PrescriptionDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO prescription VALUES(?, ?, ?)",
                dto.getPrescriptionId(),dto.getDescription(),dto.getAppointmentId());

    }

    public static PrescriptionDto searchPrescription(String presid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM prescription WHERE presc_id=?", presid);

        PrescriptionDto dto = null;

        if(resultSet.next()) {
            dto = new PrescriptionDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return dto;
    }


    public static List<AppointmentDto> getAllAppointmentId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM appointment");

        ArrayList<AppointmentDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
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
        return dtoList;
    }

    public static boolean deletePrescription(String presid) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM prescription WHERE presc_id=?", presid);
    }

    public static boolean updatePrescription(PrescriptionDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE prescription SET description=?, appointment_id=? WHERE presc_id=?",
                dto.getDescription(), dto.getAppointmentId(), dto.getPrescriptionId());
    }

    public static PrescriptionDto searchPrescriptionbyAppId(String appId) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM prescription WHERE appointment_id=?", appId);

        PrescriptionDto dto = null;

        if(resultSet.next()) {
            dto = new PrescriptionDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return dto;
    }

    public static String generateNextPresId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT presc_id FROM prescription ORDER BY presc_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitPresId(resultSet.getString(1));
        }
        return splitPresId(null);
    }

    private static String splitPresId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("PR0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "PR00" + id;
            } else {
                return "PR0" + id;
            }
        } else {
            return "PR001";
        }
    }

    public static List<PrescriptionDto> getAllPrescriptions() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM prescription");

        ArrayList<PrescriptionDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new PrescriptionDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );

        }
        return dtoList;


    }
}
