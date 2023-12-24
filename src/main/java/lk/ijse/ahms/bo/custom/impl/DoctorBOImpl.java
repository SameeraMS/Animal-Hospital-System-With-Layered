package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.BOFactory;
import lk.ijse.ahms.bo.custom.DoctorBO;
import lk.ijse.ahms.dao.custom.DoctorDAO;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.entity.Doctor;

import java.sql.SQLException;
import java.util.List;

public class DoctorBOImpl implements DoctorBO {
    DoctorDAO doctorDAO = (DoctorDAO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DOCTOR);
    @Override
    public boolean saveDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException {
        return doctorDAO.save(new Doctor(dto.getDocId(),dto.getName(),dto.getEmail(),dto.getTel()));
    }

    @Override
    public List<DoctorDto> getAllDoctor() throws SQLException, ClassNotFoundException {
        List<Doctor> all = doctorDAO.getAll();
        List<DoctorDto> doctorDtos = null;
        for (Doctor doctor : all) {
            doctorDtos.add(new DoctorDto(doctor.getDocId(),doctor.getName(),doctor.getEmail(),doctor.getTel()));
        }
        return doctorDtos;
    }

    @Override
    public boolean updateDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException {
        return doctorDAO.update(new Doctor(dto.getDocId(),dto.getName(),dto.getEmail(),dto.getTel()));
    }

    @Override
    public boolean deleteDoctor(String id) throws SQLException, ClassNotFoundException {
        return doctorDAO.delete(id);
    }

    @Override
    public String generateNextDocId() throws SQLException, ClassNotFoundException {
        return doctorDAO.generateNextId();
    }

    @Override
    public DoctorDto searchDoctor(String id) throws SQLException, ClassNotFoundException {
        Doctor doctor = doctorDAO.search(id);
        return new DoctorDto(doctor.getDocId(),doctor.getName(),doctor.getEmail(),doctor.getTel());
    }
}
