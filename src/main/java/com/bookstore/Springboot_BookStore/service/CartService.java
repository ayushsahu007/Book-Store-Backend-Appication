package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.dto.CartItemResponseDTO;
import com.bookstore.Springboot_BookStore.dto.CartResponseDTO;
import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.model.User;
import com.bookstore.Springboot_BookStore.repository.CartRepository;
import com.bookstore.Springboot_BookStore.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {


    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    //Create cart
    @Transactional
    public CartResponseDTO createCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  check if cart already exists
        Cart existingCart = cartRepository.findByUserId(userId);
        if (existingCart != null) {
            return convertToDTO(existingCart);
        }

        // ✅ create new cart
        Cart cart = new Cart();
        cart.setUser(user);

        // ✅ set both sides (if bidirectional)
        user.setCart(cart);

        Cart savedCart = cartRepository.save(cart);

        return convertToDTO(savedCart);
    }

                 // ✅ Get Cart by UserId
    @Transactional
    public CartResponseDTO getCartByUserId(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        return convertToDTO(cart);
    }

              // ✅ Convert Entity → DTO
            private CartResponseDTO convertToDTO(Cart cart) {

        // Step 1: basic mapping
        CartResponseDTO dto = modelMapper.map(cart, CartResponseDTO.class);

        // Step 2: fix nested field
             dto.setUserId(cart.getUser().getId());

        // Step 3: map items manually
        List<CartItemResponseDTO> itemDTOs = cart.getCartItems() != null
                ? cart.getCartItems().stream().map(item -> {

            CartItemResponseDTO itemDTO = new CartItemResponseDTO();

            itemDTO.setItemId(item.getId());
            itemDTO.setBookId(item.getBook().getId());
            itemDTO.setBookName(item.getBook().getTitle());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getBook().getPrice());

            double total = item.getQuantity() * item.getBook().getPrice();
            itemDTO.setTotalPrice(total);

            return itemDTO;

        }).toList()
                : List.of();

        dto.setItems(itemDTOs);

        // Step 4: calculate total price
        double totalPrice = itemDTOs.stream()
                .mapToDouble(CartItemResponseDTO::getTotalPrice)
                .sum();

        dto.setTotalPrice(totalPrice);

        return dto;
    }
}
