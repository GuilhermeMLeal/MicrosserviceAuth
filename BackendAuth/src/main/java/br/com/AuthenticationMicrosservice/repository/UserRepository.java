package br.com.AuthenticationMicrosservice.repository;

import br.com.AuthenticationMicrosservice.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
