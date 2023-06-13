package lk.ijse.hospital.dto.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class PaymentTM {
    private String payment_id;
    private double price;
    private String appointment_id;
}
