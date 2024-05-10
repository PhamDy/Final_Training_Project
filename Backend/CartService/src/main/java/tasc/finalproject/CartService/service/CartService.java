package tasc.finalproject.CartService.service;

import tasc.finalproject.CartService.model.CartDto;

public interface CartService {

    void addToCart(long productId, long quantity);

    CartDto showCart();

    int updateQuantityCartIt(long cartItemId, long quantity);

    int deleteCartItemById(long cartItemId);
}
