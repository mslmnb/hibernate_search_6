package com.gala.hibernate_search.model;

import com.gala.hibernate_search.model.Book;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "author")
// здесь не требуется @Indexed так как мы не используем Author как самостоятельный документ для поиска
// в документе Book  Author используется как вложенный документ ( @IndexedEmbedded)
public class Author {
    @Id
    @GeneratedValue
    private Integer id;

    @FullTextField (analyzer = "name1")                 // maps a property to a full-text index field with the same name and type.
    private String name;
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
