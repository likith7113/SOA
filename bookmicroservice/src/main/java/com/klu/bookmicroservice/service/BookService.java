package com.klu.bookmicroservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.klu.bookmicroservice.model.Book;
import com.klu.bookmicroservice.repo.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // CREATE
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // READ ALL
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // READ ONE
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // UPDATE
    public Book updateBook(Long id, Book book) {

        Book existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook == null) {
            return null;
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());

        return bookRepository.save(existingBook);
    }

    // DELETE
    public boolean deleteBook(Long id) {

        if (!bookRepository.existsById(id)) {
            return false;
        }

        bookRepository.deleteById(id);

        return true;
    }
}