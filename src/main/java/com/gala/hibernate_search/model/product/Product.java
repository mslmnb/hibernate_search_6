package com.gala.hibernate_search.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Indexed
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", length = 1024)
    @Convert(converter = LocalVariableConverter.class)
    @IndexedEmbedded  // указывает, что сущность LocalVariable, должна быть вложена при индексацц в данную
    private LocalVariable name;

    // выбор по умолчанию, который будет работать для каждого типа со встроенной поддержкой.
    // аннотациz, не предоставляют никаких дополнительных функций, таких как полнотекстовый поиск:
    // совпадения в общем поле являются точными совпадениями.
    @GenericField(sortable=Sortable.YES)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @KeywordField
    private ProductStatus status;
}
