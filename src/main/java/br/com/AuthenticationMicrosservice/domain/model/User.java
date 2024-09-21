package br.com.AuthenticationMicrosservice.domain.model;

import br.com.AuthenticationMicrosservice.domain.enums.UserRoles;
import jakarta.persistence.*;
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
    private String firstName;
    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
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
        if (this.role == UserRoles.ADMIN){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_COORDINATOR")
                    );
        } else if (this.role == UserRoles.COORDINATOR) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_COORDINATOR"),
                    new SimpleGrantedAuthority("ROLE_STUDENT")
            );
        }else{
            return List.of(
                    new SimpleGrantedAuthority("ROLE_STUDENT")
            );
        }
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