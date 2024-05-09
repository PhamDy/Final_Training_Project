package tasc.finalproject.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDetails extends BaseEntity{
    private long orderDetailsId;
    private long orderId;
    private long productId;
    private long quantity;
    private double price;
    private double discount;
}
