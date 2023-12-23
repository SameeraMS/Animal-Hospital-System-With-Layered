package lk.ijse.ahms.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartTm {
    private String medId;
    private String name;
    private String unitPrice;
    private String qty;
    private String total;
}
