package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.repositories.CustomerRepository;
import com.lambdaschool.ordersapp.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> list = new ArrayList<>();
        customerRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findCustomerById(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
        return customer;
    }

    @Override
    public List<Customer> findByNameLike(String thename) {
        List<Customer> list = customerRepository.findCustomerByCustnameContainingIgnoringCase(thename);
        return list;
    }

    @Override
    public List<CustomerOrderCount> getCustomerOrderCount() {
        List<CustomerOrderCount> list = customerRepository.getCustomerOrderCount();
        return list;
    }

    @Transactional
    @Override
    public Customer save(Customer customer) { return customerRepository.save(customer); }
}
