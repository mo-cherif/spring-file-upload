package me.mocherif.fileupload.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor
@Transactional
@Service
public class StudentService {
    private StudentRepos studentRepos;
    public List<Student> students() {
        return studentRepos.findAll();
    }

    public Student findbyId(String id) {
        return studentRepos.findById(id).get();
    }
}
