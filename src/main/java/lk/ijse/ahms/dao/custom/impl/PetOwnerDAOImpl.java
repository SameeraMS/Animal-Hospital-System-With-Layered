package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.PetOwnerDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PetOwnerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetOwnerDAOImpl implements PetOwnerDAO {
    public static boolean savePetOwner(PetOwnerDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO pet_owner VALUES(?, ?, ?, ?)",
                dto.getOwnerId(),dto.getName(),dto.getEmail(),dto.getTel());
    }

    public static List<PetOwnerDto> getAllOwners() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM pet_owner");

        ArrayList<PetOwnerDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new PetOwnerDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public static PetOwnerDto getOwnerDetails(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM pet_owner WHERE pet_owner_id = ?", id);

        if(resultSet.next()) {
            return new PetOwnerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            return null;
        }
    }

    public static boolean deletePetOwner(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM pet_owner WHERE pet_owner_id =?",id);
    }

    public static boolean updatePetOwner(PetOwnerDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE pet_owner SET name =?, email =?, contact_no =? WHERE pet_owner_id =?",
                dto.getName(),dto.getEmail(),dto.getTel(),dto.getOwnerId());

    }

    public static String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT pet_owner_id FROM pet_owner ORDER BY pet_owner_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    private static String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "O00" + id;
            } else {
                return "O0" + id;
            }
        } else {
            return "O001";
        }
    }
}
