package lk.ijse.hospital.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTM {
    private String employee_id;
    private String name;
    private String contact;
    private String gender;
    private String job_role;

}
