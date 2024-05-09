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
    private long paymentId;
    private String paymentMethod;
    private PaymentStatus status;
}
