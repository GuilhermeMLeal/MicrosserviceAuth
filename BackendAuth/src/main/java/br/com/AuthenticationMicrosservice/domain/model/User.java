package br.com.AuthenticationMicrosservice.domain.model;

import br.com.AuthenticationMicrosservice.domain.enums.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "TB_USERS", schema = "AUTHENTICATION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Column(name = "PASSWORD", nullable = false)
    @NotBlank(message = "Password cannot be blank")
    @JsonIgnore
    private String password;

    @Column(name = "EMAIL", nullable = false)
    @Email
    private String email;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "USER_ROLES", nullable = false)
    private UserRoles role;

    public User(String firstName, String lastName, String email, String password, UserRoles role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (this.role) {
            case ADMIN -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_COORDINATOR")
            );
            case COORDINATOR -> List.of(
                    new SimpleGrantedAuthority("ROLE_COORDINATOR"),
                    new SimpleGrantedAuthority("ROLE_STUDENT")
            );
            default -> List.of(new SimpleGrantedAuthority("ROLE_STUDENT"));
        };
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}