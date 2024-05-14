package tasc.finalproject.PaymentService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tasc.finalproject.PaymentService.entity.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long orderId;
    private String paymentMethod;
    private PaymentStatus status;
    private double amount;
}
