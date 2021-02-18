package com.lambdaschool.ordersapp.controllers;

import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> listAllOrders() {
        List<Order> myOrders = orderServices.findAllOrders();
        return new ResponseEntity<>(myOrders, HttpStatus.OK);
    }

    //    http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{orderId}", produces = "application/json")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        Order order = orderServices.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //    POST http://localhost:2019/orders/order/ - see order controller - adds a new order to an existing customer
    @PostMapping(value =  "/order", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order order) {
        order.setOrdnum(0);

        order = orderServices.save(order);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderNum}")
                .buildAndExpand(order.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

//    PUT http://localhost:2019/orders/order/{orderNum} - see order controller  - completely replaces the given order record
    @PutMapping(value = "/order/{orderNum}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> replaceOrder(@Valid @RequestBody Order order, @PathVariable long orderNum) {
        order.setOrdnum(orderNum);
        Order newOrder = orderServices.save(order);

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }
//    DELETE http://localhost:2019/orders/order/{orderNum} - see order controller - deletes the given order
    @DeleteMapping(value = "/order/{orderNum}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> deleteOrderById(@PathVariable long orderNum) {
        orderServices.deleteByOrderId(orderNum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
