package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.dto.BookDTO;
import com.bookstore.Springboot_BookStore.dto.UserDTO;
import com.bookstore.Springboot_BookStore.model.User;
import com.bookstore.Springboot_BookStore.service.UserService;
import com.bookstore.Springboot_BookStore.util.RestResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final RestResponseBuilder responseBuilder;


   @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO , HttpServletRequest request) {
         UserDTO userRegister =  userService.register(userDTO);
       return responseBuilder.success(
               HttpStatus.CREATED,
               "User Registerd",
               userRegister,
               request
       );
    }

    @GetMapping("all-users")
    public ResponseEntity<?>  getAllUsers(HttpServletRequest request) {
        List<UserDTO> allUser = userService.getAllUser();
        return responseBuilder.success(
                HttpStatus.OK,
                "User Fetched",
                allUser,
                request
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, HttpServletRequest request) {
        UserDTO byID = userService.getByID(id);
        return responseBuilder.success(
                HttpStatus.OK,
                "User Fetched",
                byID,
                request
        );
    }


  @PatchMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id ,@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
      UserDTO update =  userService.userUpdate(id , userDTO);
      return responseBuilder.success(
              HttpStatus.OK,
              "user update",
              update,
              request
      );
  }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id,HttpServletRequest request) {
       userService.deleteUser(id); // service should return true if deleted
        return responseBuilder.success(
                HttpStatus.OK,
                "user deleted",
                null,
                request
        );
    }
}
