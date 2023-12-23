package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PetsDto {
    private String petId;
    private String name;
    private String age;
    private String gender;
    private String type;
    private String ownerId;

}
