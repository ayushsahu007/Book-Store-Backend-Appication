package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.dto.CartItemRequestDTO;
import com.bookstore.Springboot_BookStore.dto.CartItemResponseDTO;
import com.bookstore.Springboot_BookStore.dto.CartResponseDTO;
import com.bookstore.Springboot_BookStore.model.Book;
import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.model.CartItem;
import com.bookstore.Springboot_BookStore.repository.BookRepository;
import com.bookstore.Springboot_BookStore.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public CartResponseDTO addItem(Long cartId, CartItemRequestDTO requestDto) {

        // ✅ Fetch cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // ✅ Fetch book
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        int quantity = requestDto.getQuantity();

        // ✅ Stock check
        if (quantity > book.getStock()) {
            throw new RuntimeException("Only " + book.getStock() + " books available");
        }

        // ✅ Initialize list if null
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        // ✅ Check existing item
        CartItem existingItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getBook() != null &&
                        item.getBook().getId().equals(requestDto.getBookId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {

            int totalQty = existingItem.getQuantity() + quantity;

            if (totalQty > book.getStock()) {
                throw new RuntimeException("Only " + book.getStock() + " books available in total");
            }

            existingItem.setQuantity(totalQty);

        } else {

            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);

            cart.getCartItems().add(cartItem);
        }

        // ✅ Save cart (works if CascadeType.ALL is set)
        Cart savedCart = cartRepository.save(cart);

        return convertToDTO(savedCart);
    }

    private CartResponseDTO convertToDTO(Cart cart) {

        // ✅ Basic mapping
        CartResponseDTO dto = modelMapper.map(cart, CartResponseDTO.class);

        // ✅ Safe user mapping
        if (cart.getUser() != null) {
            dto.setUserId(cart.getUser().getId());
        }

        // ✅ Map items safely
        List<CartItemResponseDTO> itemDTOs = cart.getCartItems() != null
                ? cart.getCartItems().stream().map(item -> {

            CartItemResponseDTO itemDTO = new CartItemResponseDTO();

            itemDTO.setItemId(item.getId());
            itemDTO.setQuantity(item.getQuantity());

            if (item.getBook() != null) {
                itemDTO.setBookId(item.getBook().getId());
                itemDTO.setBookName(item.getBook().getTitle());

                double price = item.getBook().getPrice();
                itemDTO.setPrice(price);
                itemDTO.setTotalPrice(item.getQuantity() * price);
            }

            return itemDTO;

        }).toList()
                : List.of();

        dto.setItems(itemDTOs);

        // ✅ Calculate total
        double cartTotal = itemDTOs.stream()
                .mapToDouble(CartItemResponseDTO::getTotalPrice)
                .sum();

        dto.setTotalPrice(cartTotal);

        return dto;
    }
}