package lk.ijse.ahms.bo.custom;

import lk.ijse.ahms.bo.SuperBO;
import lk.ijse.ahms.dto.PlaceOrderDto;

import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    boolean saveOrder(PlaceOrderDto placeOrderDto) throws SQLException, ClassNotFoundException;
}
