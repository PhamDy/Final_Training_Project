package tasc.finalproject.NotificationService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private long cartItemId;
    private long productId;
    private String productName;
    private String img;
    private long quantity;
    private double price;
    private float discount;
}
