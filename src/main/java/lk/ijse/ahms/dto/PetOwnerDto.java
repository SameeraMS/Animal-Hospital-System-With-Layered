package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PetOwnerDto {
    private String ownerId;
    private String name;
    private String email;
    private String tel;
}
