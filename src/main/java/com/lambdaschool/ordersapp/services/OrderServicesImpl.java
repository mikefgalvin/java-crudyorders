package com.lambdaschool.ordersapp.services;


import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.models.Payment;
import com.lambdaschool.ordersapp.repositories.CustomerRepository;
import com.lambdaschool.ordersapp.repositories.OrderRepository;
import com.lambdaschool.ordersapp.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

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

        newOrder.setCustomer(customerRepository.findById(tempOrder.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + tempOrder.getCustomer()
                        .getCustcode() + " Not Found")));

        newOrder.setOrderdescription(tempOrder.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : tempOrder.getPayments()) { //Payments Loop
            Payment newPayment = new Payment();
            Optional<Payment> optionalPayment = paymentRepository.findById(p.getPaymentid());
            if (optionalPayment.isPresent()) {
                newPayment = optionalPayment.get();
            } else {
                newPayment.setType(p.getType());
                newOrder.getPayments().add(newPayment);
            }
        }

        return orderRepository.save(newOrder);
    }

    @Transactional
    @Override
    public void deleteByOrderId(long id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }
    }

    @Transactional
    @Override
    public void deleteAllOrders() {
    }
}
