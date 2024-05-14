package tasc.finalproject.DeliveryService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tasc.finalproject.DeliveryService.entity.Delivery;

import java.util.List;

@Repository
public class DeliveryDao implements DaoDeliveryRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Delivery> getAll() {
        try {
            String sql = "SELECT * FROM delivery";
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Delivery.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public double getPrice(long deliveryId) {
        try {
            String sql = "SELECT price FROM delivery WHERE delivery_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{deliveryId}, Double.class );
        }catch (EmptyResultDataAccessException e){
            return 0;
        }
    }
}
