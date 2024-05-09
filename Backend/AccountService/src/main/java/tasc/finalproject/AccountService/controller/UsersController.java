package tasc.finalproject.AccountService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasc.finalproject.AccountService.model.RegisterRequest;
import tasc.finalproject.AccountService.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(userService.saveUser(registerRequest), HttpStatus.CREATED);
    }
}
