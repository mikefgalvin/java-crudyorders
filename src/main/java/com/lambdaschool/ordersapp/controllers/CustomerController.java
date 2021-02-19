package com.lambdaschool.ordersapp.controllers;


import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.services.CustomerServices;
import com.lambdaschool.ordersapp.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.Valid;
import java.net.URI;
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


//   POST http://localhost:2019/customers/customer - Adds a new customer including any new orders
    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer customer) {
        customer.setCustcode(0);

        customer = customerServices.save(customer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(customer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(customer, responseHeaders, HttpStatus.CREATED);
    }
//   PUT http://localhost:2019/customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
    @PutMapping(value = "/customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> replaceCustomer(@Valid @RequestBody Customer customer, @PathVariable long custcode) {
        customer.setCustcode(custcode);
        Customer newCustomer = customerServices.save(customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }
//   PATCH http://localhost:2019/customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.
    @PatchMapping(value = "/customer/{custcode}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable long custcode) {
        Customer newCustomer = customerServices.update(custcode, customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }
//   DELETE http://localhost:2019/customers/customer/{custcode} - Deletes the given customer including any associated orders
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode) {
        customerServices.deleteById(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    POST http://localhost:2019/orders/order/ - see order controller - adds a new order to an existing customer
//    PUT http://localhost:2019/orders/order/{custcode} - see order controller  - completely replaces the given order record
//    DELETE http://localhost:2019/orders/order/{custcode} - see order controller - deletes the given order

}
