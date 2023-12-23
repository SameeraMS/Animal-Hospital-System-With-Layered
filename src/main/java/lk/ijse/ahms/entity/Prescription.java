package lk.ijse.ahms.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Prescription {
    private String prescriptionId;
    private String description;
    private String appointmentId;

}
