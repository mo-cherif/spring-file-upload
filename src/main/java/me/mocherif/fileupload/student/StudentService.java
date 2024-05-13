package me.mocherif.fileupload.student;

import lombok.AllArgsConstructor;
import java.util.List;


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
