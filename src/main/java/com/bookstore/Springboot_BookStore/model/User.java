package com.bookstore.Springboot_BookStore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String userName;

    @NonNull
    private String password;

    @Column(unique = true)
    private String email;

    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;




}
