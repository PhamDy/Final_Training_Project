package tasc.finalproject.PaymentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasc.finalproject.PaymentService.entity.Payment;
import tasc.finalproject.PaymentService.service.PaymentService;

@RestController
@RequestMapping("/private/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/savePayment")
    public ResponseEntity<String> savePayment(@RequestBody Payment payment){
        long id = paymentService.save(payment);
        return new ResponseEntity<>("Save payment successfully!  " + id, HttpStatus.CREATED);
    }

}
