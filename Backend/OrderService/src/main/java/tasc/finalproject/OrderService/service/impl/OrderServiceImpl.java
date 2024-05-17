package tasc.finalproject.OrderService.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tasc.finalproject.CartService.model.CartDto;
import tasc.finalproject.CartService.model.CartItemResponse;
import tasc.finalproject.OrderService.entity.OrderStatus;
import tasc.finalproject.OrderService.entity.Orders;
import tasc.finalproject.OrderService.entity.OrdersDetails;
import tasc.finalproject.OrderService.kafka.KafkaProduce;
import tasc.finalproject.OrderService.model.OrderRequest;
import tasc.finalproject.OrderService.model.OrderSendMail;
import tasc.finalproject.OrderService.model.PaymentRequest;
import tasc.finalproject.OrderService.model.PaymentStatus;
import tasc.finalproject.OrderService.repository.DaoOrderRepository;
import tasc.finalproject.OrderService.service.OrderService;
import tasc.finalproject.OrderService.service.client.CartService;
import tasc.finalproject.OrderService.service.client.DeliveryService;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    public static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private DaoOrderRepository orderRepository;
    private RestTemplate restTemplate;
    private KafkaProduce kafkaProduce;
    private CartService cartService;
    private DeliveryService deliveryService;

    @Override
    public void placeOrder(OrderRequest orderRequest) {

        LOGGER.info(String.format("Invoking Cart service to fetch "));
        CartDto cartDto = restTemplate.getForObject(
                "http://CART-SERVICE/api/v1/private/cart/showCart",
                CartDto.class
        );

        LOGGER.info(String.format("Invoking Delivery service to fetch "));
        double priceDelivery = deliveryService.getPriceById(orderRequest.getDelivery_id()).getBody();

        Orders orders = new Orders();
        try {
            BeanUtils.copyProperties(orderRequest, orders);
            orders.setTotal_price(cartDto.getTotalPrice());
            orders.setStatus(OrderStatus.Pending);
            orders.setTotal_price(priceDelivery + cartDto.getTotalPrice());
            orders.setCreated_by("Customer");
        } catch (Exception e){
            e.printStackTrace();
        }

        long orderId = orderRepository.saveOrder(orders);
        LOGGER.info(String.format("Save order successfully by order Id: " + orderId));

        cartService.updateCartStatus(cartDto.getCartId());
        LOGGER.info(String.format("Change cart status Close by cart Id: " + cartDto.getCartId()));

        for (CartItemResponse c: cartDto.getCartItemResponseList()
             ) {
            OrdersDetails ordersDetails = new OrdersDetails();
            ordersDetails.setOrder_id(orderId);
            ordersDetails.setDiscount(c.getDiscount());
            ordersDetails.setPrice(c.getPrice());
            ordersDetails.setQuantity(c.getQuantity());
            ordersDetails.setProduct_id(c.getProductId());
            ordersDetails.setCreated_by("Customer");
            orderRepository.saveOrderDetails(ordersDetails);
        }
        LOGGER.info(String.format("Save list order details successfully by order Id: " + orderId));

        PaymentRequest paymentRequest = new PaymentRequest(orderId, "COD", PaymentStatus.Unpaid, orders.getTotal_price());
        kafkaProduce.sendMessagePaymentService(paymentRequest);
        LOGGER.info(String.format("Order placed and payment request sent to PaymentService"));

        OrderSendMail orderSendMail = OrderSendMail
                .builder()
                .first_name(orderRequest.getFirst_name())
                .last_name(orderRequest.getLast_name())
                .address(orderRequest.getAddress())
                .priceDelivery(priceDelivery)
                .subtotal(cartDto.getTotalPrice())
                .email(orderRequest.getEmail())
                .city(orderRequest.getCity())
                .cartDto(cartDto)
                .build();
        kafkaProduce.sendMessageEmail(orderSendMail);
        LOGGER.info(String.format("Send orderSendMail to NotificationService!"));

    }
}
