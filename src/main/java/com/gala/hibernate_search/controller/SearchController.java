package com.gala.hibernate_search.controller;

import com.gala.hibernate_search.model.Book;
import com.gala.hibernate_search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "search")
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public List<Book> getBooksByWord(@Param(value = "word") String word) {
        return  searchService.getBooksByWord(word);
    }


}
