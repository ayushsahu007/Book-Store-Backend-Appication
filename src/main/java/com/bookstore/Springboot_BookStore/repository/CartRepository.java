package com.bookstore.Springboot_BookStore.repository;

import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);

    Optional<Cart> findByUser(User user);
}
