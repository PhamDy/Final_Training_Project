package tasc.finalproject.PaymentService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tasc.finalproject.PaymentService.entity.Payment;

import java.sql.PreparedStatement;

@Repository
public class PaymentDao implements DaoPaymentRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Payment payment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO payment(order_id, payment_method, status, amount, created_by) " +
                " VALUES (?,?,?,?,?)";

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"payment_id"});
                    ps.setLong(1, payment.getOrder_id());
                    ps.setString(2, payment.getPayment_method());
                    ps.setString(3, payment.getStatus().name());
                    ps.setDouble(4, payment.getAmount());
                    ps.setString(5, payment.getCreated_by());
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
}
