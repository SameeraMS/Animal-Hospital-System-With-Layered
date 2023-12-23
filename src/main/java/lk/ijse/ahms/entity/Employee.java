package lk.ijse.ahms.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Employee {
    private String id;
    private String name;
    private String address;
    private String tel;
    private String email;
    private String type;

}
