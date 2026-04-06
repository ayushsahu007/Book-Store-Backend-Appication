package com.bookstore.Springboot_BookStore.controller;

import com.bookstore.Springboot_BookStore.dto.BookDTO;
import com.bookstore.Springboot_BookStore.service.BookService;
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
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {


    private final BookService bookService;
    private final RestResponseBuilder responseBuilder;


    @PostMapping("/add-book")
    public ResponseEntity<?>  addBook(@Valid  @RequestBody BookDTO bookDTO, HttpServletRequest request) {
        BookDTO savedBook = bookService.createBook(bookDTO);
        return responseBuilder.success(
                HttpStatus.CREATED,
                "Book created",
                savedBook,
                request
        );
    }

    @GetMapping("/view-all-book")
    public ResponseEntity<?> viewBook(HttpServletRequest request) {
        List<BookDTO> allBooks = bookService.getAllBooks();
        return responseBuilder.success(
                HttpStatus.OK,
                "Books fetched",
                allBooks,
                request
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id, HttpServletRequest request) {
        BookDTO findById = bookService.getById(id);
        return responseBuilder.success(
            HttpStatus.OK,
            "Book fetched",
                findById ,
            request
       );
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,
                                        @Valid @RequestBody BookDTO bookDTO,
                                        HttpServletRequest request) {

        BookDTO updateId = bookService.updateBook(id, bookDTO);

        return responseBuilder.success(
                HttpStatus.OK,
                "Book updated",
                updateId,
                request
        );
    }

    @DeleteMapping("/id/{id}")
    public  ResponseEntity<?> deleteBook(@PathVariable Long id,HttpServletRequest request) {
        bookService.deleteBook(id);
        return responseBuilder.success(
                HttpStatus.OK,
                "Book deleted",
                null,
                request
        );
    }




}
