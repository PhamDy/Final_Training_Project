package tasc.finalproject.ProductService.repository.rowmappers;

import org.springframework.jdbc.core.RowMapper;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.entity.ProductStatus;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductsResponse productsResponse = new ProductsResponse();
        productsResponse.setProductId(rs.getInt("product_id"));
        productsResponse.setProductName(rs.getString("product_name"));
        productsResponse.setAvatar(rs.getString("avartar_image_product"));
        productsResponse.setPrice(rs.getDouble("price"));
        productsResponse.setStatus(ProductStatus.valueOf(rs.getString("status")));

        return null;
    }
}
