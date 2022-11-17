package in.ac.bits.orderservice.service.impl;

import com.google.gson.Gson;
import in.ac.bits.orderservice.domain.OrderDetails;
import in.ac.bits.orderservice.kafka.ProducerCreator;
import in.ac.bits.orderservice.model.Orders;
import in.ac.bits.orderservice.repository.OrderRepository;
import in.ac.bits.orderservice.service.OrdersService;
import in.ac.bits.orderservice.util.IKafkaConstants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrderRepository orderRepository;

    Producer<Long, String> producer = ProducerCreator.createProducer();

    @Override
    public String saveOrderInDB(OrderDetails orderDetails) {
        Orders orders = new Orders();
        orders.setCustomerAddress(orderDetails.getCustomerAddress());
        orders.setCustomerName(orderDetails.getCustomerName());
        orders.setCustomerPhoneNumber(orderDetails.getCustomerPhoneNumber());
        orders.setItemName(orderDetails.getItemName());
        orders.setSellerName(orderDetails.getSellerName());
        orders.setValue(orderDetails.getValue());

        try {
            orderRepository.save(orders);
            return "Success";
        } catch (Exception e) {
            return "Failed";
        }
    }

    @Override
    public String sendOrderToKafka(OrderDetails orderDetails) {
        Gson gson = new Gson();
        ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(IKafkaConstants.TOPIC_NAME, gson.toJson(orderDetails));
        try {
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Order sent to partition " + metadata.partition()
                    + " with offset " + metadata.offset());
            return "Success";
        }
        catch (ExecutionException e) {
            System.out.println("Error in sending record");
            System.out.println(e);
        }
        catch (InterruptedException e) {
            System.out.println("Error in sending record");
            System.out.println(e);
        }

        return "Failed";
    }


}
