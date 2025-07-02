package com.example.library.repository;

import com.example.library.model.IssuedBook;
import com.example.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {

    List<IssuedBook> findByStudent(User student);

    List<IssuedBook> findByStudentId(Long studentId);
}


