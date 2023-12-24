package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dao.CrudDAO;
import lk.ijse.ahms.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException;
    List<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException;
    boolean updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException;
    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
    String generateNextPaymentId() throws SQLException, ClassNotFoundException;
    PaymentDto searchPayment(String id) throws SQLException, ClassNotFoundException;
}
