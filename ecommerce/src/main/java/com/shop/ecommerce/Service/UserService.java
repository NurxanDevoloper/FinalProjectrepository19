package com.shop.ecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	JwtUtil jwtUtil;
	
	public User  register(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new RuntimeException("такой username существует");
		}
		User userEntity=new User();
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
		userEntity.setEnabled(true);
		userEntity.setEmail(user.getEmail());
		userEntity.setName(user.getName());
		userEntity.setSurname(user.getSurname());
		userEntity.setPhoneNumber(user.getPhoneNumber());
		userRepository.save(userEntity);

		Authority authority = new Authority();
		authority.setUsername(user.getUsername());
		authority.setAuthority("ROLE_USER");

		authorityRepository.save(authority);
		return user;
	}
	
	public String login(LoginRequestDto loginRequest) {
		User user=userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()->new RuntimeException("неверный username"));
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new RuntimeException("неверный пароль");
		}
		return jwtUtil.generateToken(loginRequest.getUsername());
	}
	
	public UserMeDto me() {
		User user = getCurrentUser();
		UserMeDto dto = new UserMeDto();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setName(user.getName());
		dto.setSurname(user.getSurname());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		return dto;	
	}
	
	private User getCurrentUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("user not found"));
	}
}
