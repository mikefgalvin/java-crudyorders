package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Order;

import java.util.List;

public interface OrderServices {

    List<Order> findAllOrders();

    Order findOrderById(long id);


    public Order save(Order order);
}
