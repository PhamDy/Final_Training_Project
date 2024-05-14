package tasc.finalproject.PaymentService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends BaseEntity{
    private long payment_id;
    private long order_id;
    private String payment_method;
    private PaymentStatus status;
    private double amount;
}
