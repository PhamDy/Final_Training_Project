package tasc.finalproject.DeliveryService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasc.finalproject.DeliveryService.entity.Delivery;
import tasc.finalproject.DeliveryService.repository.DaoDeliveryRepository;
import tasc.finalproject.DeliveryService.service.DeliveryService;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DaoDeliveryRepository deliveryRepository;

    @Override
    public List<Delivery> getAllDelivery() {
        return deliveryRepository.getAll();
    }
}
