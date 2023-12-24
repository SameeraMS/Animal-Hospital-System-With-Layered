package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.MedicineDto;

import java.sql.SQLException;
import java.util.List;

public interface MedicineBO extends SuperBO {
    boolean saveMedicine(MedicineDto dto) throws SQLException, ClassNotFoundException;
    List<MedicineDto> getAllMedicine() throws SQLException, ClassNotFoundException;
    boolean updateMedicine(MedicineDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteMedicine(String id) throws SQLException, ClassNotFoundException;
    String generateNextMedicineId() throws SQLException, ClassNotFoundException;
    MedicineDto searchMedicine(String id) throws SQLException, ClassNotFoundException;
}
