package tasc.finalproject.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long orderId;
    private String paymentMethod;
    private PaymentStatus status;
    private double amount;
}
