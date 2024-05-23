package tasc.finalproject.ProductService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CategoryDao implements DaoCategoryRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> getCategory() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Category.class));
    }

    @Override
    public Category getCategoryById(long categoryId) {
        try {
            String sql = "SELECT * FROM category WHERE category_id = ?";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Category.class), categoryId);
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
