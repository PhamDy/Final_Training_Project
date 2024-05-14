package tasc.finalproject.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tasc.finalproject.OrderService.entity.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String first_name;
    private String last_name;
    private String country;
    private String city;
    private String address;
    private String optional;
    private String email;
    private String phone;
    private String note;
    private String code;
    private long user_id;
    private long delivery_id;
}
