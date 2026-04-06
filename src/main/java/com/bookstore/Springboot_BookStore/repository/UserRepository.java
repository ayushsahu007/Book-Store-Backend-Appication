package com.bookstore.Springboot_BookStore.repository;


import com.bookstore.Springboot_BookStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
