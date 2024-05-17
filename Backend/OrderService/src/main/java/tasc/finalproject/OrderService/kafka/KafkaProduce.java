package tasc.finalproject.OrderService.kafka;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import tasc.finalproject.OrderService.model.OrderSendMail;
import tasc.finalproject.OrderService.model.PaymentRequest;

@Service
public class KafkaProduce {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    public void sendMessagePaymentService(PaymentRequest paymentRequest){
        LOGGER.info(String.format("Message sent -> %s", paymentRequest.toString()));

        Message<PaymentRequest> message = MessageBuilder
                .withPayload(paymentRequest)
                .setHeader(KafkaHeaders.TOPIC, "payment")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendMessageEmail(OrderSendMail orderSendMail){
        LOGGER.info(String.format("Message sent -> %s", orderSendMail.toString()));

        Message<OrderSendMail> message = MessageBuilder
                .withPayload(orderSendMail)
                .setHeader(KafkaHeaders.TOPIC, "notification")
                .build();
        kafkaTemplate.send(message);
    }

}
