package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PaymentDto {
    private String paymentId;
    private String date;
    private String amount;
    private String appointmentId;

}
