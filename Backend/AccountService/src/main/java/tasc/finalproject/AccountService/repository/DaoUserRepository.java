package tasc.finalproject.AccountService.repository;

import org.springframework.stereotype.Repository;
import tasc.finalproject.AccountService.entity.Users;

public interface DaoUserRepository {
    int saveUser(Users users);
}
