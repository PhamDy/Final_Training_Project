package tasc.finalproject.PaymentService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasc.finalproject.PaymentService.entity.Payment;
import tasc.finalproject.PaymentService.repository.DaoPaymentRepository;
import tasc.finalproject.PaymentService.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private DaoPaymentRepository paymentRepository;

    @Override
    public long save(Payment payment) {
        try {
            long id = paymentRepository.save(payment);
            LOGGER.info(String.format("Save payment successfully by Id: ", id));
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        return 0;
    }
}
