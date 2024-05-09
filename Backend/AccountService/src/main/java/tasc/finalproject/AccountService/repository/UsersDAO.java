package tasc.finalproject.AccountService.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tasc.finalproject.AccountService.entity.Users;
import tasc.finalproject.AccountService.model.RegisterRequest;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class UsersDAO implements DaoUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


//    public UsersDAO(JdbcTemplate jdbcTemplate){
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Override
    public int saveUser(Users users) {

        String sql = "INSERT INTO users(email, password, otp,otp_generated_time,created_by)" +
                " VALUES (?,?,?,?,?)";

        return jdbcTemplate.update(sql, users.getEmail(), users.getPassword(), users.getOtp(), LocalDateTime.now(), "Customer");
    }
}
