package com.lambdaschool.ordersapp.services;


import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAllOrders() {
        List<Order> list = new ArrayList<>();
        orderRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Order findOrderById(long id) throws EntityNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found"));
    }


    @Transactional
    @Override
    public Order save(Order order) { return orderRepository.save(order); }
}
