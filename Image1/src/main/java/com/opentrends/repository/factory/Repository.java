package com.opentrends.repository.factory;

import java.util.Optional;

import com.opentrends.repository.bean.RepositorySpec;

/**
 * Interface to implement in every repository service
 * 
 * @author iorodriguez
 *
 */
public interface Repository {
	
	/**
	 * 
	 * @param repositorySpec 
	 * @return
	 */
	public Optional<String> initRepository(RepositorySpec repositorySpec);
	
	/**
	 * 
	 * @param branchName 
	 */
	public void createBranch(String branchName);
	
	/**
	 * 
	 * @param tagName 
	 */
	public void createTag(String tagName);



}
