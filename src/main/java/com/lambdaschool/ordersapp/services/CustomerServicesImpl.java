package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Agent;
import com.lambdaschool.ordersapp.models.Customer;
import com.lambdaschool.ordersapp.models.Order;
import com.lambdaschool.ordersapp.models.Payment;
import com.lambdaschool.ordersapp.repositories.AgentRepository;
import com.lambdaschool.ordersapp.repositories.CustomerRepository;
import com.lambdaschool.ordersapp.repositories.OrderRepository;
import com.lambdaschool.ordersapp.views.CustomerOrderCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service(value = "customerServices")
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AgentRepository agentRepository;

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
    public Customer save(Customer tempCustomer)
    {
        Customer newCustomer = new Customer();

        //POST OR PUT
        if (tempCustomer.getCustcode() != 0) {
            customerRepository.findById(tempCustomer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + tempCustomer.getCustcode() + " Not Found!"));

            newCustomer.setCustcode(tempCustomer.getCustcode());
        }

        newCustomer.setCustname(tempCustomer.getCustname());
        newCustomer.setCustcity(tempCustomer.getCustcity());
        newCustomer.setWorkingarea(tempCustomer.getWorkingarea());
        newCustomer.setCustcountry(tempCustomer.getCustcountry());
        newCustomer.setGrade(tempCustomer.getGrade());
        newCustomer.setOpeningamt(tempCustomer.getOpeningamt());
        newCustomer.setReceiveamt(tempCustomer.getReceiveamt());
        newCustomer.setPaymentamt(tempCustomer.getPaymentamt());
        newCustomer.setOutstandingamt(tempCustomer.getOutstandingamt());
        newCustomer.setPhone(tempCustomer.getPhone());

//        newCustomer.setAgent(tempCustomer.getAgent()); //???
        newCustomer.setAgent(agentRepository.findById(tempCustomer.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + tempCustomer.getAgent()
                        .getAgentcode() + " Not Found")));

        newCustomer.getOrders().clear(); //precaution
        for (Order o : tempCustomer.getOrders()) { //Orders Loop
            Order newOrder = new Order();
            Optional <Order> optionalOrder = orderRepository.findById(o.getOrdnum());
            if (optionalOrder.isPresent()) {
                newOrder = optionalOrder.get();
            } else {
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrderdescription(o.getOrderdescription());
                newOrder.setCustomer(newCustomer); //put this in
                for (Payment p : newOrder.getPayments()) { //Payments Loop
                    Payment newPayment = new Payment();
                    newPayment.setType(p.getType());

                    newOrder.getPayments().add(newPayment);
                }
            }

            newCustomer.getOrders().add(newOrder);
        }

        return customerRepository.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(long id, Customer tempCustomer)
    {
        Customer updateCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not Found!"));



       if (tempCustomer.getCustname() != null) { updateCustomer.setCustname(tempCustomer.getCustname()); }
       if (tempCustomer.getCustcity() != null) { updateCustomer.setCustcity(tempCustomer.getCustcity()); }
       if (tempCustomer.getWorkingarea() != null) { updateCustomer.setWorkingarea(tempCustomer.getWorkingarea()); }
       if (tempCustomer.getCustcountry() != null) { updateCustomer.setCustcountry(tempCustomer.getCustcountry()); }
       if (tempCustomer.getGrade() != null) { updateCustomer.setGrade(tempCustomer.getGrade()); }
       if (tempCustomer.valueforopeningamt) { updateCustomer.setOpeningamt(tempCustomer.getOpeningamt()); }
       if (tempCustomer.valueforreceiveamt) { updateCustomer.setReceiveamt(tempCustomer.getReceiveamt()); }
       if (tempCustomer.valueforpaymentamt) { updateCustomer.setPaymentamt(tempCustomer.getPaymentamt()); }
       if (tempCustomer.valueforoutstandingamt) { updateCustomer.setOutstandingamt(tempCustomer.getOutstandingamt()); }
       if (tempCustomer.getPhone() != null) { updateCustomer.setPhone(tempCustomer.getPhone()); }
       if (tempCustomer.getAgent() != null) { updateCustomer.setAgent(agentRepository.findById(tempCustomer.getAgent().getAgentcode())
               .orElseThrow(() -> new EntityNotFoundException("Agent " + tempCustomer.getAgent()
                       .getAgentcode() + " Not Found"))); }

       if (tempCustomer.getOrders().size() > 0) {
           updateCustomer.getOrders().clear(); //precaution
           for (Order o : tempCustomer.getOrders()) { //Orders Loop
               Order newOrder = new Order();
               Optional<Order> optionalOrder = orderRepository.findById(o.getOrdnum());
               if (optionalOrder.isPresent()) {
                   newOrder = optionalOrder.get();
               }
                   newOrder.setOrdamount(o.getOrdamount());
                   newOrder.setAdvanceamount(o.getAdvanceamount());
                   newOrder.setOrderdescription(o.getOrderdescription());
                   for (Payment p : newOrder.getPayments()) { //Payments loop
                       Payment newPayment = new Payment();
                       newPayment.setType(p.getType());
                       newOrder.getPayments().add(newPayment);
                   }
                   updateCustomer.getOrders().add(newOrder);

           }
       }

        return customerRepository.save(updateCustomer);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer " + id + " Not Found");
        }
    }

    @Transactional
    @Override
    public void deleteAll() {
//
    }


}
