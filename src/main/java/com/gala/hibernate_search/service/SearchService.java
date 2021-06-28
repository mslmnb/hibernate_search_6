package com.gala.hibernate_search.service;

import com.gala.hibernate_search.model.Book;
import com.gala.hibernate_search.model.product.Product;
import com.gala.hibernate_search.model.product.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.common.BooleanOperator;
import org.hibernate.search.engine.search.predicate.SearchPredicate;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.engine.search.predicate.dsl.PredicateFinalStep;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.predicate.dsl.SimpleQueryStringPredicateFieldStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Product> getProductsByName(String searchPhrase) {
/*  // поиск через лямбда
        SearchSession searchSession = Search.session( entityManager );
        List<Product> hits = searchSession.search( Product.class )
                .where( f -> {
                    BooleanPredicateClausesStep<?> step = f.bool()
                            .minimumShouldMatchNumber(1)
                            .should(f.simpleQueryString().field("name.ru").matching(searchPhrase).defaultOperator(BooleanOperator.AND))
                            .should(f.simpleQueryString().field("name.kk").matching(searchPhrase).defaultOperator(BooleanOperator.AND))
                            .should(f.simpleQueryString().field("name.qq").matching(searchPhrase).defaultOperator(BooleanOperator.AND))
                            .must(f.match().field("status").matching(ProductStatus.ACTIVE));
                    return step;
                        }
                )
                .fetchHits( 20 );
        return hits;

 */

//     поиск через предикаты
        SearchSession searchSession = Search.session( entityManager );
        SearchScope<Product> scope = searchSession.scope( Product.class );
        SearchPredicateFactory factory = scope.predicate();

        SearchPredicate predicate1 = factory.bool()
                .minimumShouldMatchNumber(1)
                .should(factory.simpleQueryString().field("name.ru").matching(searchPhrase).defaultOperator(BooleanOperator.AND))
                .should(factory.simpleQueryString().field("name.kk").matching(searchPhrase).defaultOperator(BooleanOperator.AND))
                .should(factory.simpleQueryString().field("name.qq").matching(searchPhrase).defaultOperator(BooleanOperator.AND))
                .toPredicate();

        SearchPredicate predicate2 = factory.match()
                .field("status")
                .matching(ProductStatus.ACTIVE)
                .toPredicate();

        BooleanPredicateClausesStep<?> booleanJunction = factory.bool();
        booleanJunction.must( predicate1 );
        booleanJunction.must( predicate2 );

        SearchPredicate boolPredicate = booleanJunction.toPredicate();

        List<Product> result = searchSession.search( scope )
                .where( boolPredicate )
                .sort(p->p.field("createdAt"))
                .fetchHits( 20 );
        return result;
    }
}
