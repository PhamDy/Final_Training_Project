package tasc.finalproject.OrderService.repository;

import tasc.finalproject.OrderService.entity.Orders;
import tasc.finalproject.OrderService.entity.OrdersDetails;
import tasc.finalproject.OrderService.model.OrderRequest;

public interface DaoOrderRepository {

    long saveOrder(Orders orders);

    long saveOrderDetails(OrdersDetails ordersDetails);

}
