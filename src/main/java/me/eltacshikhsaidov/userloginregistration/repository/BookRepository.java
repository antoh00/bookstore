package me.eltacshikhsaidov.userloginregistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import me.eltacshikhsaidov.userloginregistration.entity.Book;
import me.eltacshikhsaidov.userloginregistration.entity.User;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Object findByPublisher(User user);

    List<Book> findByPublisher(User user);

    Object countByPublisher(User user);

}
