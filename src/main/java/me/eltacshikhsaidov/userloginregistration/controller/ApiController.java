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

import java.time.LocalDateTime;
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
    public ResponseEntity<?> publisherRegister(@RequestBody RegistrationRequest request) {

        Map<String, Object> response = new HashMap<>();

        String confirmation = registrationService.register(request, UserRole.PUBLISHER);
        response.put("status", 200);
        response.put("message", "confirmation link has been sent to your email address");
        response.put("registeredAt", LocalDateTime.now());
        response.put("confirmUrl", "https://taskingress.herokuapp.com/api/register/confirm?token=" + confirmation);
        response.put("path", "/api/publisher/register");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // register as a user (subscriber)
    @PostMapping(path = "/user/register")
    public ResponseEntity<?> userRegister(@RequestBody RegistrationRequest request) {


        Map<String, Object> response = new HashMap<>();

        String confirmation = registrationService.register(request, UserRole.USER);
        response.put("status", 200);
        response.put("message", "confirmation link has been sent to your email address");
        response.put("registeredAt", LocalDateTime.now());
        response.put("confirmUrl", "https://taskingress.herokuapp.com/api/register/confirm?token=" + confirmation);
        response.put("path", "/api/user/register");

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // confirm your email address
    @GetMapping(path = "/register/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            registrationService.confirmToken(token);
            response.put("status", 200);
            response.put("message", "Your email has been confirmed!");
            response.put("confirmedAt", LocalDateTime.now());
        } catch (Exception e) {
            response.put("status", 400);
            response.put("message", "Your registration token has been expired!");
            response.put("openedAt", LocalDateTime.now());
        }

        response.put("path", "/api/register/confirm");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // you will be redirected here after login
    @GetMapping(path = "/profile")
    public ResponseEntity<?> profile(Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("role", user.getUserRole());
        response.put("successMessage", "You are logged in!");
        response.put("path", "/api/profile");
        response.put("logoutPath", "/logout");

        if (user.getUserRole().equals(UserRole.PUBLISHER)) {
            response.put("totalBooksYouPublished", bookRepository.countByPublisher(user));
            // System.out.println(bookRepository.findByPublisher(user));
            response.put("books", bookRepository.findByPublisher(user));
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Book API for publisher and user

    @GetMapping(path = "/book/all")
    public List<Book> getAllBooks(@RequestParam(value = "pageNo", defaultValue = "0",required = false) int pageNo, 
                                  @RequestParam(value = "pageSize", defaultValue = "3",required = false) int pageSize) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("totalBooks", bookRepository.count());
        response.put("pageNo", pageNo);
        response.put("pageSize", pageSize);
        response.put("books", bookRepository.findAll(PageRequest.of(pageNo, pageSize)).getContent());
        response.put("path", "/api/book/all");
        
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

            Map<String, Object> response = new HashMap<>();

            response.put("status", 200);
            response.put("successMessage", "Book added successfully");
            response.put("path", "/api/book/add");

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
            Book bookToUpdate = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book", "id", id));

            if (bookToUpdate.getPublisher().equals(user)) {

                book.setPublisher(user);
                book.setId(id);
                bookRepository.save(book);

                
                Map<String, Object> response = new HashMap<>();

                response.put("status", 200);
                response.put("successMessage", "Book with id " + id + " updated");
                response.put("path", "/api/book/update/" + id);
                response.put("updatedAt", LocalDateTime.now());

                return new ResponseEntity<>(response, HttpStatus.OK);


            } else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You are not allowed to update this book"
                );
            }

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

            if (book.getPublisher().equals(user)) {
                bookRepository.delete(book);

                Map<String, Object> response = new HashMap<>();

                response.put("status", 200);
                response.put("successMessage", "Book with id " + id + " deleted");
                response.put("path", "/api/book/delete/" + id);
                response.put("deletedAt", LocalDateTime.now());

                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You are not allowed to delete this book"
                );
            }
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "You are not allowed to delete a book"
            );
        }

    }

}