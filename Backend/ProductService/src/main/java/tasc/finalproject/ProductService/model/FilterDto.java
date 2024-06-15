package tasc.finalproject.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto implements Serializable {
    private String name;
    private List<Long> category;
}
