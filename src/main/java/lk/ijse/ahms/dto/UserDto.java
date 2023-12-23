package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {
    private String username;
    private String password;
    private String empId;
}
