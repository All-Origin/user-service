package com.junior.user_service.converter;

import com.junior.user_service.enums.Roles;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class JsonEnumListConverter implements AttributeConverter<List<Roles>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Roles> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Error converting list of enums to JSON", e);
        }
    }

    @Override
    public List<Roles> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<Roles>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to list of enums", e);
        }
    }
}
