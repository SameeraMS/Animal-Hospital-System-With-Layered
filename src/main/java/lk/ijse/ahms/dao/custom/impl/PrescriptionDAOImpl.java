package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.PrescriptionDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.entity.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAOImpl implements PrescriptionDAO {
    @Override
    public boolean save(Prescription dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO prescription VALUES(?, ?, ?)",
                dto.getPrescriptionId(),dto.getDescription(),dto.getAppointmentId());

    }
    @Override
    public Prescription search(String presid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM prescription WHERE presc_id=?", presid);

        Prescription dto = null;

        if(resultSet.next()) {
            dto = new Prescription(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return dto;
    }

    @Override
    public boolean delete(String presid) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM prescription WHERE presc_id=?", presid);
    }
    @Override
    public boolean update(Prescription dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE prescription SET description=?, appointment_id=? WHERE presc_id=?",
                dto.getDescription(), dto.getAppointmentId(), dto.getPrescriptionId());
    }
    @Override
    public Prescription searchPrescriptionbyAppId(String appId) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM prescription WHERE appointment_id=?", appId);

        Prescription dto = null;

        if(resultSet.next()) {
            dto = new Prescription(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return dto;
    }
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT presc_id FROM prescription ORDER BY presc_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }
    @Override
    public String splitId(String currentId) {
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
    @Override
    public List<Prescription> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM prescription");

        ArrayList<Prescription> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new Prescription(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );

        }
        return dtoList;


    }
}
