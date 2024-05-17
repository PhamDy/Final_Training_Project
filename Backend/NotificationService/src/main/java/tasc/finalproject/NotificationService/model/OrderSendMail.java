package tasc.finalproject.NotificationService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSendMail {
    private String first_name;
    private String last_name;
    private String address;
    private String city;
    private String email;
    private double priceDelivery;
    private double subtotal;
    private CartDto cartDto;
}
