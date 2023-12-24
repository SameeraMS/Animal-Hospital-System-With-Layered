package lk.ijse.ahms.dao.custom;

import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.entity.Prescription;

import java.sql.SQLException;

public interface PrescriptionDAO extends CrudDAO<Prescription> {
    Prescription searchPrescriptionbyAppId(String appId) throws SQLException, ClassNotFoundException;
}
