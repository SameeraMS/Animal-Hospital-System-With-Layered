package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.PetsDto;

import java.sql.SQLException;
import java.util.List;

public interface PetBO extends SuperBO {
    boolean savePet(PetsDto dto) throws SQLException, ClassNotFoundException;
    List<PetsDto> getAllPet() throws SQLException, ClassNotFoundException;
    boolean updatePet(PetsDto dto) throws SQLException, ClassNotFoundException;
    boolean deletePet(String id) throws SQLException, ClassNotFoundException;
    String generateNextPetId() throws SQLException, ClassNotFoundException;
    PetsDto searchPet(String id) throws SQLException, ClassNotFoundException;
}
