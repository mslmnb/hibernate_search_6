package com.gala.hibernate_search.controller;

import com.gala.hibernate_search.model.Book;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@RestController
@RequiredArgsConstructor
@RequestMapping("reindex")
public class InitialController {

    private final EntityManager entityManager;

    // эту апишку необхожимо запустить, если на момент появления Hibernate Search
    // в базе данных были уже данные. Чтобы проиндексировать эти данные нужно
    // один раз запустить данное апи
    @GetMapping
    public ResponseEntity initialBookIndex() {
        SearchSession searchSession = Search.session( entityManager );
        MassIndexer indexer = searchSession.massIndexer( Book.class )
                                            .threadsToLoadObjects( 7 );
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
