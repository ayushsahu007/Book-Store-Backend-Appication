package com.bookstore.Springboot_BookStore.service;

import com.bookstore.Springboot_BookStore.model.Cart;
import com.bookstore.Springboot_BookStore.model.CartItem;
import com.bookstore.Springboot_BookStore.model.Order;
import com.bookstore.Springboot_BookStore.model.OrderItem;
import com.bookstore.Springboot_BookStore.repository.CartRepository;
import com.bookstore.Springboot_BookStore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(Long userId, Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // ✅ security check
        if (!cart.getUser().getId().equals(userId)) {
            throw new RuntimeException("Cart does not belong to user");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());

            double itemTotal = cartItem.getQuantity() * cartItem.getBook().getPrice();
            orderItem.setPrice(itemTotal);

            totalPrice += itemTotal;

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalPrice);

        Order savedOrder = orderRepository.save(order);

        // ✅ clear cart instead of delete
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return savedOrder;
    }


}
