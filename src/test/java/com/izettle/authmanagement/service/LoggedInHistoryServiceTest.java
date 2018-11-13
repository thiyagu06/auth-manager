package com.izettle.authmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.izettle.authmanagement.dto.login.LoginAttempt;
import com.izettle.authmanagement.entity.LoginAttemptEntity;
import com.izettle.authmanagement.repository.LoginAttemptRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoggedInHistoryServiceTest {

	@Mock
	private LoginAttemptRepository loginHistoryRepositoryMock;

	@InjectMocks
	private LoginAttemptServiceImpl loggedinHistoryService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLoggedInHistorySaveSuccess() {
		LoginAttempt loggedinHistory = Mockito.mock(LoginAttempt.class);
		LoginAttemptEntity loginAttemptEntity = Mockito.mock(LoginAttemptEntity.class);
		Mockito.when(loginHistoryRepositoryMock.save(Mockito.any(LoginAttemptEntity.class)))
				.thenReturn(loginAttemptEntity);
		loggedinHistoryService.createSuccessLoginAttempt(loggedinHistory);
		ArgumentCaptor<LoginAttemptEntity> pageArgument = ArgumentCaptor.forClass(LoginAttemptEntity.class);
		Mockito.verify(loginHistoryRepositoryMock, Mockito.times(1)).save(pageArgument.capture());
		Mockito.verifyNoMoreInteractions(loginHistoryRepositoryMock);
	}

	@Test
	public void getLoggedInSuccessHistory() {
		List<LoginAttemptEntity> expected = new ArrayList<>();
		Page<LoginAttemptEntity> expectedPage = new PageImpl<>(expected);
		Mockito.when(loginHistoryRepositoryMock.findAllByUserIdAndSuccessTrue(Mockito.anyString(),
				Mockito.any(Pageable.class))).thenReturn(expectedPage);
		loggedinHistoryService.getSuccessfulLoginAttempts("Thiyagu103@gmail.com", Mockito.mock(Pageable.class));
		ArgumentCaptor<Pageable> pageArgument = ArgumentCaptor.forClass(Pageable.class);
		Mockito.verify(loginHistoryRepositoryMock, Mockito.times(1)).findAllByUserIdAndSuccessTrue(Mockito.anyString(),
				pageArgument.capture());
		Mockito.verifyNoMoreInteractions(loginHistoryRepositoryMock);
	}
}
