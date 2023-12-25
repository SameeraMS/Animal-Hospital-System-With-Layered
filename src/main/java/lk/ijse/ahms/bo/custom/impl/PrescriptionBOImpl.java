package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.PrescriptionBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.PrescriptionDAO;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.entity.Prescription;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionBOImpl implements PrescriptionBO {

    PrescriptionDAO prescriptionDAO = (PrescriptionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRESCRIPTION);
    @Override
    public boolean savePrescription(PrescriptionDto dto) throws SQLException, ClassNotFoundException {
        return prescriptionDAO.save(new Prescription(
                dto.getPrescriptionId(),dto.getDescription(),dto.getAppointmentId()
        ));
    }

    @Override
    public List<PrescriptionDto> getAllPrescription() throws SQLException, ClassNotFoundException {
        List<Prescription> all = prescriptionDAO.getAll();
        List<PrescriptionDto> prescriptionDtos = new ArrayList<>();
        for (Prescription prescription : all) {
            prescriptionDtos.add(new PrescriptionDto(
                    prescription.getPrescriptionId(),prescription.getDescription(),prescription.getAppointmentId()
            ));
        }
        return prescriptionDtos;
    }

    @Override
    public boolean updatePrescription(PrescriptionDto dto) throws SQLException, ClassNotFoundException {
        return prescriptionDAO.update(new Prescription(
                dto.getPrescriptionId(),dto.getDescription(),dto.getAppointmentId()
        ));
    }

    @Override
    public boolean deletePrescription(String id) throws SQLException, ClassNotFoundException {
        return prescriptionDAO.delete(id);
    }

    @Override
    public String generateNextPrescriptionId() throws SQLException, ClassNotFoundException {
        return prescriptionDAO.generateNextId();
    }

    @Override
    public PrescriptionDto searchPrescription(String id) throws SQLException, ClassNotFoundException {
        Prescription prescription = prescriptionDAO.search(id);
        return new PrescriptionDto(
                prescription.getPrescriptionId(),prescription.getDescription(),prescription.getAppointmentId()
        );
    }

    @Override
    public PrescriptionDto searchPrescriptionbyAppId(String appId) throws SQLException, ClassNotFoundException {
        Prescription prescription = prescriptionDAO.searchPrescriptionbyAppId(appId);
        return new PrescriptionDto(
                prescription.getPrescriptionId(),prescription.getDescription(),prescription.getAppointmentId()
        );
    }
}
