package lk.ijse.ahms.dao.custom;

import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.tm.CartTm;
import lk.ijse.ahms.entity.Medicine;

import java.sql.SQLException;
import java.util.List;

public interface MedicineDAO extends CrudDAO<Medicine> {
    boolean update(List<CartTm> cartTmList) throws SQLException, ClassNotFoundException;
    boolean updateQty(String medid, String qty) throws SQLException, ClassNotFoundException;
}
