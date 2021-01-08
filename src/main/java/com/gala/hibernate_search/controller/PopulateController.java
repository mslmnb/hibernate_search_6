package com.gala.hibernate_search.controller;

import com.gala.hibernate_search.model.Author;
import com.gala.hibernate_search.model.Book;
import com.gala.hibernate_search.repository.AuthorRepository;
import com.gala.hibernate_search.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("populate")
@RequiredArgsConstructor
public class PopulateController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity populate() {
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
//    По умолчанию, особенно при использовании Elasticsearch, изменения не будут
//    отображается сразу после совершения транзакции. Небольшая задержка
//    необходима Elasticsearch для обработки изменений.
//    По этой причине, если вы изменяете сущности в транзакции, а затем выполняете поиск
//    сразу после этой транзакции, результаты поиска могут не соответствовать
//    изменениям, которые вы только что внесли.
}
