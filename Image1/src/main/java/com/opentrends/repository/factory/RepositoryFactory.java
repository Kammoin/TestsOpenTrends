package com.opentrends.repository.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Factory for hide repositories implementations.
 * 
 * @author iorodriguez
 *
 */
@Component
public class RepositoryFactory {

	@Autowired
	GitLabRepository gitLabRepository;
	
	/**
	 * 
	 * @param repositoryType
	 * @return
	 */
	public Repository getRepositoryService(RepositoryType repositoryType) {
		
		if (repositoryType == null) {
			return null;
		}
		
		if (RepositoryType.GITLAB.equals(repositoryType)) {
			return gitLabRepository;

		} 
		
		//XXX: Add more future implementations of repositories...

		return null;
	}

}
