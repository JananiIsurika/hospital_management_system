package lk.ijse.hospital.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class AppointmentDTO {
    private String appointment_id;
    private String doctor_id;
    private String patient_id;
    private String date;
    private String service;


}
