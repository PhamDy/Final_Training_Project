package tasc.finalproject.ProductService.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tasc.finalproject.ProductService.entity.Category;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryDao implements DaoCategoryRepository{

    private final JdbcTemplate jdbcTemplate;

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
    public long saveCategory(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO category(name, created_by, updated_by) VALUES (?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"category_id"});
                    ps.setString(1, category.getName());
                    ps.setString(2, category.getCreated_by());
                    ps.setString(3, category.getUpdated_by());
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

    @Override
    public void editCategoryById(long id, Category category) {
        try {
            String sql = "UPDATE category SET name = ?, created_by = ? ,updated_by = ? WHERE category_id = ?";
            jdbcTemplate.update(sql, category.getName(), category.getCreated_by() ,category.getUpdated_by(), id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int deleteCategoryById(long id) {
        String sql = "DELETE FROM category WHERE category_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Long> getCategoryId() {
        String sql = "SELECT category_id FROM category";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

}
