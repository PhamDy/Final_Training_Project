package tasc.finalproject.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    private long orderId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String address;
    private String optional;
    private String email;
    private String phone;
    private String note;
    private String code;
    private double totalPrice;
    private long userId;
    private long paymentId;
    private long deliveryId;
    private OrderStatus status;
}
