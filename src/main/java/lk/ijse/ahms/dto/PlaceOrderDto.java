package lk.ijse.ahms.dto;

import lk.ijse.ahms.dto.tm.CartTm;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PlaceOrderDto {

    private String payId;
    private String date;
    private String amoount;
    private String appointId;
    private List<CartTm> cartTmList = new ArrayList<>();
}
