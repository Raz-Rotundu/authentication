package com.example.authentication.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class UserEntity {

	@Id
	private UUID id;
	
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	private String userType;
	
	
	/**
	 * Function called on create to assign a UUID to user entity if it does not already exist
	 */
	@PrePersist
	protected void onCreate() {
		if(this.id == null) {
			this.id = UUID.randomUUID();
		}
	}
	
}

