package tasc.finalproject.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tasc.finalproject.ProductService.entity.ProductStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponse {
    private long productId;
    private long categoryId;
    private String productName;
    private String avatar;
    private double price;
    private ProductStatus status;
    private float discount;
    private long quantity;
}
