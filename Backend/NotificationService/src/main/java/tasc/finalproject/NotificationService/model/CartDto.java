package tasc.finalproject.NotificationService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private long cartId;
    private long userId;
    private List<CartItemResponse> cartItemResponseList;
    private double totalPrice;
}
