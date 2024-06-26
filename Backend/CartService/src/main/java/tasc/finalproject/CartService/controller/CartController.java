package tasc.finalproject.CartService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tasc.finalproject.CartService.model.CartDto;
import tasc.finalproject.CartService.service.CartService;

@RestController
@RequestMapping("/private/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('User')")
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestParam(name = "productId") long productId,
                                            @RequestParam(name = "quantity") long quantity){
        cartService.addToCart(productId, quantity);
        return new ResponseEntity<>("Add to cart successfully!", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('User') || hasRole('Admin')")
    @GetMapping("/showCart")
    public ResponseEntity<CartDto> showCart(){
        return new ResponseEntity<>(cartService.showCart(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('User') || hasRole('Admin')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateQuantity(@PathVariable(name = "id") long cartItemId,
                                            @RequestParam(name = "quantity") long quantity){
        cartService.updateQuantityCartIt(cartItemId, quantity);
        return new ResponseEntity<>("Update quantity cart item successfully " + cartItemId, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('User') || hasRole('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable(name = "id") long cartItemId){
        cartService.deleteCartItemById(cartItemId);
        return new ResponseEntity<>("Delete cart item successfully " + cartItemId, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('User') || hasRole('Admin')")
    @PutMapping("/updateCartStatus")
    public ResponseEntity<String> updateCartStatus(@RequestParam long cartId){
        cartService.updateCartStatus(cartId);
        return new ResponseEntity<>("Update status cart successfully: " + cartId, HttpStatus.OK);
    }

}
