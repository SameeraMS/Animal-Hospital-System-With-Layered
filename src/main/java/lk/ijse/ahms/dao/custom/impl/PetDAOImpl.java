package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.PetDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PetsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {
    public static List<PetsDto> getAllPets() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM pets");

        ArrayList<PetsDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new PetsDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        return dtoList;
    }

    public static boolean savePet(PetsDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO pets VALUES(?,?,?,?,?,?)",
                dto.getPetId(),dto.getName(),dto.getAge(),
                dto.getGender(),dto.getType(),dto.getOwnerId());

    }

    public static PetsDto getPetsDetails(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM pets WHERE pet_id=?", id);

        PetsDto dto = null;

        if(resultSet.next()) {
            dto = new PetsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }

        return dto;
    }

    public static boolean deletePet(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM pets WHERE pet_id=?", id);
    }

    public static boolean updatePet(PetsDto dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE pets SET name=?, age=?, gender=?, type=?, pet_owner_id=? WHERE pet_id=?",
                dto.getName(),dto.getAge(),dto.getGender(),
                dto.getType(),dto.getOwnerId(),dto.getPetId());
    }

    public static String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT pet_id FROM pets ORDER BY pet_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitpetId(resultSet.getString(1));
        }
        return splitpetId(null);
    }

    private static String splitpetId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("PE0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "PE00" + id;
            } else {
                return "PE0" + id;
            }
        } else {
            return "PE001";
        }
    }
}
