package com.shop.ecommerce.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommerce.Entity.User;
import com.shop.ecommerce.Service.UserService;
import com.shop.ecommerce.dto.LoginRequestDto;
import com.shop.ecommerce.dto.UserMeDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://127.0.0.1:5501") 
public class Controller {

    @Autowired
    private UserService userService;

    /**
     * Регистрация нового пользователя.
     * Мы принимаем UserMeDto, так как в нем есть валидация и все нужные поля.
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserMeDto userDto) {
        // Контроллер просто передает DTO в сервис
        User savedUser = userService.register(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    /**
     * Вход пользователя.
     * Возвращает JWT токен в формате JSON: {"token": "..."}
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto) {

        String token = userService.login(loginRequestDto);
        return ResponseEntity.ok(Map.of("token", token));
    }

    /**
     * Получение данных текущего авторизованного пользователя.
     */
    @GetMapping("/me")
    public ResponseEntity<UserMeDto> me() {
        return ResponseEntity.ok(userService.me());
    }
}