package com.lambdaschool.ordersapp.controllers;


import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.services.CustomerServices;
import com.lambdaschool.ordersapp.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {


    @Autowired
    private CustomerServices customerServices;

//    http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers()
    {
        List<Customer> myCustomers = customerServices.findAllCustomers();
        return new ResponseEntity<>(myCustomers, HttpStatus.OK);
    }

//    http://localhost:2019/customers/customer/7
//    http://localhost:2019/customers/customer/77
    @GetMapping(value = "/customer/{custid}", produces = "application/json")
    public ResponseEntity<?> getCustomerById(@PathVariable Long custid) {
        Customer customer = customerServices.findCustomerById(custid);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

//    http://localhost:2019/customers/namelike/mes
//    http://localhost:2019/customers/namelike/cin
    @GetMapping(value = "/namelike/{restname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String restname) {
        List<Customer> rtnList = customerServices.findByNameLike(restname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //    http://localhost:2019/customers/orders/count
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getCustomerOrderCount() {
        List<CustomerOrderCount> myList = customerServices.getCustomerOrderCount();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

//    http://localhost:2019/agents/agent/9 - see agent controller
//    http://localhost:2019/orders/order/7 - see order controller



}
