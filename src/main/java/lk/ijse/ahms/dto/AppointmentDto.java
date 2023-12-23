package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AppointmentDto {

    private String appointmentId;
    private String amount;
    private String date;
    private String time;
    private String description;
    private String doctorId;
    private String doctorName;
    private String petOwnerId;
    private String petOwnerName;
    private String petId;
    private String petName;

}
