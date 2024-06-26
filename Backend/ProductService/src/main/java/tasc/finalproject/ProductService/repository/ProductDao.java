package tasc.finalproject.ProductService.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.Page;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductDao implements DaoProductRepository{

    private final JdbcTemplate jdbcTemplate;

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
    public List<Product> getProductByCategoryId(long categoryId) {
        try {
            String sql = "SELECT * FROM products WHERE category_id = ? LIMIT 4 OFFSET 0";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), categoryId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public long saveProduct(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO products (category_id, product_name, avatar," +
                " img1, img2, img3, description, price," +
                " status, discount, quantity, created_by, updated_by)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
                    ps.setString(13, product.getUpdated_by());
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
                    "price = ?, status = ?, discount = ?, quantity = ?, category_id = ?, created_by = ?, updated_by = ? " +
                    "WHERE product_id = ?";
            jdbcTemplate.update(sql, product.getProduct_name(), product.getAvatar(), product.getImg1(), product.getImg2(), product.getImg3(),
                   product.getDescription(), product.getPrice(), product.getStatus().name(), product.getDiscount(), product.getQuantity(),
                    product.getCategory_id(), product.getCreated_by() ,product.getUpdated_by() ,productId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Page<ProductsResponse> listProduct(String name, List<Long> category, int size, int offset) {
        StringBuilder whereName = new StringBuilder();
        List<Object> paramsList = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            whereName.append(" WHERE product_name LIKE ?") ;
            paramsList.add("%" + name + "%");
        }

        if (category!=null && !category.isEmpty()) {
            if (whereName.length()>0){
                whereName.append(" AND ");
            } else {
                whereName.append(" WHERE ");
            }
            whereName.append("category_id IN (") ;
            whereName.append(String.join(",", Collections.nCopies(category.size(), "?")));
            whereName.append(")");
            paramsList.add(category);
        }
        paramsList.add(size);
        paramsList.add(offset);

        Object[] params = paramsList.toArray();
        log.info(String.valueOf(params));

        String rowCountSql = "SELECT count(1) AS row_count FROM products" + whereName;

        int totalElements;
        try {
            totalElements = jdbcTemplate.queryForObject(rowCountSql, paramsList.subList(0, paramsList.size() - 2).toArray(),
                    Integer.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to count products", e);
        }

        String sql = "SELECT p.product_id, p.product_name, p.category_id, " +
                "p.avatar, p.price, p.status, p.discount, p.quantity " +
                "FROM products p " + whereName +
                " LIMIT ? OFFSET ?";

        List<ProductsResponse> list;
        try {
            list = jdbcTemplate.query(
                    sql,
                    params,
                    BeanPropertyRowMapper.newInstance(ProductsResponse.class)
            );
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to query users", e);
        }

        int totalPage = (totalElements % size == 0) ? (totalElements / size) : (totalElements / size) + 1;
        log.info(sql);

        return new tasc.finalproject.ProductService.model.Page<ProductsResponse>(list, size, offset, totalPage, totalElements);
    }

    @Override
    public List<Product> listProduct(int size, int offset) {
        try {
            String sql = "SELECT * FROM products p LIMIT ? OFFSET ?";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), size, offset);
        }catch (DataAccessException e){
            return null;
        }
    }

    @Override
    public LocalDateTime lastRequestProduct() {
        try {
            String sql = "SELECT last_request FROM process p WHERE name = 'product_details'";
            return jdbcTemplate.queryForObject(sql, LocalDateTime.class);
        }catch (DataAccessException e){
            return null;
        }
    }

    @Override
    public void updateLastRequestProduct() {
        try {
            String sql = "UPDATE process SET last_request = ? WHERE name = 'product_details'";
             jdbcTemplate.update(sql, LocalDateTime.now());
        }catch (DataAccessException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> listProductUpdate(LocalDateTime lastRequest) {
        try {
            String sql = "SELECT * FROM products p" +
                    " WHERE updated_at > ?";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Product.class), lastRequest);
        }catch (DataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkProductUpdate() {
        try {
            String sql = "SELECT MAX(updated_at) FROM products p";
            LocalDateTime maxUpdatedAt = jdbcTemplate.queryForObject(sql, LocalDateTime.class);
            var lastRequest = lastRequestProduct();
            return maxUpdatedAt!=null && maxUpdatedAt.isAfter(lastRequest);
        }catch (DataAccessException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Long> listProductIdByCategory(long categoryId) {
        try {
            String sql = "SELECT product_id FROM products WHERE category_id = ?";
            return jdbcTemplate.queryForList(sql, Long.class, categoryId);
        }catch (DataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long categoryIdByProductId(long productId) {
        try {
            String sql = "SELECT category_id FROM products WHERE product_id = ?";
            return jdbcTemplate.queryForObject(sql, Long.class, productId);
        }catch (DataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }
}
