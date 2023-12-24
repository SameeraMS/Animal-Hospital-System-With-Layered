package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.DoctorDto;

import java.sql.SQLException;
import java.util.List;

public interface DoctorBO extends SuperBO {
    boolean saveDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException;
    List<DoctorDto> getAllDoctor() throws SQLException, ClassNotFoundException;
    boolean updateDoctor(DoctorDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteDoctor(String id) throws SQLException, ClassNotFoundException;
    String generateNextDocId() throws SQLException, ClassNotFoundException;
    DoctorDto searchDoctor(String id) throws SQLException, ClassNotFoundException;
}
