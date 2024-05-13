package me.mocherif.fileupload.payment;

import lombok.AllArgsConstructor;
import me.mocherif.fileupload.student.Student;
import me.mocherif.fileupload.student.StudentRepos;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PaymentService {
    public PaymentRepos paymentRepos;
    public StudentRepos studentRepos;

    public List<Payment> allPayment() {
        return paymentRepos.findAll();
    }

    private Payment findById( Long id) {
        return paymentRepos.findById(id).get();
    }

    public List<Payment> findByStudentCode( String code) {
        return paymentRepos.findByStudentCode(code);
    }

    public Payment savePayment(MultipartFile file, LocalDate date,
                               double amount, PaymentType type, String studentCode) throws IOException {

        var path = Paths.get(System.getProperty("user.home"), "student-app-files", "payments");
        if(!Files.exists(path)) {
            Files.createDirectories(path);
        }
        String fileId = UUID.randomUUID().toString();
        Path filepath = Paths.get(System.getProperty("user.home"), "student-app-files",fileId+".pdf");
        Student student = studentRepos.findByCode(studentCode);
        Files.copy(file.getInputStream(), filepath);
        Payment payment = Payment.builder()
                .paymentType(type)
                .amount(amount)
                .student(student)
                .paymentStatus(PaymentStatus.CREATED)
                .file(filepath.toUri().toString())
                .build();

        return paymentRepos.save(payment);
    }


    public byte [] getPaymentFile(Long paymentid) throws IOException {
        Payment payment = paymentRepos.findById(paymentid).get();
        String filePath = payment.getFile();
        return Files.readAllBytes(Path.of(URI.create(filePath)));

    }

}
