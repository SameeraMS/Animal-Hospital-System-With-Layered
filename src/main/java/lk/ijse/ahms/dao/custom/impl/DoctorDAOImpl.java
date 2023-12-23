package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.custom.DoctorDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dao.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl {
    public static boolean saveDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("INSERT INTO doctor VALUES(?, ?, ?, ?)",
                dto.getDocId(),dto.getName(),dto.getTel(),dto.getEmail());

        return isSaved;


    }

    public static List<DoctorDto> getAllDoctor() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM doctor");

        ArrayList<DoctorDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new DoctorDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public static DoctorDto getDocDetails(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM doctor WHERE doc_id = ?", id);

        if(resultSet.next()) {
            return new DoctorDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            return null;}
    }

    public static boolean updateDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("UPDATE doctor SET name =?, email =?, contact_no =? WHERE doc_id =?",
                dto.getName(),dto.getEmail(),dto.getTel(),dto.getDocId());

        return isSaved;

    }

    public static boolean deleteDoctor(String id) throws SQLException, ClassNotFoundException {

        boolean isDelete = SQLUtil.execute("DELETE FROM doctor WHERE doc_id =?",
                id);

        return isDelete;

    }

    public static String generateNextDocId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT doc_id FROM doctor ORDER BY doc_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitDocId(resultSet.getString(1));
        }
        return splitDocId(null);
    }

    private static String splitDocId(String currentDocId) {
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
