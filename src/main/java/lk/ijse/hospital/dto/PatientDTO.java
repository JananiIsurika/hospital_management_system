package lk.ijse.hospital.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PatientDTO {
    private String patient_id;
    private String name;
    private String contact;
    private int age;
    private String gender;
    private String dob;
    private String email;
}
