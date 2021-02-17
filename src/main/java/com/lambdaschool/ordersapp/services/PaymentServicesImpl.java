package com.lambdaschool.ordersapp.services;

import com.lambdaschool.ordersapp.models.Payment;
import com.lambdaschool.ordersapp.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "paymentservices")
public class PaymentServicesImpl implements PaymentServices{
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Payment save(Payment payment) { return paymentRepository.save(payment); }
}
