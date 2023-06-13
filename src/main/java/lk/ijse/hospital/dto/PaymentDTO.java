package lk.ijse.hospital.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class PaymentDTO {
    private String payment_id;
    private double price;
    private String appointment_id;
}
