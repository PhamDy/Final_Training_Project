package tasc.finalproject.CartService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart extends BaseEntity{
    private long cart_id;
    private long user_id;
    private CartStatus status;
}
