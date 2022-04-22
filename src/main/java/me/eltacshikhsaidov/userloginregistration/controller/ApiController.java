package me.eltacshikhsaidov.userloginregistration.controller;

import lombok.AllArgsConstructor;
import me.eltacshikhsaidov.userloginregistration.entity.role.UserRole;
import me.eltacshikhsaidov.userloginregistration.exception.BookNotFoundException;
import me.eltacshikhsaidov.userloginregistration.repository.BookRepository;
import me.eltacshikhsaidov.userloginregistration.repository.UserRepository;
import me.eltacshikhsaidov.userloginregistration.entity.Book;
import me.eltacshikhsaidov.userloginregistration.entity.User;
import me.eltacshikhsaidov.userloginregistration.service.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
public class ApiController {

    private final RegistrationService registrationService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    // register as a publisher
    @PostMapping(path = "/publisher/register")
    public String publisherRegister(@RequestBody RegistrationRequest request) {
        return registrationService.register(request, UserRole.PUBLISHER);
    }

    // register as a user (subscriber)
    @PostMapping(path = "/user/register")
    public String userRegister(@RequestBody RegistrationRequest request) {
        return registrationService.register(request, UserRole.USER);
    }

    // confirm your email address
    @GetMapping(path = "/register/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    // you will be redirected here after login
    @GetMapping(path = "/profile")
    public ResponseEntity<?> profile(Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();

        return new ResponseEntity<>("You logged in successfully\nemail:" + email + "\nLogged as: " + user.getUserRole(), HttpStatus.OK);
    }

    // Book API for publisher and user

    @GetMapping(path = "/book/all")
    public List<Book> getAllBooks(@RequestParam(value = "pageNo", defaultValue = "0",required = false) int pageNo, 
                                  @RequestParam(value = "pageSize", defaultValue = "3",required = false) int pageSize) {

        return bookRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent();
        
    }

    @GetMapping(path = "/book/{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book", "id", id));
    }

    @PostMapping(path = "/book/add")
    public ResponseEntity<?> addBook(@RequestBody Book book, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();

        if (user.getUserRole().equals(UserRole.PUBLISHER)) {
            book.setPublisher(user);
            bookRepository.save(book);

            // just for testing purpose
            Map<String, Object> response = new HashMap<>();

            response.put("successMessage", "Book added successfully");
            response.put("statusCode", 200);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "You are not allowed to add a book"
            );
        }
    }

    @PutMapping(path = "/book/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id, @RequestBody Book book, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();

        if (user.getUserRole().equals(UserRole.PUBLISHER)) {
            bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book", "id", id));
            book.setPublisher(user);
            book.setId(id);
            bookRepository.save(book);
            return new ResponseEntity<>("Book with id " + id + " updated", HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "You are not allowed to update book information"
            );
        }
    }

    @DeleteMapping(path = "/book/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id, Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();

        if (user.getUserRole().equals(UserRole.PUBLISHER)) {
            Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book", "id", id));
            bookRepository.delete(book);
            return new ResponseEntity<>("Book with id " + id + " deleted", HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "You are not allowed to delete a book"
            );
        }

    }

}