package tasc.finalproject.CartService.repository;

import tasc.finalproject.CartService.entity.Cart;
import tasc.finalproject.CartService.entity.CartItems;
import tasc.finalproject.CartService.entity.CartStatus;

import java.util.List;

public interface DaoCartRepository {

    long saveCart(Cart cart);

    long saveCartItem(CartItems cartItems);

    Cart findByCartIdOpen(long cartId);

    CartItems findByCartItemsId(long cartItemId);

    CartItems findByCartItByCartIdAndProductId(long cartId ,long productId);

    List<CartItems> getCartItemByCartId(long cartId);

    long findByUserIdInCartOpen(long userId);

    int updateQuantityInCartIt(long cartItemId, long quantity);

    int deleteCartItemById(long cartItemId);

}
