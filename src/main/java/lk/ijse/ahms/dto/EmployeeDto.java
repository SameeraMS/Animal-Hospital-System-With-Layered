package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeDto {
    private String id;
    private String name;
    private String address;
    private String tel;
    private String email;
    private String type;

}
