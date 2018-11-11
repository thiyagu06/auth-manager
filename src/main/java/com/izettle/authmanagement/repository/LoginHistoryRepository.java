package com.izettle.authmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.izettle.authmanagement.entity.LoggedinHistoryEntity;

/**
 * The loginHistory repository.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Repository
public interface LoginHistoryRepository extends PagingAndSortingRepository<LoggedinHistoryEntity, String> {

	/**
	 * The method to find the all the successful loggedin history filtered by user
	 * and page params.
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<LoggedinHistoryEntity> findAllByUserIdAndSuccessTrue(String userId, Pageable page);
}
