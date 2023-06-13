package lk.ijse.hospital.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class DoctorTM {
    private String doctor_id;
    private String name;
    private String contact;
    private int age;
    private String gender;

}
