package tasc.finalproject.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page <T>{
    List<T> data;
    int size;
    int offset;
    int totalPage;
    int totalElements;
}
