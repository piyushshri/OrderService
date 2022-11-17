package in.ac.bits.orderservice.controller;

import in.ac.bits.orderservice.domain.OrderDetails;
import in.ac.bits.orderservice.service.OrdersService;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderDetailsController {

    @Autowired
    OrdersService ordersservice;

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody OrderDetails orderDetails) {
        try{
            ordersservice.saveOrderInDB(orderDetails);
            ordersservice.sendOrderToKafka(orderDetails);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
