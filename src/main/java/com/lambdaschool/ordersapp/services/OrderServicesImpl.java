package com.lambdaschool.ordersapp.services;


import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.models.Payment;
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
    public Order save(Order tempOrder)
    {
        Order newOrder = new Order();

        if (tempOrder.getOrdnum() != 0) {
            orderRepository.findById(tempOrder.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + tempOrder.getOrdnum() + " Not Found!"));

            newOrder.setOrdnum(tempOrder.getOrdnum());
        }

        newOrder.setOrdamount(tempOrder.getOrdamount());
        newOrder.setAdvanceamount(tempOrder.getAdvanceamount());
        newOrder.setCustomer(tempOrder.getCustomer());
        newOrder.setOrderdescription(tempOrder.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : newOrder.getPayments()) { //Payments Loop
            Payment newPayment = new Payment();
            newPayment.setType(p.getType());

            newOrder.getPayments().add(newPayment);
        }

        return orderRepository.save(newOrder);
    }
}
