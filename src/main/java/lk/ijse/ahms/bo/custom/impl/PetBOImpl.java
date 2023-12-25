package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.PetBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.PetDAO;
import lk.ijse.ahms.dto.PetsDto;
import lk.ijse.ahms.entity.Pet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetBOImpl implements PetBO {

    PetDAO petDAO = (PetDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PET);
    @Override
    public boolean savePet(PetsDto dto) throws SQLException, ClassNotFoundException {
        return petDAO.save(new Pet(dto.getPetId(),dto.getName(),dto.getAge(),dto.getGender(),
                dto.getType(),dto.getOwnerId()));
    }

    @Override
    public List<PetsDto> getAllPet() throws SQLException, ClassNotFoundException {
        List<Pet> all = petDAO.getAll();
        List<PetsDto> petsDtos = new ArrayList<>();
        for (Pet pet : all) {
            petsDtos.add(new PetsDto(pet.getPetId(),pet.getName(),pet.getAge(),pet.getGender(),
                    pet.getType(),pet.getOwnerId()));
        }
        return petsDtos;
    }

    @Override
    public boolean updatePet(PetsDto dto) throws SQLException, ClassNotFoundException {
        return petDAO.update(new Pet(dto.getPetId(),dto.getName(),dto.getAge(),dto.getGender(),
                dto.getType(),dto.getOwnerId()));
    }

    @Override
    public boolean deletePet(String id) throws SQLException, ClassNotFoundException {
        return petDAO.delete(id);
    }

    @Override
    public String generateNextPetId() throws SQLException, ClassNotFoundException {
        return petDAO.generateNextId();
    }

    @Override
    public PetsDto searchPet(String id) throws SQLException, ClassNotFoundException {
        Pet pet = petDAO.search(id);
        return new PetsDto(pet.getPetId(),pet.getName(),pet.getAge(),pet.getGender(),
                pet.getType(),pet.getOwnerId());
    }
}
