package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.dto.UserDTO;
import com.bookstore.Springboot_BookStore.model.User;
import com.bookstore.Springboot_BookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


   @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
       return ResponseEntity.ok("Register");
    }

  @PatchMapping("/id/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id ,@RequestBody UserDTO userDTO) {
      UserDTO update =  userService.userUpdate(id , userDTO);
      return ResponseEntity.ok(update);
  }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id); // service should return true if deleted

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }


}
