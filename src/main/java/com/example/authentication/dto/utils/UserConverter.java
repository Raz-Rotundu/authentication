package com.example.authentication.dto.utils;

import com.example.authentication.dto.UserDto;
import com.example.authentication.entity.UserEntity;

/**
 * @author Razvan
 * Converter class contains static methods to convert between UserDto and UserEntity objects
 */
public class UserConverter {
	
	/**
	 * Converts given UserEntity into a UserDto
	 * @param entity the UserEntity to be converted
	 * @return a UserDto containing the same data
	 */
	public static UserDto toUserDto(UserEntity entity) {
		return UserDto.builder()
				.email(entity.getEmail())
				.password(entity.getPassword())
				.userType(entity.getUserType())
				.build();
	}
	
	/**
	 * Converts given UserDto into the equivalent UserEntity
	 * @param dto the UserDto to be converted
	 * @return a UserEntity containing the same data and NULL ID
	 */
	public static UserEntity toUserEntity(UserDto dto) {
		return UserEntity.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.userType(dto.getUserType())
				.build();
	}
}
