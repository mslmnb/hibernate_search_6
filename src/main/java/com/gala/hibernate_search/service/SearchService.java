package com.gala.hibernate_search.service;

import com.gala.hibernate_search.model.Book;
import com.gala.hibernate_search.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchService {
    @PersistenceContext
    private final EntityManager entityManager;

    public List<Book> getBooksByWord(String word) {
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Book> result = searchSession.search(Book.class)
                .where(f -> f.match()
                        .fields("title", "authors.name")
                        .matching(word)
                ).fetch(20);
        long totalHitCount = result.total().hitCount();
//        если нужно получить только общее количество записей
//        long totalHitCount = searchSession.search( Book.class )
//                .where( f -> f.match()
//                        .fields( "title", "authors.name" )
//                        .matching( "refactoring" ) )
//                .fetchTotalHitCount();
        return result.hits();
    }

    public List<Product> getProductsByName(String name) {
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<Product> result = searchSession.search(Product.class)
                .where(f -> f.match()
                        .fields("name.ru", "name.kk", "name.qq")
                        .matching(name)
                ).fetch(20);
        return result.hits();
    }
}
