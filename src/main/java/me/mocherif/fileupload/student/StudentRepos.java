package me.mocherif.fileupload.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepos extends JpaRepository<Student, String> {
    Student findByCode(String code);
}
