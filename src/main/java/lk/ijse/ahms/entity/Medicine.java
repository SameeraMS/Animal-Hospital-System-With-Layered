package lk.ijse.ahms.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Medicine {
    private String medId;
    private String name;
    private String type;
    private String qty;
    private String price;
    private String description;
    private String expdate;

}
