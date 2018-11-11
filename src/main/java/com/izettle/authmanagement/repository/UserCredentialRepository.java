package com.izettle.authmanagement.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.izettle.authmanagement.entity.UserCredentialEntity;

/**
 * The UserCredential repository.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Repository
public interface UserCredentialRepository extends CrudRepository<UserCredentialEntity, String> {

	/**
	 * retrieve the user credential by userId.
	 * 
	 * @param userid
	 * @return
	 */
	public Optional<UserCredentialEntity> findByUserid(String userid);
}
