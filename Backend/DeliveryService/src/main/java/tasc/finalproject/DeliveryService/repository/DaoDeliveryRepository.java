package tasc.finalproject.DeliveryService.repository;

import tasc.finalproject.DeliveryService.entity.Delivery;

import java.util.List;

public interface DaoDeliveryRepository {

    List<Delivery> getAll();

    double getPrice(long deliveryId);

}
