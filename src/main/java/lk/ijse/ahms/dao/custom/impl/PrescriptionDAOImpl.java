package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.PrescriptionDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAOImpl {
    public static boolean savePrescription(PrescriptionDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO prescription VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPrescriptionId());
        pstm.setString(2, dto.getDescription());
        pstm.setString(3, dto.getAppointmentId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public static PrescriptionDto searchPrescription(String presid) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prescription WHERE presc_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, presid);

        ResultSet resultSet = pstm.executeQuery();

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


    public static List<AppointmentDto> getAllAppointmentId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

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

    public static boolean deletePrescription(String presid) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM prescription WHERE presc_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, presid);

        boolean isDeleted = pstm.executeUpdate() > 0;

        return isDeleted;
    }

    public static boolean updatePrescription(PrescriptionDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE prescription SET description=?, appointment_id=? WHERE presc_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDescription());
        pstm.setString(2, dto.getAppointmentId());
        pstm.setString(3, dto.getPrescriptionId());

        boolean isUpdated = pstm.executeUpdate() > 0;

        return isUpdated;
    }

    public static PrescriptionDto searchPrescriptionbyAppId(String appId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prescription WHERE appointment_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, appId);

        ResultSet resultSet = pstm.executeQuery();

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

    public static String generateNextPresId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT presc_id FROM prescription ORDER BY presc_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
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

    public static List<PrescriptionDto> getAllPrescriptions() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prescription";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

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
