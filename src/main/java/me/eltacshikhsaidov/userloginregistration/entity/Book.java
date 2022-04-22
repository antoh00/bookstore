package me.eltacshikhsaidov.userloginregistration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "author")
    private String author;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User publisher;

    // creating constructor without id
    public Book(String title, String description, String imageUrl, String author, User publisher) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.author = author;
        this.publisher = publisher;
    }

    public Book(String title, String description, String imageUrl, String author) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.author = author;
    }
}
