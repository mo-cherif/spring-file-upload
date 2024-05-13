package me.mocherif.fileupload.student;

import lombok.AllArgsConstructor;
import me.mocherif.fileupload.payment.Payment;
import me.mocherif.fileupload.payment.PaymentRepos;
import me.mocherif.fileupload.payment.PaymentStatus;
import me.mocherif.fileupload.payment.PaymentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class StudentService {
    private StudentRepos studentRepos;
    public List<Student> students() {
        return studentRepos.findAll();
    }

    public Student findbyId(String id) {
        return studentRepos.findById(id).get();
    }
}
