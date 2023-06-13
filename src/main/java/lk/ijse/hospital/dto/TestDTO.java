package lk.ijse.hospital.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class TestDTO {
    private String test_id;
    private String test_name;
    private String treatments;
    private String description;
    private String patient_id;
}
