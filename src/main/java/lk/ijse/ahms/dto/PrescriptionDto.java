package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PrescriptionDto {
    private String prescriptionId;
    private String description;
    private String appointmentId;

}
