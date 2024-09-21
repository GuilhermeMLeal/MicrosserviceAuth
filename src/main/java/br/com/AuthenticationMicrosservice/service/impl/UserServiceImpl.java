package br.com.AuthenticationMicrosservice.service.impl;

import br.com.AuthenticationMicrosservice.domain.dto.RegisterUserDto;
import br.com.AuthenticationMicrosservice.domain.model.User;
import br.com.AuthenticationMicrosservice.repository.UserRepository;
import br.com.AuthenticationMicrosservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserDto salvar(RegisterUserDto registerUserDto) {

        User userExists = usuarioRepository.findByEmail(registerUserDto.email());

        if (userExists != null) {
            throw new RuntimeException("Usuário já existe!");
        }

        var passwordHash = passwordEncoder.encode(registerUserDto.password());

        User entity = new User(registerUserDto.firstName(), registerUserDto.lastName(), registerUserDto.email(), passwordHash, registerUserDto.role());

        User newUser = usuarioRepository.save(entity);

        return new RegisterUserDto(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getPassword(), newUser.getIsActive(), newUser.getRole());
    }
}
