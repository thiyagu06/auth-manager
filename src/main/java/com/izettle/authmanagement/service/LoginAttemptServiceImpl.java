package com.izettle.authmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.izettle.authmanagement.dto.login.LoginAttempt;
import com.izettle.authmanagement.entity.LoginAttemptEntity;
import com.izettle.authmanagement.repository.LoginAttemptRepository;

/**
 * The implementation of loginAttempt service.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

	/**
	 * The loginAttemptRepository field
	 */
	@Autowired
	private LoginAttemptRepository loginAttemptRepository;

	/* (non-Javadoc)
	 * @see com.izettle.authmanagement.service.LoggedinHistoryService#createSuccessLogin(com.izettle.authmanagement.dto.login.LoggedInHistory)
	 */
	@Override
	public void createSuccessLoginAttempt(LoginAttempt loginAttempt) {
		LoginAttemptEntity loginAttemptEntity = new LoginAttemptEntity();
		loginAttempt.setSuccess(true);
		BeanUtils.copyProperties(loginAttempt, loginAttemptEntity);
		loginAttemptRepository.save(loginAttemptEntity);
	}

	/* (non-Javadoc)
	 * @see com.izettle.authmanagement.service.LoggedinHistoryService#getLoggedInSuccessHistory(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<LoginAttempt> getSuccessfulLoginAttempts(String userId, Pageable pageRequest) {
		List<LoginAttemptEntity> loginAttemptEntities = loginAttemptRepository
				.findAllByUserIdAndSuccessTrue(userId, pageRequest).getContent();
		List<LoginAttempt> loggedinHistories = new ArrayList<>();
		for (LoginAttemptEntity loginAttemptEntity : loginAttemptEntities) {
			LoginAttempt loginAttempt = new LoginAttempt();
			BeanUtils.copyProperties(loginAttemptEntity, loginAttempt);
			loggedinHistories.add(loginAttempt);
		}
		return loggedinHistories;
	}
}
