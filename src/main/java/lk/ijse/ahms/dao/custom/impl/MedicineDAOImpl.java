package lk.ijse.ahms.dao.custom.impl;

import lk.ijse.ahms.dao.custom.EmployeeDAO;
import lk.ijse.ahms.dao.custom.MedicineDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.tm.CartTm;
import lk.ijse.ahms.dao.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAOImpl implements MedicineDAO {
    public static boolean saveMedicine(MedicineDto dto) throws SQLException, ClassNotFoundException {

        boolean isSaved = SQLUtil.execute("INSERT INTO medicine VALUES(?, ?, ?, ?, ?, ?, ?)",
                dto.getMedId(),dto.getName(),dto.getType(),dto.getQty(),dto.getPrice(),dto.getDescription(),dto.getExpdate());

        return isSaved;

    }

    public static List<MedicineDto> getAllMedicine() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM medicine");

        ArrayList<MedicineDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new MedicineDto(
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

    public static MedicineDto getMedicineDetails(String id) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM medicine WHERE med_id = ?", id);

        if(resultSet.next()) {
            return new MedicineDto(
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

    public static boolean updateMedicine(MedicineDto dto) throws SQLException, ClassNotFoundException {

        boolean isSave = SQLUtil.execute("UPDATE medicine SET name =?, type =?, qty =?, price =?, description =?, exp_date =? WHERE med_id =?",
                dto.getName(),dto.getType(),dto.getQty(),dto.getPrice(),dto.getDescription(),dto.getExpdate(),dto.getMedId());

        return isSave;

    }

    public static boolean deleteMedicine(String id) throws SQLException, ClassNotFoundException {

        boolean isDelete = SQLUtil.execute("DELETE FROM medicine WHERE med_id =?",
                id);

        return isDelete;

    }

    public static String generateNextMedId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT med_id FROM medicine ORDER BY med_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitmedId(resultSet.getString(1));
        }
        return splitmedId(null);
    }

    private static String splitmedId(String currentMedId) {
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


    public boolean updateMed(List<CartTm> cartTmList) throws SQLException, ClassNotFoundException {
        System.out.println("med model -> "+cartTmList);
        for(CartTm tm : cartTmList) {

                if (!updateQty(tm.getMedId(), tm.getQty())) {
                    return false;
                }

        }
        return true;
    }

    public boolean updateQty(String medid, String qty) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("UPDATE medicine SET qty = qty - ? WHERE med_id = ?", qty, medid);
    }
}
