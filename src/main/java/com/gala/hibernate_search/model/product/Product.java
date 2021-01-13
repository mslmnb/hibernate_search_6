package com.gala.hibernate_search.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

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
    @IndexedEmbedded
    private LocalVariable name;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @KeywordField
    private ProductStatus status;
}
