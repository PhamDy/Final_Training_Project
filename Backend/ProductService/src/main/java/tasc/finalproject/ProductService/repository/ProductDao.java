package tasc.finalproject.ProductService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.exception.ProductNotFoundException;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao implements DaoProductRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProductsResponse> getProductAll() {
        String sql = "SELECT p.product_id, p.product_name, p.category_id," +
                " p.avatar, p.price, p.status, p.discount FROM products p";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ProductsResponse.class));
    }

    @Override
    public Product getProductById(long productId) {
        try {
            String sql = "SELECT * FROM products WHERE product_id = ?";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Product.class), productId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public long saveProduct(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO category(name, created_by) VALUES (?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"category_id"});
                    ps.setString(1, category.getName());
                    ps.setString(2, category.getCreated_by());
                    return ps;
                },
                keyHolder
        );

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Some problems with getting the category id.");
        }
    }


}
