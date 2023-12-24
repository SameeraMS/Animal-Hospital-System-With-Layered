package lk.ijse.ahms.dao.custom;

import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.SuperDAO;
import lk.ijse.ahms.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public interface PrescriptionDetailsDAO extends SuperDAO {
     boolean saveOrderDetails(String payId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException;
     void printDetailsSave(String payId, CartTm tm) throws SQLException, ClassNotFoundException;
     boolean saveOrderDetails(String payId, CartTm tm) throws SQLException, ClassNotFoundException;

}
