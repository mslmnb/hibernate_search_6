package com.gala.hibernate_search.controller;

import com.gala.hibernate_search.model.Author;
import com.gala.hibernate_search.model.Book;
import com.gala.hibernate_search.model.product.LocalVariable;
import com.gala.hibernate_search.model.product.Product;
import com.gala.hibernate_search.model.product.ProductStatus;
import com.gala.hibernate_search.repository.AuthorRepository;
import com.gala.hibernate_search.repository.BookRepository;
import com.gala.hibernate_search.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("populate")
@RequiredArgsConstructor
public class PopulateController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ProductRepository productRepository;

    @GetMapping("/book")
    public ResponseEntity populateBook() {
        Author author = new Author();
        author.setName( "John Doe" );
        authorRepository.save(author);

        Book book = new Book();
        book.setTitle( "Refactoring: Improving the Design of Existing Code" );
        book.setIsbn( "978-0-58-600835-5" );
        book.setPageCount( 200 );
        book.getAuthors().add( author );
        bookRepository.save(book);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity populateProduct() {
        LocalDateTime jan1 = LocalDateTime.of(2020, 01, 01, 0, 0);
        LocalDateTime jan10 = LocalDateTime.of(2020, 01, 10, 0, 0);
        LocalDateTime feb1 = LocalDateTime.of(2020, 02, 01, 0, 0);
        LocalDateTime feb11 = LocalDateTime.of(2020, 02, 11, 0, 0);

        LocalVariable name = new LocalVariable("сок томатный", "томат шырыны", "томат шырыны");
        Product product = new Product(null, name, jan1, ProductStatus.ACTIVE);
        productRepository.save(product);
        name = new LocalVariable("сок апельсиновый", "апельсин шырыны", "апельсин шырыны");
        product = new Product(null, name, jan10, ProductStatus.ACTIVE);
        productRepository.save(product);
        name = new LocalVariable("сок персиковый", "шабдалы шырыны", "шабдалы шырыны");
        product = new Product(null, name, feb1, ProductStatus.ACTIVE);
        productRepository.save(product);
        name = new LocalVariable("сок яблочный", "алма шырыны", "алма шырыны");
        product = new Product(null, name, feb11, ProductStatus.HIDE);
        productRepository.save(product);
        return new ResponseEntity(HttpStatus.OK);
    }
//    По умолчанию, особенно при использовании Elasticsearch, изменения не будут
//    отображается сразу после совершения транзакции. Небольшая задержка
//    необходима Elasticsearch для обработки изменений.
//    По этой причине, если вы изменяете сущности в транзакции, а затем выполняете поиск
//    сразу после этой транзакции, результаты поиска могут не соответствовать
//    изменениям, которые вы только что внесли.
}
