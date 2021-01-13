package com.gala.hibernate_search.model.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gala.hibernate_search.model.product.LocalVariable;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;

@Slf4j
public class LocalVariableConverter implements AttributeConverter<LocalVariable, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LocalVariable localVariable) {
        try {
            return objectMapper.writeValueAsString(localVariable);
        } catch (JsonProcessingException e) {
            log.error(e.getOriginalMessage(), e);
            return "";
        }
    }

    @Override
    public LocalVariable convertToEntityAttribute(String locale) {
        try {
            return objectMapper.readValue(locale, LocalVariable.class);
        } catch (JsonProcessingException e) {
            log.error(e.getOriginalMessage(), e);
            return new LocalVariable();
        }
    }
}
