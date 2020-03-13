package com.opentrends.repository.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opentrends.repository.bean.RepositorySpec;
import com.opentrends.repository.factory.RepositoryFactory;
import com.opentrends.repository.factory.RepositoryType;
import com.opentrends.repository.repositories.RepoSpecRepository;

import lombok.Data;

/**
 * Class for repository service.
 * 
 * @author iorodriguez
 *
 */
@Service
@Data
public class RepositoryService {

	@Autowired
	RepositoryFactory repositoryFactory;

	@Autowired
	RepoSpecRepository repoSpecRepository;

	/**
	 * 
	 * @param repositorySpec
	 * @param repositoryType
	 * @return
	 */
	@Transactional
	public Optional<String> initRepository(RepositorySpec repositorySpec, RepositoryType repositoryType) {

		Optional<String> webUrl = repositoryFactory.getRepositoryService(repositoryType).initRepository(repositorySpec);
		
		// If I obtain a WebURL, I'll save the repo specs on DB. 
		// With that I can check if a repo exists, has a specific branch or tag... without make a call to GitLab or any repo service and, so, saving time.
		if(webUrl.isPresent()){
			repositorySpec.setWebUrl(webUrl.get());
			repoSpecRepository.save(repositorySpec);
		}
		
		return webUrl;
	}

}
