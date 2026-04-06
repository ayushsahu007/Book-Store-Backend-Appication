package com.bookstore.Springboot_BookStore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String title;
    private String author;
    private Double price;

    private Integer stock;

}
