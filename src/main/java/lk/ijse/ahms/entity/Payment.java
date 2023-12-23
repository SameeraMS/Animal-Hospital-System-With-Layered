package lk.ijse.ahms.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Payment {
    private String paymentId;
    private String date;
    private String amount;
    private String appointmentId;

}
