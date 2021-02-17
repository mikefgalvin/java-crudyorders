package com.lambdaschool.ordersapp.repositories;

import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.views.CustomerOrderCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findCustomerByCustnameContainingIgnoringCase(String likename);

    @Query(value = "SELECT c.custname, COUNT(o.ordnum) as numoforders " +
            "FROM customers c LEFT JOIN orders o " +
            "ON o.custcode = c.custcode " +
            "GROUP BY c.custname",
            nativeQuery = true)
    List<CustomerOrderCount> getCustomerOrderCount();
}
