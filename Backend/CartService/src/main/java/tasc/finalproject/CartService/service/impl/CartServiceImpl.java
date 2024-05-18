package tasc.finalproject.CartService.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tasc.finalproject.CartService.entity.Cart;
import tasc.finalproject.CartService.entity.CartItems;
import tasc.finalproject.CartService.entity.CartStatus;
import tasc.finalproject.CartService.exception.CartNotFoundException;
import tasc.finalproject.CartService.model.CartDto;
import tasc.finalproject.CartService.model.CartItemResponse;
import tasc.finalproject.CartService.repository.DaoCartRepository;
import tasc.finalproject.CartService.service.CartService;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.exception.ProductNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CartServiceImpl.class);

    private DaoCartRepository cartRepository;
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public void addToCart(long productId, long quantity) {

        int userId = 1;

        LOGGER.info(String.format("Invoking Product service to fetch the product for id: {}", productId));

        Product product = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/public/api/v1/product/" + productId,
                Product.class
        );

        LOGGER.info("xong");
        if (product==null){
            throw new ProductNotFoundException("Product by " + productId + " not found!");
        }

        long cartId = cartRepository.findByUserIdInCartOpen(userId);
        if (cartId==0){
            Cart cart = new Cart();
            cart.setUser_id(userId);
            cart.setStatus(CartStatus.Open);
            cart.setCreated_by("Customer");
            cartId = cartRepository.saveCart(cart);
            LOGGER.info(String.format("Save cart id " + cartId + " successfully!"));
        }

        CartItems cartItems = cartRepository.findByCartItByCartItemAndProductId(cartId, productId);
        if (cartItems==null){
            cartItems = new CartItems();
            cartItems.setCart_id(cartId);
            cartItems.setProduct_id(productId);
            cartItems.setQuantity(quantity);
            cartItems.setCreated_by("Customer");
            cartItems.setPrice(product.getPrice());
            cartItems.setDiscount(product.getDiscount());
            LOGGER.info(String.format("Save cart items id successfully!"));
            cartRepository.saveCartItem(cartItems);
        } else {
            long quantityNew = cartItems.getQuantity() + quantity;
            cartRepository.updateQuantityInCartIt(cartItems.getCart_item_id(), quantityNew);
            LOGGER.info(String.format("Update quantity cart items id successfully: {}", quantityNew ));
        }
    }

    @Override
    public CartDto showCart() {
        long userId = 1;

        long cartId = cartRepository.findByUserIdInCartOpen(userId);
        if (cartId==0){
            return CartDto
                    .builder()
                    .userId(userId)
                    .totalPrice(0.00)
                    .cartItemResponseList(null)
                    .build();
        }

        var cartItems = cartRepository.getCartItemByCartId(cartId);
        if (cartItems==null){
            throw new CartNotFoundException("Cart Item by " + cartId + " not found!");
        }

        double total = 0;
        for (CartItems c: cartItems
             ) {
            if (c.getDiscount()<=0){
                total += c.getPrice()*c.getQuantity();
            } else {
                total += (c.getPrice() - (c.getPrice() * c.getDiscount() / 100))*c.getQuantity();
            }
        }
        var cartItemResponseList = convertToListCartItem(cartItems);
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cartId);
        cartDto.setUserId(userId);
        cartDto.setTotalPrice(total);
        cartDto.setCartItemResponseList(cartItemResponseList);
        LOGGER.info(String.format("Show cart successfully!"));
        return cartDto;
    }

    @Override
    @Transactional
    public int updateQuantityCartIt(long cartItemId, long quantity) {

        var cartItem = cartRepository.findByCartItemsId(cartItemId);
        if (cartItem==null){
            throw new CartNotFoundException("Cart Item " + cartItemId + " not found!");
        }
        return cartRepository.updateQuantityInCartIt(cartItemId, quantity);
    }

    @Override
    @Transactional
    public int deleteCartItemById(long cartItemId) {
        var cartItem = cartRepository.findByCartItemsId(cartItemId);
        if (cartItem==null){
            throw new CartNotFoundException("Cart Item " + cartItemId + " not found!");
        }
        return cartRepository.deleteCartItemById(cartItemId);
    }

    public List<CartItemResponse> convertToListCartItem(List<CartItems> cartItemsList){
        List<CartItemResponse> cartItemResponses = cartItemsList.stream()
                .map(cartItems -> {
                    Product product = restTemplate.getForObject(
                            "http://PRODUCT-SERVICE/public/api/v1/product/" + cartItems.getProduct_id(),
                            Product.class
                    );
                    CartItemResponse cartItemResponse = new CartItemResponse();
                    cartItemResponse.setCartItemId(cartItems.getCart_item_id());
                    cartItemResponse.setProductId(cartItems.getProduct_id());
                    cartItemResponse.setProductName(product.getProductName());
                    cartItemResponse.setImg(product.getAvatar());
                    cartItemResponse.setDiscount(product.getDiscount());
                    cartItemResponse.setPrice(product.getPrice());
                    cartItemResponse.setQuantity(cartItems.getQuantity());
                    return cartItemResponse;
                }).collect(Collectors.toList());
        return cartItemResponses;
    }

    @Override
    public int updateCartStatus(long cartId) {
        return cartRepository.updateCartStatus(cartId);
    }
}
