package com.bookstore.Springboot_BookStore.repository;

import com.bookstore.Springboot_BookStore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
