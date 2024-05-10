package tasc.finalproject.CartService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItems extends BaseEntity{
    private long cart_item_id;
    private long product_id;
    private long cart_id;
    private double price;
    private long quantity;
}
