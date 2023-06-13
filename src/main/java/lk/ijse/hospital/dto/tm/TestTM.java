package lk.ijse.hospital.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class TestTM {
    private String test_id;
    private String test_name;
    private String treatments;
    private String description;
    private String patient_id;
}
