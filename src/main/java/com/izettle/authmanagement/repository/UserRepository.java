package com.izettle.authmanagement.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.izettle.authmanagement.entity.UserEntity;

/**
 * The user repository.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

	/**
	 * Method to find the user user email.
	 * 
	 * @param email
	 * @return UserEntity.
	 */
	Optional<UserEntity> findByEmail(String email);
}
