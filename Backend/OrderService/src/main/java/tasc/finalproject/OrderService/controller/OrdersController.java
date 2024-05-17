package tasc.finalproject.OrderService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasc.finalproject.OrderService.model.OrderRequest;
import tasc.finalproject.OrderService.service.OrderService;

@RestController
@RequestMapping("/private/api/v1/order")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return new ResponseEntity<>("Place order successfully!", HttpStatus.CREATED);
    }

}
