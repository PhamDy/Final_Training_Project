package tasc.finalproject.PaymentService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tasc.finalproject.PaymentService.entity.Payment;
import tasc.finalproject.PaymentService.model.PaymentRequest;
import tasc.finalproject.PaymentService.repository.DaoPaymentRepository;

@Service
public class KafkaConsumer {

    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private DaoPaymentRepository paymentRepository;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(PaymentRequest paymentRequest){
        LOGGER.info(String.format("Event message recieved -> %s", paymentRequest.toString()));

        try {
            Payment payment = new Payment();
            BeanUtils.copyProperties(paymentRequest, payment);
            payment.setCreated_by("Customer");
            long id = paymentRepository.save(payment);
            LOGGER.info(String.format("Save Payment successfully! ", id));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
