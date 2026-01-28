package com.shop.ecommerce.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Username is required")
	    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
	    @Column(nullable = false, unique = true)
	    private String username;

	    @NotBlank(message = "Password is required")
	    @Size(min = 6, message = "Password must be at least 6 characters")
	    private String password;

	    private boolean enabled = true;

	    @NotBlank(message = "Phone number is required")
	    @Pattern(
	        regexp = "^\\+?[0-9]{10,15}$",
	        message = "Phone number must contain only digits and be 10–15 characters long"
	    )
	    @Column(name = "phone_number")
	    private String phoneNumber;

	    @NotBlank(message = "Name is required")
	    @Size(max = 50, message = "Name must be at most 50 characters")
	    private String name;

	    @NotBlank(message = "Surname is required")
	    @Size(max = 50, message = "Surname must be at most 50 characters")
	    private String surname;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Email must be valid")
	    private String email;
}
