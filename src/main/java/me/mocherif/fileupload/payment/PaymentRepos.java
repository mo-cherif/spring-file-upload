package me.mocherif.fileupload.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepos extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String code);

    List<Payment> findByPaymentStatus(PaymentStatus status);
}
