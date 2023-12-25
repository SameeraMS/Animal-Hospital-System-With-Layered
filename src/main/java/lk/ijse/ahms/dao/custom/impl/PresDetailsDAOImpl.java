package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.PrescriptionDetailsDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PresDetailsDAOImpl implements PrescriptionDetailsDAO {
    @Override
    public boolean saveOrderDetails(String payId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException {
        

        for(CartTm tm : cartTmList) {
            printDetailsSave(payId, tm);
            if(!saveOrderDetails(payId, tm)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void printDetailsSave(String payId, CartTm tm) throws SQLException, ClassNotFoundException {

        SQLUtil.execute("INSERT INTO print VALUES(?, ?, ?, ?, ?, ?)",
                payId, tm.getMedId(), tm.getName(),
                tm.getQty(), tm.getUnitPrice(), tm.getTotal());

    }

    @Override
    public boolean saveOrderDetails(String payId, CartTm tm) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO prescription_details VALUES(?, ?, ?, ?)",
                payId, tm.getMedId(), tm.getQty(), tm.getUnitPrice());
    }
}
