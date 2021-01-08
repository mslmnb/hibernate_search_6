package com.gala.hibernate_search.model;

import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "book")
@Indexed                                // marks Book as indexed
public class Book {
    @Id                                //  is used to generate a document identifier.
    @GeneratedValue
    private Integer id;

    @FullTextField (analyzer = "english1")                     // maps a property to a full-text index field with the same name and type.
    private String title;

    @KeywordField                       // maps a property to a non-analyzed index field. Подобные поля используются для сортировки и фильтрации(?)
    private String isbn;

    @GenericField                       // use for index non-String types (int, double, Integer, ...)
    private int pageCount;

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = {@JoinColumn(name = "book_id")}, inverseJoinColumns = {@JoinColumn(name = "author_id")})
    @IndexedEmbedded               // в классе Author для индексации описано поле name (в данном классе оно одно)
                                    // поэтому в документе Book появится свойство (property)  "author.name"
                                   // Hibernate будет отслеживать изменение значения для эттого поля и автоматически синхронизировать его
    private Set<Author> authors = new HashSet<>();
    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
