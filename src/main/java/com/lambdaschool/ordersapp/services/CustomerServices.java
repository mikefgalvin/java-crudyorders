package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.views.CustomerOrderCount;

import java.util.List;

public interface CustomerServices {

    List<Customer> findAllCustomers();
    Customer findCustomerById(long id);
    List<Customer> findByNameLike(String thename);
    List<CustomerOrderCount> getCustomerOrderCount();

    Customer save(Customer customer);
    Customer update(long id, Customer customer);
    void deleteById(long id);
    void deleteAll();
}
