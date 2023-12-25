package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.custom.DoctorDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.entity.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {
    @Override
    public boolean save(Doctor dto) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("INSERT INTO doctor VALUES(?, ?, ?, ?)",
                dto.getDocId(),dto.getName(),dto.getTel(),dto.getEmail());

        return isSaved;


    }
    @Override
    public List<Doctor> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM doctor");

        ArrayList<Doctor> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new Doctor(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }
    @Override
    public Doctor search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM doctor WHERE doc_id = ?", id);

        if(resultSet.next()) {
            return new Doctor(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            return null;}
    }
    @Override
    public boolean update(Doctor dto) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("UPDATE doctor SET name =?, email =?, contact_no =? WHERE doc_id =?",
                dto.getName(),dto.getEmail(),dto.getTel(),dto.getDocId());

        return isSaved;

    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        boolean isDelete = SQLUtil.execute("DELETE FROM doctor WHERE doc_id =?",
                id);

        return isDelete;

    }
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT doc_id FROM doctor ORDER BY doc_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }
    @Override
    public String splitId(String currentDocId) {
        if(currentDocId != null) {
            String[] split = currentDocId.split("D0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "D00" + id;
            } else {
                return "D0" + id;
            }
        } else {
            return "D001";
        }
    }
}
