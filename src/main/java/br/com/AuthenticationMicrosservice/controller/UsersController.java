package br.com.AuthenticationMicrosservice.controller;

import br.com.AuthenticationMicrosservice.domain.dto.RegisterUserDto;
import br.com.AuthenticationMicrosservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UsersController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    private RegisterUserDto salvar(@RequestBody RegisterUserDto registerUserDto) {
        return  userService.salvar(registerUserDto) ;
    }
}
