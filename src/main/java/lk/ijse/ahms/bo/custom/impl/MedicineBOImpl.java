package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.MedicineBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.MedicineDAO;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.entity.Medicine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineBOImpl implements MedicineBO {

    MedicineDAO medicineDAO = (MedicineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEDICINE);
    @Override
    public boolean saveMedicine(MedicineDto dto) throws SQLException, ClassNotFoundException {
        return medicineDAO.save(new Medicine(
                dto.getMedId(),dto.getName(),dto.getType(),
                dto.getQty(),dto.getPrice(),dto.getDescription(),
                dto.getExpdate()
        ));
    }

    @Override
    public List<MedicineDto> getAllMedicine() throws SQLException, ClassNotFoundException {
        List<Medicine> all = medicineDAO.getAll();
        List<MedicineDto> medicineDtos = new ArrayList<>();

        for (Medicine medicine : all) {
            medicineDtos.add(new MedicineDto(
                    medicine.getMedId(),medicine.getName(),medicine.getType(),
                    medicine.getQty(),medicine.getPrice(),medicine.getDescription(),
                    medicine.getExpdate()
            ));
        }

        return medicineDtos;
    }

    @Override
    public boolean updateMedicine(MedicineDto dto) throws SQLException, ClassNotFoundException {
        return medicineDAO.update(new Medicine(
                dto.getMedId(),dto.getName(),dto.getType(),
                dto.getQty(),dto.getPrice(),dto.getDescription(),
                dto.getExpdate()
        ));
    }

    @Override
    public boolean deleteMedicine(String id) throws SQLException, ClassNotFoundException {
        return medicineDAO.delete(id);
    }

    @Override
    public String generateNextMedicineId() throws SQLException, ClassNotFoundException {
        return medicineDAO.generateNextId();
    }

    @Override
    public MedicineDto searchMedicine(String id) throws SQLException, ClassNotFoundException {
        Medicine medicine = medicineDAO.search(id);
        return new MedicineDto(
                medicine.getMedId(),medicine.getName(),medicine.getType(),
                medicine.getQty(),medicine.getPrice(),medicine.getDescription(),
                medicine.getExpdate()
        );
    }
}
