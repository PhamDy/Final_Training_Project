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
    private long order_detail_id;
    private long order_id;
    private long product_id;
    private long quantity;
    private double price;
    private double discount;
}
