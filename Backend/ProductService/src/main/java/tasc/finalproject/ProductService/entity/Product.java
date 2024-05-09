package tasc.finalproject.ProductService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity{
    private long productId;
    private long categoryId;
    private String productName;
    private String avatar;
    private String img1;
    private String img2;
    private String img3;
    private String desc;
    private double price;
    private ProductStatus status;
    private float discount;
    private long quantity;
}
