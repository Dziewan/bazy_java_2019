package wsi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wsi.model.Employee;
import wsi.model.Message;
import wsi.model.Order;
import wsi.model.Shipper;
import wsi.service.EmployeeRepo;
import wsi.service.MessageRepo;
import wsi.service.OrderRepo;
import wsi.service.ShipperRepo;

/**
 * Projektowanie API:
 * https://pages.apigee.com/rs/apigee/images/api-design-ebook-2012-03.pdf
 */

@RestController
@CrossOrigin
public class AppController {
    @Autowired MessageRepo messageRepo;
    @Autowired ShipperRepo shipperRepo;
    @Autowired EmployeeRepo employeeRepo;
    @Autowired OrderRepo orderRepo;

    @GetMapping(value = "/status")
    public String showStatus() {
        return "App running OK";
    }

    //Messages
    @GetMapping(value = "/msgs")
    public Iterable<Message> getMessages() {
        return messageRepo.findAll();
    }

    @PostMapping(value = "/msgs")
    public Message upsertMessage(@RequestBody Message message) {
        /*
         * Dostajemy JSON:
         * {"id":1,"title":"genesis","body":"lorem ipsum dolor sit amet"}
         */
        return messageRepo.save(message);
    }

    //// Shippers
    //C

    //R
    @GetMapping("/shippers")
    public Iterable<Shipper> getAllShippers(
            @RequestParam(value = "prefix", defaultValue = "") String prefix) {
        if (prefix.equals("")) return shipperRepo.findAll();
        else return shipperRepo.getByShippernameStartingWith(prefix);
    }

    //U

    //D

    //// Employees
    @GetMapping("/employees")
    public Iterable<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }


    //// Customers

    //dodac metode "getAllCustomers"


    //// Orders
    @GetMapping("/customers/{customerid}/orders")
    public Iterable<Order> getOrdersOfCustomer(@PathVariable(value = "customerid") Integer customerid) {
        return orderRepo.getByCustomerid(customerid);
    }

}
