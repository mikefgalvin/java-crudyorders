package com.lambdaschool.ordersapp.controllers;

import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
