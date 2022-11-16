package in.ac.bits.orderservice.service;

import in.ac.bits.orderservice.domain.OrderDetails;

public interface OrdersService {
    String saveOrderInDB(OrderDetails orderDetails);

    String sendOrderToKafka(OrderDetails orderDetails);
}
