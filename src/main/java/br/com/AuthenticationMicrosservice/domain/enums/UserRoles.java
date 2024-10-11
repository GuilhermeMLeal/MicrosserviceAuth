package br.com.AuthenticationMicrosservice.domain.enums;

import br.com.AuthenticationMicrosservice.domain.persistence.AbstractEnumConverter;
import br.com.AuthenticationMicrosservice.domain.persistence.PersistableEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum UserRoles implements PersistableEnum<String> {
    ADMIN("admin"),
    STUDENT("student"),
    COORDINATOR("coordinator");

    private final String role;

    @Override
    @JsonValue
    public String getCode() {
        return this.role;
    }

    @JsonCreator
    public static UserRoles fromUserRoleType(String role) {
        return Arrays.stream(UserRoles.values())
                .filter(type -> type.getCode().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown role type: " + role));
    }

    @Converter(autoApply = true)
    @SuppressWarnings("unused")
    public static class UserRoleTypeConverter extends AbstractEnumConverter<UserRoles, String> {
        protected UserRoleTypeConverter() {
            super(UserRoles::fromUserRoleType);
        }
    }
}