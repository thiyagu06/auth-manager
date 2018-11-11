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

import com.izettle.authmanagement.dto.login.LoggedInHistory;
import com.izettle.authmanagement.entity.LoggedinHistoryEntity;
import com.izettle.authmanagement.repository.LoginHistoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoggedInHistoryServiceTest {

	@Mock
	private LoginHistoryRepository loginHistoryRepositoryMock;

	@InjectMocks
	private LoggedInHistoryServiceImpl loggedinHistoryService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLoggedInHistorySaveSuccess() {
		LoggedInHistory loggedinHistory = Mockito.mock(LoggedInHistory.class);
		LoggedinHistoryEntity loggedinHistoryEntity = Mockito.mock(LoggedinHistoryEntity.class);
		Mockito.when(loginHistoryRepositoryMock.save(Mockito.any(LoggedinHistoryEntity.class)))
				.thenReturn(loggedinHistoryEntity);
		loggedinHistoryService.createSuccessLogin(loggedinHistory);
		ArgumentCaptor<LoggedinHistoryEntity> pageArgument = ArgumentCaptor.forClass(LoggedinHistoryEntity.class);
		Mockito.verify(loginHistoryRepositoryMock, Mockito.times(1)).save(pageArgument.capture());
		Mockito.verifyNoMoreInteractions(loginHistoryRepositoryMock);
	}

	@Test
	public void getLoggedInSuccessHistory() {
		List<LoggedinHistoryEntity> expected = new ArrayList<>();
		Page<LoggedinHistoryEntity> expectedPage = new PageImpl<>(expected);
		Mockito.when(loginHistoryRepositoryMock.findAllByUserIdAndSuccessTrue(Mockito.anyString(),
				Mockito.any(Pageable.class))).thenReturn(expectedPage);
		loggedinHistoryService.getLoggedInSuccessHistory("Thiyagu103@gmail.com", Mockito.mock(Pageable.class));
		ArgumentCaptor<Pageable> pageArgument = ArgumentCaptor.forClass(Pageable.class);
		Mockito.verify(loginHistoryRepositoryMock, Mockito.times(1)).findAllByUserIdAndSuccessTrue(Mockito.anyString(),
				pageArgument.capture());
		Mockito.verifyNoMoreInteractions(loginHistoryRepositoryMock);
	}
}
