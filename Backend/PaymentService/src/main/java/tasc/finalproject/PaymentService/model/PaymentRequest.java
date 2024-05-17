package tasc.finalproject.PaymentService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tasc.finalproject.PaymentService.entity.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long order_id;
    private String payment_method;
    private PaymentStatus status;
    private double amount;
}
