package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderServices {

    List<Order> findAllOrders();

    Order findOrderById(long id);


    Order save(Order order);
    void deleteByOrderId(long id);
    void deleteAllOrders();
}
