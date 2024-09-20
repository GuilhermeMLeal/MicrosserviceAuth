package br.com.AuthenticationMicrosservice.domain.enums;

import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN("admin"),
    STUDENT("student"),
    COORDINATOR("coordinator");

    private String role;

    UserRoles(String role){
        this.role = role;
    }

}
