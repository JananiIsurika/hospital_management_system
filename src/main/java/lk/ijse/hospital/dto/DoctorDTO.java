package lk.ijse.hospital.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class DoctorDTO {
    private String doctor_id;
    private String name;
    private String contact;
    private int age;
    private String gender;

}
