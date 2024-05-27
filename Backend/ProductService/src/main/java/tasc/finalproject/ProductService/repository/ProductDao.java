package tasc.finalproject.ProductService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
                " p.avatar, p.price, p.status, p.discount, p.quantity FROM products p";
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
    public long saveProduct(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO products (category_id, product_name, avatar," +
                " img1, img2, img3, description, price," +
                " status, discount, quantity, created_by)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"product_id"});
                    ps.setLong(1, product.getCategory_id());
                    ps.setString(2, product.getProduct_name());
                    ps.setString(3, product.getAvatar());
                    ps.setString(4, product.getImg1());
                    ps.setString(5, product.getImg2());
                    ps.setString(6, product.getImg3());
                    ps.setString(7, product.getDescription());
                    ps.setDouble(8, product.getPrice());
                    ps.setString(9, product.getStatus().name());
                    ps.setFloat(10, product.getDiscount());
                    ps.setLong(11, product.getQuantity());
                    ps.setString(12, product.getCreated_by());
                    return ps;
                },
                keyHolder
        );
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Some problems with getting the product id.");
        }
    }

    @Override
    public void editProduct(long productId, Product product) {
        try {
            String sql = "UPDATE products SET product_name = ?, avatar = ?, img1 = ?, img2 = ?, img3 = ?, description = ?, " +
                    "price = ?, status = ?, discount = ?, quantity = ?, category_id = ?, updated_by = ? " +
                    "WHERE product_id = ?";
            jdbcTemplate.update(sql, product.getProduct_name(), product.getAvatar(), product.getImg1(), product.getImg2(), product.getImg3(),
                   product.getDescription(), product.getPrice(), product.getStatus().name(), product.getDiscount(), product.getQuantity(),
                    product.getCategory_id(), product.getUpdated_by() ,productId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Page<ProductsResponse> listProduct(Pageable pageable) {
        String rowCountSql = "SELECT count(1) AS row_count " +
                "FROM products ";
        int total = jdbcTemplate.queryForObject(
                rowCountSql, Integer.class
        );

        String sql = "SELECT p.product_id, p.product_name, p.category_id, " +
                "p.avatar, p.price, p.status, p.discount, p.quantity " +
                "FROM products p " +
                "LIMIT ? OFFSET ?";

        List<ProductsResponse> list = jdbcTemplate.query(
                sql,
                new Object[]{pageable.getPageSize(), pageable.getOffset()},
                BeanPropertyRowMapper.newInstance(ProductsResponse.class)
        );

        return new PageImpl<>(list, pageable, total);
    }


}
