package com.gala.hibernate_search.model.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalVariable {

    @FullTextField(analyzer = "autocomplete", searchAnalyzer = "standard")
    private String ru;
    @FullTextField(analyzer = "autocomplete", searchAnalyzer = "standard")
    private String kk;
    @FullTextField(analyzer = "autocomplete", searchAnalyzer = "standard")
    private String qq;

}
