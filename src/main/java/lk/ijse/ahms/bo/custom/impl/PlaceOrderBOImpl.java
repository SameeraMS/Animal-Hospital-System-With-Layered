package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.PlaceOrderBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.MedicineDAO;
import lk.ijse.ahms.dao.custom.PaymentDAO;
import lk.ijse.ahms.dao.custom.PrescriptionDetailsDAO;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PlaceOrderDto;
import lk.ijse.ahms.entity.Payment;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    PaymentDAO paymentDAOImpl = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    MedicineDAO medicineDAOImpl = (MedicineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEDICINE);
    PrescriptionDetailsDAO presDetailsDAOImpl = (PrescriptionDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRESCRIPTION_DETAIL);
    @Override
    public boolean saveOrder(PlaceOrderDto placeOrderDto) throws SQLException, ClassNotFoundException {
        boolean isSuccess = false;
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        Payment ent = new Payment(placeOrderDto.getPayId(), placeOrderDto.getDate(), placeOrderDto.getAmoount(), placeOrderDto.getAppointId());
        boolean isOrderSaved = paymentDAOImpl.save(ent);
        if (isOrderSaved) {
            boolean isUpdated = medicineDAOImpl.update(placeOrderDto.getCartTmList());
            if(isUpdated) {
                boolean isOrderDetailSaved = presDetailsDAOImpl.saveOrderDetails(placeOrderDto.getPayId(), placeOrderDto.getCartTmList());
                if (isOrderDetailSaved) {
                    connection.commit();
                    isSuccess = true;
                    connection.setAutoCommit(true);
                }
            }
        }
        connection.setAutoCommit(true);
        return isSuccess;
    }
}
