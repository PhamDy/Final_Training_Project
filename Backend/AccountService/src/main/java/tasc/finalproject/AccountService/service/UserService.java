package tasc.finalproject.AccountService.service;

import tasc.finalproject.AccountService.model.RegisterRequest;

public interface UserService {
    int saveUser(RegisterRequest registerRequest);
}
