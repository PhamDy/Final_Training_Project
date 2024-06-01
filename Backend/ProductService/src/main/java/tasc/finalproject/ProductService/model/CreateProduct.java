package tasc.finalproject.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import tasc.finalproject.ProductService.entity.ProductStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {
    private long category_id;
    private String product_name;
    private String description;
    private double price;
    private float discount;
    private long quantity;
    private String created_by;
    private String updated_by;
}
