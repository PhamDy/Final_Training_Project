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
    private long cartItemId;
    private long productId;
    private long cartId;
    private double price;
    private int quantity;
}
