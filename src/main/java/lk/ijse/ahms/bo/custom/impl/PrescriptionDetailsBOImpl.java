package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.PrescriptionDetailsBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.PrescriptionDetailsDAO;
import lk.ijse.ahms.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionDetailsBOImpl implements PrescriptionDetailsBO {
    PrescriptionDetailsDAO prescriptionDetailsDAO = (PrescriptionDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRESCRIPTION_DETAIL);
    @Override
    public boolean saveOrderDetails(String payId, List<CartTm> cartTmList) throws SQLException, ClassNotFoundException {
        return prescriptionDetailsDAO.saveOrderDetails(payId, cartTmList);
    }

    @Override
    public void printDetailsSave(String payId, CartTm tm) throws SQLException, ClassNotFoundException {
        prescriptionDetailsDAO.printDetailsSave(payId, tm);
    }

    @Override
    public boolean saveOrderDetails(String payId, CartTm tm) throws SQLException, ClassNotFoundException {
        return prescriptionDetailsDAO.saveOrderDetails(payId, tm);
    }
}
