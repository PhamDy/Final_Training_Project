package tasc.finalproject.DeliveryService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery extends BaseEntity{
    private long delivery_id;
    private String name;
    private double price;
    private String img;
}
