package tasc.finalproject.AccountService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasc.finalproject.AccountService.entity.Users;
import tasc.finalproject.AccountService.model.RegisterRequest;
import tasc.finalproject.AccountService.repository.DaoUserRepository;
import tasc.finalproject.AccountService.service.UserService;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DaoUserRepository userRepository;

    public int saveUser(RegisterRequest registerRequest){
        Users user = Users
                .builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .otp("12345")
                .otp_generated_time(LocalDateTime.now())
                .enabled(false)
                .build();
        user.setCreated_by("Customer");
        return userRepository.saveUser(user);
    }

}
