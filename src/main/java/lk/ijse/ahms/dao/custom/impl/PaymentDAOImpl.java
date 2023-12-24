package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.PaymentDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PaymentDto;
import lk.ijse.ahms.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    public List<Payment> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM payment order by amount asc limit 5");

        ArrayList<Payment> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new Payment(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }

        return dtoList;

    }

    @Override
    public boolean update(Payment dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    public String splitId(String currentPayId) {
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

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


    public boolean save(Payment ent) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO payment VALUES(?, ?, ?, ?)",
                ent.getPaymentId(), ent.getDate(), ent.getAmount(), ent.getAppointmentId());
    }
}
