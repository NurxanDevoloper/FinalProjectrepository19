package com.shop.ecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.ecommerce.Entity.Authority;
import com.shop.ecommerce.Entity.User;
import com.shop.ecommerce.Repository.AuthorityRepository;
import com.shop.ecommerce.Repository.UserRepository;
import com.shop.ecommerce.dto.LoginRequestDto;
import com.shop.ecommerce.dto.UserMeDto;
import com.shop.ecommerce.jwt.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired 
    private JwtUtil jwtUtil;

    @Transactional
    public User register(UserMeDto userDto) { // Теперь принимает DTO
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        // Создаем новую сущность и перекладываем данные из DTO
        User userEntity = new User();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setName(userDto.getName());
        userEntity.setSurname(userDto.getSurname());
        userEntity.setPhoneNumber(userDto.getPhoneNumber()); // ДОБАВЛЕНО: Теперь база не будет ругаться
        userEntity.setEnabled(true);
        
        // Сохраняем пользователя
        User savedUser = userRepository.save(userEntity);

        // Создаем роль
        Authority authority = new Authority();
        authority.setUsername(savedUser.getUsername());
        authority.setAuthority("ROLE_USER");
        authorityRepository.save(authority);

        return savedUser; 
    }

    public String login(LoginRequestDto loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return jwtUtil.generateToken(user.getUsername());
    }

    public UserMeDto me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserMeDto dto = new UserMeDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber()); // ДОБАВЛЕНО для полноты данных
        return dto;
    }
}