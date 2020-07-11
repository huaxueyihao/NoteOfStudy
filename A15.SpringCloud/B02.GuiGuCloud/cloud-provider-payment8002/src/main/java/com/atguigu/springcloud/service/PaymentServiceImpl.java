package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.dao.PaymentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    public int create(Payment payment){
        return paymentDao.create(payment);
    }


    public Payment getPaymentById(Long id){
        return paymentDao.getPaymentById(id);
    }

}
