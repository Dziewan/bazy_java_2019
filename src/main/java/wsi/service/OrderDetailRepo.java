package wsi.service;

import org.springframework.data.repository.CrudRepository;
import wsi.model.Order;
import wsi.model.OrderDetail;

public interface OrderDetailRepo extends CrudRepository<Order,Integer> {
    //metoda dająca wszystkie orderDetail dla danego orderId
    Iterable<OrderDetail> getByOrderid(Integer orderid);
}
