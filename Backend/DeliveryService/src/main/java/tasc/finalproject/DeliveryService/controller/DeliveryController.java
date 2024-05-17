package tasc.finalproject.DeliveryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tasc.finalproject.DeliveryService.entity.Delivery;
import tasc.finalproject.DeliveryService.service.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/public/api/v1/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/")
    public ResponseEntity<List<Delivery>> getAllDelivery(){
        return new ResponseEntity<>(deliveryService.getAllDelivery(), HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<Double> getPriceById(@RequestParam(name = "deliveryId") long deliveryId){
        return new ResponseEntity<>(deliveryService.getPriceById(deliveryId), HttpStatus.OK);
    }

}
