package com.izettle.authmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.izettle.authmanagement.dto.login.LoggedInHistory;
import com.izettle.authmanagement.entity.LoggedinHistoryEntity;
import com.izettle.authmanagement.repository.LoginHistoryRepository;

/**
 * The implementation of LoggedInHistory service.
 * 
 * @author Thiyagu
 * @version 1.0
 *
 */
@Service
public class LoggedInHistoryServiceImpl implements LoggedinHistoryService {

	/**
	 * The loginHistoryRepository field
	 */
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;

	/* (non-Javadoc)
	 * @see com.izettle.authmanagement.service.LoggedinHistoryService#createSuccessLogin(com.izettle.authmanagement.dto.login.LoggedInHistory)
	 */
	@Override
	public void createSuccessLogin(LoggedInHistory loggedInHistory) {
		LoggedinHistoryEntity loggedinHistoryEntity = new LoggedinHistoryEntity();
		loggedInHistory.setSuccess(true);
		BeanUtils.copyProperties(loggedInHistory, loggedinHistoryEntity);
		loginHistoryRepository.save(loggedinHistoryEntity);
	}

	/* (non-Javadoc)
	 * @see com.izettle.authmanagement.service.LoggedinHistoryService#getLoggedInSuccessHistory(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<LoggedInHistory> getLoggedInSuccessHistory(String userId, Pageable pageRequest) {
		List<LoggedinHistoryEntity> loggedinHistoryEntities = loginHistoryRepository
				.findAllByUserIdAndSuccessTrue(userId, pageRequest).getContent();
		List<LoggedInHistory> loggedinHistories = new ArrayList<>();
		for (LoggedinHistoryEntity loggedinHistoryEntity : loggedinHistoryEntities) {
			LoggedInHistory loggedInHistory = new LoggedInHistory();
			BeanUtils.copyProperties(loggedinHistoryEntity, loggedInHistory);
			loggedinHistories.add(loggedInHistory);
		}
		return loggedinHistories;
	}
}
