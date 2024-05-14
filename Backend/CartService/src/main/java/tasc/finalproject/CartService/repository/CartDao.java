package tasc.finalproject.CartService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tasc.finalproject.CartService.entity.Cart;
import tasc.finalproject.CartService.entity.CartItems;
import tasc.finalproject.CartService.entity.CartStatus;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


@Repository
public class CartDao implements DaoCartRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long saveCart(Cart cart) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO carts(user_id, status, created_by) VALUES (?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"cart_id"});
                    ps.setLong(1, cart.getUser_id());
                    ps.setString(2, String.valueOf(cart.getStatus()));
                    ps.setString(3, cart.getCreated_by());
                    return ps;
                },
                keyHolder
        );

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Some problems with getting the cart id.");
        }
    }

    @Override
    public long saveCartItem(CartItems cartItems) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO cart_item(product_id, cart_id, price, quantity, discount, created_by) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"cart_item_id"});
                    ps.setLong(1, cartItems.getProduct_id());
                    ps.setLong(2, cartItems.getCart_id());
                    ps.setDouble(3, cartItems.getPrice());
                    ps.setLong(4, cartItems.getQuantity());
                    ps.setDouble(5, cartItems.getDiscount());
                    ps.setString(6, cartItems.getCreated_by());
                    return ps;
                },
                keyHolder
        );

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            // Phù hợp với cách xử lý lỗi của bạn
            throw new RuntimeException("Failed to retrieve Cart Item ID after insert.");
        }
    }
    @Override
    public Cart findByCartIdOpen(long cartId) {
        try {
            String sql = "SELECT * FROM carts WHERE cart_id = ? AND status = ?";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Cart.class), cartId, CartStatus.Open.name());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public CartItems findByCartItemsId(long cartItemId) {
        try {
            String sql = "SELECT * FROM cart_item WHERE cart_item_id = ?";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(CartItems.class), cartItemId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public CartItems findByCartItByCartItemAndProductId(long cartId , long productId) {
        try {
            String sql = "SELECT * FROM cart_item WHERE product_id = ? AND cart_id = ?";
            return jdbcTemplate.queryForObject(sql,BeanPropertyRowMapper.newInstance(CartItems.class), productId, cartId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public long findByUserIdInCartOpen(long userId) {
        try {
            String sql = "SELECT cart_id FROM carts WHERE user_id = ? AND status = ?";
            return jdbcTemplate.queryForObject(sql, Long.class, userId, CartStatus.Open.name());
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public List<CartItems> getCartItemByCartId(long cartId) {
        try {
            String sql = "SELECT * FROM cart_item WHERE cart_id = ?";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(CartItems.class), cartId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int updateQuantityInCartIt(long cartItemId, long quantity) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_item_id = ?";
        return jdbcTemplate.update(sql, quantity, cartItemId);
    }

    @Override
    public int deleteCartItemById(long cartItemId) {
        String sql = "DELETE FROM cart_item WHERE cart_item_id = ?";
        return jdbcTemplate.update(sql, cartItemId);
    }
}
