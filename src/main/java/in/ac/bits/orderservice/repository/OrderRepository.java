package in.ac.bits.orderservice.repository;

import in.ac.bits.orderservice.model.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders, Integer> {
}
