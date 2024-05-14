package tasc.finalproject.PaymentService.repository;

import tasc.finalproject.PaymentService.entity.Payment;

public interface DaoPaymentRepository {

    long save(Payment payment);

}
