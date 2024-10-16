package br.com.AuthenticationMicrosservice.domain.converter;

import br.com.AuthenticationMicrosservice.domain.enums.UserRoles;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRolesConverter implements AttributeConverter<UserRoles, String> {

    @Override
    public String convertToDatabaseColumn(UserRoles attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public UserRoles convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        try {
            return UserRoles.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid value for UserRoles enum: " + dbData, e);
        }
    }
}
