package me.eltacshikhsaidov.userloginregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import me.eltacshikhsaidov.userloginregistration.entity.Book;
import me.eltacshikhsaidov.userloginregistration.exception.BookNotFoundException;
import me.eltacshikhsaidov.userloginregistration.repository.BookRepository;

@Service
public class BookService {
    
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book", "id", id));
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book", "id", id));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setDescription(book.getDescription());
        bookToUpdate.setImageUrl(book.getImageUrl());
        return bookRepository.save(bookToUpdate);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksPerPage(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size)).getContent();
    }

}
