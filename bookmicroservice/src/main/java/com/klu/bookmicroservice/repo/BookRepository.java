package com.klu.bookmicroservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klu.bookmicroservice.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}