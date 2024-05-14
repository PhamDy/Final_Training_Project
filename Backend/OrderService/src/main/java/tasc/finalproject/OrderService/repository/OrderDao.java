package tasc.finalproject.OrderService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tasc.finalproject.OrderService.entity.Orders;
import tasc.finalproject.OrderService.entity.OrdersDetails;
import tasc.finalproject.OrderService.model.OrderRequest;

import java.sql.PreparedStatement;

@Repository
public class OrderDao implements DaoOrderRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long saveOrder(Orders orders) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO orders(first_name, last_name, country, city, address, " +
                "optional, email, phone, note, code," +
                " total_price, user_id, delivery_id, status, created_by)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"order_id"});
                    ps.setString(1, orders.getFirst_name());
                    ps.setString(2, orders.getLast_name());
                    ps.setString(3, orders.getCountry());
                    ps.setString(4, orders.getCity());
                    ps.setString(5, orders.getAddress());
                    ps.setString(6, orders.getOptional());
                    ps.setString(7, orders.getEmail());
                    ps.setString(8, orders.getPhone());
                    ps.setString(9, orders.getNote());
                    ps.setString(10, orders.getCode());
                    ps.setDouble(11, orders.getTotal_price());
                    ps.setLong(12, orders.getUser_id());
                    ps.setLong(13, orders.getDelivery_id());
                    ps.setString(14, orders.getStatus().name());
                    ps.setString(15, orders.getCreated_by());
                    return ps;
                },
                keyHolder
        );
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Failed to retrieve Order ID after insert.");
        }
    }

    @Override
    public long saveOrderDetails(OrdersDetails ordersDetails) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO order_details(quantity, price, discount, order_id, product_id, created_by) " +
                " VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"order_detail_id"});
                    ps.setLong(1, ordersDetails.getQuantity());
                    ps.setDouble(2, ordersDetails.getPrice());
                    ps.setDouble(3, ordersDetails.getDiscount());
                    ps.setLong(4, ordersDetails.getOrder_id());
                    ps.setLong(5, ordersDetails.getProduct_id());
                    ps.setString(6, ordersDetails.getCreated_by());
                    return ps;
                },
                keyHolder
        );
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Failed to retrieve Order Details ID after insert.");
        }
    }
}
