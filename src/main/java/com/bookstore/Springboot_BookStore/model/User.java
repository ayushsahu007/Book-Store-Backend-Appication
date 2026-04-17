package com.bookstore.Springboot_BookStore.model;

import com.bookstore.Springboot_BookStore.model.type.Role;
import jakarta.persistence.*;
import lombok.*;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL)
    private Cart cart;



    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
