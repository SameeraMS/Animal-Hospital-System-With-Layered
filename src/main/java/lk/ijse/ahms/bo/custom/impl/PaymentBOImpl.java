package lk.ijse.ahms.bo.custom.impl;

import lk.ijse.ahms.bo.custom.PaymentBO;
import lk.ijse.ahms.dao.DAOFactory;
import lk.ijse.ahms.dao.custom.PaymentDAO;
import lk.ijse.ahms.dto.PaymentDto;
import lk.ijse.ahms.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(
                dto.getPaymentId(),dto.getDate(),dto.getAmount(),dto.getAppointmentId()
        ));
    }

    @Override
    public List<PaymentDto> getAllPayment() throws SQLException, ClassNotFoundException {
        List<Payment> all = paymentDAO.getAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();

        for (Payment payment : all) {
            paymentDtos.add(new PaymentDto(
                    payment.getPaymentId(),payment.getDate(),payment.getAmount(),payment.getAppointmentId()
            ));
        }

        return paymentDtos;
    }

    @Override
    public boolean updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(
                dto.getPaymentId(),dto.getDate(),dto.getAmount(),dto.getAppointmentId()
        ));
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(id);
    }

    @Override
    public String generateNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNextId();
    }

    @Override
    public PaymentDto searchPayment(String id) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.search(id);
        return new PaymentDto(
                payment.getPaymentId(),payment.getDate(),payment.getAmount(),payment.getAppointmentId()
        );
    }
}
