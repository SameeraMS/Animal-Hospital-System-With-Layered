package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.PetOwnerBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.PetOwnerDAO;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.entity.PetOwner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetOwnerBOImpl implements PetOwnerBO {
    PetOwnerDAO petOwnerDAO = (PetOwnerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PET_OWNER);
    @Override
    public boolean savePetOwner(PetOwnerDto dto) throws SQLException, ClassNotFoundException {
        return petOwnerDAO.save(new PetOwner(
                dto.getOwnerId(),dto.getName(),dto.getEmail(),dto.getTel()));
    }

    @Override
    public List<PetOwnerDto> getAllPetOwner() throws SQLException, ClassNotFoundException {
        List<PetOwner> all = petOwnerDAO.getAll();
        List<PetOwnerDto> petOwnerDtos = new ArrayList<>();
        for (PetOwner petOwner : all) {
            petOwnerDtos.add(new PetOwnerDto(
                    petOwner.getOwnerId(),petOwner.getName(),petOwner.getEmail(),petOwner.getTel()
            ));
        }
        return petOwnerDtos;
    }

    @Override
    public boolean updatePetOwner(PetOwnerDto dto) throws SQLException, ClassNotFoundException {
        return petOwnerDAO.update(new PetOwner(
                dto.getOwnerId(),dto.getName(),dto.getEmail(),dto.getTel()
        ));
    }

    @Override
    public boolean deletePetOwner(String id) throws SQLException, ClassNotFoundException {
        return petOwnerDAO.delete(id);
    }

    @Override
    public String generateNextPetOwnerId() throws SQLException, ClassNotFoundException {
        return petOwnerDAO.generateNextId();
    }

    @Override
    public PetOwnerDto searchPetOwner(String id) throws SQLException, ClassNotFoundException {
        PetOwner petOwner = petOwnerDAO.search(id);
        return new PetOwnerDto(
                petOwner.getOwnerId(),petOwner.getName(),petOwner.getEmail(),petOwner.getTel()
        );
    }
}
