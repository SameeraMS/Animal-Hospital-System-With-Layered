package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.MedicineDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.tm.CartTm;
import lk.ijse.ahms.dao.SQLUtil;
import lk.ijse.ahms.entity.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAOImpl implements MedicineDAO {
    @Override
    public boolean save(Medicine ent) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("INSERT INTO medicine VALUES(?, ?, ?, ?, ?, ?, ?)",
                ent.getMedId(),ent.getName(),ent.getType(),ent.getQty(),ent.getPrice(),ent.getDescription(),ent.getExpdate());

        return isSaved;

    }
    @Override
    public List<Medicine> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM medicine");

        ArrayList<Medicine> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new Medicine(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    )
            );
        }
        return dtoList;

    }
    @Override
    public  Medicine search(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM medicine WHERE med_id = ?", id);

        if(resultSet.next()) {
            return new Medicine(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        } else {
            return null;}
    }
    @Override
    public boolean update(Medicine ent) throws SQLException, ClassNotFoundException {

        boolean isSave = SQLUtil.execute("UPDATE medicine SET name =?, type =?, qty =?, price =?, description =?, exp_date =? WHERE med_id =?",
                ent.getName(),ent.getType(),ent.getQty(),ent.getPrice(),ent.getDescription(),ent.getExpdate(),ent.getMedId());

        return isSave;

    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        boolean isDelete = SQLUtil.execute("DELETE FROM medicine WHERE med_id =?",
                id);

        return isDelete;

    }
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT med_id FROM medicine ORDER BY med_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }
    @Override
    public String splitId(String currentMedId) {
        if(currentMedId != null) {
            String[] split = currentMedId.split("M0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "M00" + id;
            } else {
                return "M0" + id;
            }
        } else {
            return "M001";
        }
    }
    @Override
    public boolean update(List<CartTm> cartTmList) throws SQLException, ClassNotFoundException {
        System.out.println("med model -> "+cartTmList);
        for(CartTm tm : cartTmList) {

                if (!updateQty(tm.getMedId(), tm.getQty())) {
                    return false;
                }

        }
        return true;
    }
    @Override
    public boolean updateQty(String medid, String qty) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE medicine SET qty = qty - ? WHERE med_id = ?", qty, medid);
    }
}
