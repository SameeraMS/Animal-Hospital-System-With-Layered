package lk.ijse.ahms.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppointmentTm{
    private String id;
    private String date;
    private String time;
    private String description;
    private String docId;
    private String petOwnerId;
}
