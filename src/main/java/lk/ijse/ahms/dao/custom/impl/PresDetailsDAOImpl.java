package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PresDetailsDAOImpl {
    public boolean saveOrderDetails(String payId, List<CartTm> cartTmList) throws SQLException {
        

        for(CartTm tm : cartTmList) {
            printDetailsSave(payId, tm);
            if(!saveOrderDetails(payId, tm)) {
                return false;
            }
        }
        return true;
    }

    private void printDetailsSave(String payId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO print VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, payId);
        pstm.setString(2, tm.getMedId());
        pstm.setString(3, tm.getName());
        pstm.setString(4, tm.getQty());
        pstm.setString(5, tm.getUnitPrice());
        pstm.setString(6, tm.getTotal());


        pstm.executeUpdate();

    }


    private boolean saveOrderDetails(String payId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO prescription_details VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, payId);
            pstm.setString(2, tm.getMedId());
            pstm.setString(3, tm.getQty());
            pstm.setString(4, tm.getUnitPrice());


        return pstm.executeUpdate() > 0;
    }
}
