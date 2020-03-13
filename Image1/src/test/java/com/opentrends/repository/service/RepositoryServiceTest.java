package com.opentrends.repository.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.opentrends.repository.bean.RepositorySpec;
import com.opentrends.repository.factory.GitLabRepository;
import com.opentrends.repository.factory.RepositoryFactory;
import com.opentrends.repository.factory.RepositoryType;

@RunWith(SpringRunner.class)
public class RepositoryServiceTest {

	@MockBean
	RepositoryFactory repositoryFactory;
	
	@MockBean
	GitLabRepository gitLabRepository;

	@Test
	public void whenInitRepositoryIsOK_thenObtainWebUrlNotEmpty() throws Exception {
		
		RepositorySpec repositorySpec = new RepositorySpec();
		
		Mockito.when(repositoryFactory.getRepositoryService(RepositoryType.GITLAB)).thenReturn(gitLabRepository);
		Mockito.when(gitLabRepository.initRepository(repositorySpec)).thenReturn(Optional.of("https://groupname.example.io/projectname"));
		
		Optional<String> webUrl = repositoryFactory.getRepositoryService(RepositoryType.GITLAB).initRepository(repositorySpec);
		
		assertEquals(Optional.of("https://groupname.example.io/projectname"), webUrl);

		
	}



}
