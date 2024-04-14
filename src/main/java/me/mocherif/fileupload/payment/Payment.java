package me.mocherif.fileupload.payment;

import jakarta.persistence.*;
import lombok.*;
import me.mocherif.fileupload.student.Student;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private LocalDateTime dateTime;
    private double amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus = PaymentStatus.CREATED;
    private String file;

    @ManyToOne
    private Student student;
}
