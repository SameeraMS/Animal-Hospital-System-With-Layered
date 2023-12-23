package lk.ijse.ahms.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Pet {
    private String petId;
    private String name;
    private String age;
    private String gender;
    private String type;
    private String ownerId;

}
