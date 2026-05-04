package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.dto.OrderItemResponseDTO;
import com.bookstore.Springboot_BookStore.dto.OrderRequestDTO;
import com.bookstore.Springboot_BookStore.dto.OrderResponseDTO;
import com.bookstore.Springboot_BookStore.model.*;
import com.bookstore.Springboot_BookStore.repository.CartRepository;
import com.bookstore.Springboot_BookStore.repository.OrderRepository;
import com.bookstore.Springboot_BookStore.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;



    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findById(requestDTO.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // ✅ security check
        if (!cart.getUser().getId().equals(requestDTO.getUserId())) {
            throw new RuntimeException("Cart does not belong to user");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());

            //price freeze
            double bookPrice = cartItem.getBook().getPrice();
            double itemTotal = cartItem.getQuantity() * bookPrice;

            orderItem.setPrice(itemTotal);

            totalAmount += itemTotal;
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // ✅ clear cart instead of delete
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return convertToDTO(savedOrder);
    }

    // ✅ Entity → DTO conversion
    private OrderResponseDTO convertToDTO(Order order) {

        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);

        dto.setUserId(order.getUser().getId());

        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream().map(item -> {

            OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();

            itemDTO.setItemId(item.getId());
            itemDTO.setBookId(item.getBook().getId());
            itemDTO.setBookName(item.getBook().getTitle());
            itemDTO.setQuantity(item.getQuantity());

            double total = item.getPrice(); // already stored
            itemDTO.setTotalPrice(total);

            return itemDTO;

        }).toList();

        dto.setItems(itemDTOs);

        return dto;
    }


}
