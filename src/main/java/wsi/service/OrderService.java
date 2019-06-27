package wsi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wsi.model.*;

@Service
public class OrderService {
    @Autowired OrderRepo orderRepo;
    @Autowired CustomerRepo customerRepo;
    @Autowired EmployeeRepo employeeRepo;
    @Autowired OrderDetailRepo orderDetailRepo;
    @Autowired ProductRepo productRepo;


    public OrderExpanded resolveDetails(Integer orderid) {
        //Uwaga: w nowym JPA nie ma .findOne, tylko .findById zwracajace "Optional"
        Order order = orderRepo.findById(orderid).get();
        Customer customer = customerRepo.findById(order.getCustomerid()).get();
        Employee employee = employeeRepo.findById(order.getEmployeeid()).get();

        Double total = 0.;
        //teraz policzyc total...
        //1) wyciagnać liste (iterable) wszystkich orderDetail
        //2) dla każdego, wziąć productid, i sprawdzić w repo jaki jest jego koszt jednostkowy
        //3) do sumy dodać orederDetail.quantity * product.price
        
        /*
		Proponuje działać na BigDecimalach i można pobawić się streamami :) przykład wyliczenia total
		
				BigDecimal total = orderDetailRepo.findByOrderId(orderId)
				.stream()
				.map(detail -> Optional
						.ofNullable(productRepo.findById(detail.getProductId())
								.orElseGet(Product::new)
								.getPrice())
						.orElse(new BigDecimal(0.0))
						.multiply(detail.getQuantity()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
                
                MD

		 */


        for(OrderDetail detail : orderDetailRepo.getByOrderid(orderid)){
            int productId = detail.getProductid();
            total += productRepo.findById(productId).get().getPrice() * detail.getQuantity();
        }

        OrderExpanded expanded = new OrderExpanded(
                order,
                customer.getCustomername(),
                employee.getLastname(),
                total);
        return expanded;
    }

}
