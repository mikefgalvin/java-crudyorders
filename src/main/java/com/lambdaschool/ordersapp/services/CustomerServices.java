package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.views.CustomerOrderCount;

import java.util.List;

public interface CustomerServices {

    List<Customer> findAllCustomers();
    Customer findCustomerById(long id);
    List<Customer> findByNameLike(String thename);
    List<CustomerOrderCount> getCustomerOrderCount();

    public Customer save(Customer customer);
}
