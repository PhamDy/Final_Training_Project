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
    private long order_id;
    private String first_name;
    private String last_name    ;
    private String country;
    private String city;
    private String address;
    private String optional;
    private String email;
    private String phone;
    private String note;
    private String code;
    private double total_price;
    private long user_id;
    private long payment_id;
    private long delivery_id;
    private OrderStatus status;
}
