package me.mocherif.fileupload;

import me.mocherif.fileupload.payment.Payment;
import me.mocherif.fileupload.payment.PaymentRepos;
import me.mocherif.fileupload.payment.PaymentStatus;
import me.mocherif.fileupload.payment.PaymentType;
import me.mocherif.fileupload.student.Student;
import me.mocherif.fileupload.student.StudentRepos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class FileUploadApplication {
	public static void main(String[] args) {
		SpringApplication.run(FileUploadApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepos studentRepos, PaymentRepos paymentRepos) {
		return args -> {
			studentRepos.save(Student.builder().id(UUID.randomUUID().toString()).fullName("Soma CHERIF").code("1111").build());
			studentRepos.save(Student.builder().id(UUID.randomUUID().toString()).fullName("Mohamed CHERIF").code("2222").build());

			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepos.findAll().forEach(student -> {
				int index = random.nextInt(paymentTypes.length);
				for(int i = 0 ; i< 10; i++) {
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*1000))
							.dateTime(LocalDateTime.now())
							.paymentType(paymentTypes[index])
							.file(UUID.randomUUID().toString())
							.paymentStatus(PaymentStatus.CREATED)
							.student(student)
							.build();
					paymentRepos.save(payment);
				}
			});



		};
	}
}
