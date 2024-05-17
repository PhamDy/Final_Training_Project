package tasc.finalproject.NotificationService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tasc.finalproject.NotificationService.model.OrderSendMail;
import tasc.finalproject.NotificationService.service.NotificationService;

@Service
public class KafkaConsumer {
    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(
            topics = "notification",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderSendMail orderSendMail){
        LOGGER.info(String.format("Event message recieved -> %s", orderSendMail.toString()));
        try {
            notificationService.sendMailOrder(orderSendMail);
            LOGGER.info(String.format("Send Email successfully! ", orderSendMail.getEmail()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
