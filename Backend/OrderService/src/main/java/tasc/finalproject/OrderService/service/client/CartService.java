package tasc.finalproject.OrderService.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CART-SERVICE/private/api/v1/cart")
public interface CartService {

    @PutMapping("/updateCartStatus")
    public ResponseEntity<String> updateCartStatus(@RequestParam long cartId);

}

