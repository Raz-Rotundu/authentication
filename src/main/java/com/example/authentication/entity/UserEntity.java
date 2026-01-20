package com.example.authentication.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Razvan
 * Entity class representing the login information of a user within the system
 */

@Entity
@Table(name = "users", uniqueConstraints = @
UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	private UUID id;
	
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private String userType;
	
}
