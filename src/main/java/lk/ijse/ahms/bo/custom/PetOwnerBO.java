package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.PetOwnerDto;

import java.sql.SQLException;
import java.util.List;

public interface PetOwnerBO extends SuperBO {
    boolean savePetOwner(PetOwnerDto dto) throws SQLException, ClassNotFoundException;
    List<PetOwnerDto> getAllPetOwner() throws SQLException, ClassNotFoundException;
    boolean updatePetOwner(PetOwnerDto dto) throws SQLException, ClassNotFoundException;
    boolean deletePetOwner(String id) throws SQLException, ClassNotFoundException;
    String generateNextPetOwnerId() throws SQLException, ClassNotFoundException;
    PetOwnerDto searchPetOwner(String id) throws SQLException, ClassNotFoundException;
}
