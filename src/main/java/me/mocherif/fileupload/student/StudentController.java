package me.mocherif.fileupload.student;

import lombok.AllArgsConstructor;
import me.mocherif.fileupload.payment.Payment;
import me.mocherif.fileupload.payment.PaymentRepos;
import me.mocherif.fileupload.payment.PaymentStatus;
import me.mocherif.fileupload.payment.PaymentType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class StudentController {
    private PaymentRepos paymentRepos;
    private StudentRepos  studentRepos;

    @GetMapping("/payments")
    public List<Payment> allPayment() {
        return paymentRepos.findAll();
    }

    @GetMapping("/payments/{id}")
    private Payment findById(@PathVariable Long id) {
        return paymentRepos.findById(id).get();
    }

    @GetMapping("/students")
    public List<Student> students() {
        return studentRepos.findAll();
    }

    @GetMapping("/students/{id}")
    public Student findbyId(@PathVariable String id) {
        return studentRepos.findById(id).get();
    }

    @GetMapping("/students/{code}/payments")
    public List<Payment> findByStudentCode(@PathVariable String code) {
        return paymentRepos.findByStudentCode(code);
    }

    @PostMapping(value = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,
                               double amount, PaymentType type, String studentCode) throws IOException {

        Path path = Paths.get(System.getProperty("user.home"), "student-app-files", "payments");
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

    @GetMapping(value = "/paymentFile/{paymentid}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte [] getPaymentFile(@PathVariable Long paymentid) throws IOException {
        Payment payment = paymentRepos.findById(paymentid).get();
        String filePath = payment.getFile();
        return Files.readAllBytes(Path.of(URI.create(filePath)));

    }
}
