package lk.ijse.ahms.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PetOwner {
    private String ownerId;
    private String name;
    private String email;
    private String tel;
}
