package com.klu.bookmicroservice.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.klu.bookmicroservice.model.Book;
import com.klu.bookmicroservice.service.BookService;

@RestController
@RequestMapping("/books")
public class BookControlle {

    private final BookService bookService;

    public BookControlle(BookService bookService) {
        this.bookService = bookService;
    }

    // ADD BOOK
    @PostMapping
    public ResponseEntity<Book> addBook(
            @Valid @RequestBody Book book) {

        Book savedBook = bookService.addBook(book);

        return new ResponseEntity<>(
                savedBook,
                HttpStatus.CREATED
        );
    }

    // VIEW ALL BOOKS
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok(
                bookService.getAllBooks()
        );
    }

    // VIEW ONE BOOK
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable Long id) {

        Book book = bookService.getBookById(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    // UPDATE BOOK
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book) {

        Book updatedBook =
                bookService.updateBook(id, book);

        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedBook);
    }

    // DELETE BOOK
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Long id) {

        boolean deleted =
                bookService.deleteBook(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Book deleted successfully"
        );
    }
}