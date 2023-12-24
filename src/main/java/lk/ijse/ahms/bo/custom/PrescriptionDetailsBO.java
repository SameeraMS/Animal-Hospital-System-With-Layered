package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public interface PrescriptionDetailsBO extends SuperBO {
    boolean saveOrderDetails(String payId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException;
    void printDetailsSave(String payId, CartTm tm) throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(String payId, CartTm tm) throws SQLException, ClassNotFoundException;
}
