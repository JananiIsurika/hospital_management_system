package lk.ijse.hospital.dto.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AppointmentTM {
    private String appointment_id;
    private String doctor_id;
    private String patient_id;
    private String date;
    private String em_id;
    private String service;
}
