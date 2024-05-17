package tasc.finalproject.ProductService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private/api/v1/product")
public class ProductControllerPrivate {

    @PreAuthorize("hasRole('User') || hasRole('Admin')")
//    @Secured({"ROLE_User", "ROLE_Admin"})
    @GetMapping("/user")
    public ResponseEntity<String> testUser(){
        return new ResponseEntity<>("test Product private User", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Admin')")
//    @Secured("ROLE_Admin")
    @GetMapping("/admin")
    public ResponseEntity<String> testAdmin(){
        return new ResponseEntity<>("test Product private Admin", HttpStatus.OK);
    }
}
