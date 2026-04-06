package com.bookstore.Springboot_BookStore.repository;

import com.bookstore.Springboot_BookStore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
}
