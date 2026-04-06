package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.dto.BookDTO;
import com.bookstore.Springboot_BookStore.dto.UserDTO;
import com.bookstore.Springboot_BookStore.model.Book;
import com.bookstore.Springboot_BookStore.model.User;
import com.bookstore.Springboot_BookStore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    public UserDTO register(UserDTO userDTO) {
        // Convert DTO to entity
        User user = modelMapper.map(userDTO, User.class);
        // Save entity to the database
        User savedUser = userRepository.save(user);
        // Convert saved entity back to DTO and return
        return modelMapper.map(savedUser, UserDTO.class);
    }


    public UserDTO userUpdate(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user Not Found"));

        if (userDTO.getUserName() != null) {
            user.setUserName(userDTO.getUserName());
        }

        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true; // deleted successfully
        } else {
            return false; // user not found
        }
    }



}
