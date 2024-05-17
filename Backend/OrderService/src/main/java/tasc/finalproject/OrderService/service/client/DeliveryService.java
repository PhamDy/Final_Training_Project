package tasc.finalproject.OrderService.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "DELIVERY-SERVICE/api/v1/public/delivery")
public interface DeliveryService {

    @GetMapping("/price")
    public ResponseEntity<Double> getPriceById(@RequestParam(name = "deliveryId") long deliveryId);

}