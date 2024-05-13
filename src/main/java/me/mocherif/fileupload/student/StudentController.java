package me.mocherif.fileupload.student;

import lombok.AllArgsConstructor;
import me.mocherif.fileupload.payment.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RestController
@AllArgsConstructor
public class StudentController {
    private PaymentService paymentService;
    private StudentService  studentService;
    private PaymentRepos paymentRepos;

    @GetMapping("/payments")
    public List<Payment> allPayment() {
        return paymentService.allPayment();
    }

    @GetMapping("/payments/{id}")
    private Payment findById(@PathVariable Long id) {
        return paymentService.findById(id);
    }

    @GetMapping("/students")
    public List<Student> students() {
        return studentService.students();
    }

    @GetMapping("/students/{id}")
    public Student findbyId(@PathVariable String id) {
        return studentService.findbyId(id);
    }

    @GetMapping("/students/{code}/payments")
    public List<Payment> findByStudentCode(@PathVariable String code) {
        return paymentService.findByStudentCode(code);
    }

    @PostMapping(value = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,
                               double amount, PaymentType type, String studentCode) throws IOException {
        return paymentService.savePayment(file, date, amount, type, studentCode);
    }

    @GetMapping(value = "/paymentFile/{paymentid}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte [] getPaymentFile(@PathVariable Long paymentid) throws IOException {
        return paymentService.getPaymentFile(paymentid);
    }

    @PutMapping("/payments/update/{paymentId}")
    public Payment updatePaymentStatus(@RequestParam  PaymentStatus paymentStatus,@PathVariable Long paymentId) {
        return paymentService.updatePaymentStatus(paymentStatus, paymentId);
    }
}
