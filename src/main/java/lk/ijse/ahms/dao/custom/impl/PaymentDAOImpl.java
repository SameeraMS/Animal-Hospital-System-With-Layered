package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.PaymentDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PaymentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    public static List<PaymentDto> getPayment() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment order by amount asc limit 5");

        ArrayList<PaymentDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new PaymentDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }

        return dtoList;

    }

    public String generateNextPayId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitPayId(resultSet.getString(1));
        }
        return splitPayId(null);
    }

    private String splitPayId(String currentPayId) {
        if(currentPayId != null) {
            String[] split = currentPayId.split("P0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "P00" + id;
            } else {
                return "P0" + id;
            }
        } else {
            return "P001";
        }
    }


    public boolean savePayment(String payId, String date, String amount, String appointId) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO payment VALUES(?, ?, ?, ?)",
                payId, date, amount, appointId);
    }
}
