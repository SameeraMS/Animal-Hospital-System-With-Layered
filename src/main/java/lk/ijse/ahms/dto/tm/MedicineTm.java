package lk.ijse.ahms.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MedicineTm {
    private String medId;
    private String name;
    private String type;
    private String price;
    private String description;
    private String expDate;
    private String qty;
}
