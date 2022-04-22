package me.eltacshikhsaidov.userloginregistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.eltacshikhsaidov.userloginregistration.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
