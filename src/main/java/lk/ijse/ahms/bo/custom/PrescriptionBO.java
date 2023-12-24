package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.entity.Prescription;

import java.sql.SQLException;
import java.util.List;

public interface PrescriptionBO extends SuperBO {
    boolean savePrescription(PrescriptionDto dto) throws SQLException, ClassNotFoundException;
    List<PrescriptionDto> getAllPrescription() throws SQLException, ClassNotFoundException;
    boolean updatePrescription(PrescriptionDto dto) throws SQLException, ClassNotFoundException;
    boolean deletePrescription(String id) throws SQLException, ClassNotFoundException;
    String generateNextPrescriptionId() throws SQLException, ClassNotFoundException;
    PrescriptionDto searchPrescription(String id) throws SQLException, ClassNotFoundException;
    PrescriptionDto searchPrescriptionbyAppId(String appId) throws SQLException, ClassNotFoundException;

}
