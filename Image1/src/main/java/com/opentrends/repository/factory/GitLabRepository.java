package com.opentrends.repository.factory;

import static com.opentrends.repository.factory.RepositoryConstants.DEVELOP;
import static com.opentrends.repository.factory.RepositoryConstants.MASTER;

import java.util.Optional;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.opentrends.repository.bean.RepositorySpec;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * Repository service specific to GitLab
 * 
 * @author iorodriguez
 *
 */
@Slf4j
@ConfigurationProperties("gitlab")
@Data
@Service
public class GitLabRepository implements Repository{
	
	private String url;
	private String  privateToken;
	/*
	 * (non-Javadoc)
	 * @see com.opentrends.repository.factory.Repository#initRepository()
	 */
	@Override
	public Optional<String> initRepository(RepositorySpec repositorySpec) {
		
		Optional<String> webUrl = Optional.empty();
		GitLabApi gitLabApi = null;
		
		try {
			
			log.debug("Url::{}, privateToken::{}", url, privateToken);
			
			// Create a GitLabApi instance to communicate with your GitLab
			// server
			gitLabApi = new GitLabApi(url, privateToken);
			
			if(null != gitLabApi){
				
//				Create a new project
				Project projectSpec = new Project().withName(repositorySpec.getName()).withDescription(repositorySpec.getDescription())
						.withIssuesEnabled(true).withMergeRequestsEnabled(true).withWikiEnabled(true).withPublic(true);
				
				Project newProject = gitLabApi.getProjectApi().createProject(projectSpec);
				
				createBranch(gitLabApi, newProject.getId(), DEVELOP, MASTER);
				
				createTag(gitLabApi, newProject.getId(), repositorySpec.getTagName(), MASTER);
	
				return Optional.of(newProject.getWebUrl());
				
			}
			
		} catch (GitLabApiException gitEx) {
			log.error("ERROR::initRepository", gitEx);
//			return Optional.of("https://succes.es");  //Uncomment to mock a 200 response.
			
		} finally {
			if(null != gitLabApi)
				gitLabApi.close();
		}
		
		return webUrl;

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.opentrends.repository.factory.Repository#createBranch(java.lang.String)
	 */
	@Override
	public void createBranch(String branchName) {
		
		// TODO: Out of scope
		// createBranch(GitLabApi gitLabApi, Project newProject);
		
		
	}


	/*
	 * (non-Javadoc)
	 * @see com.opentrends.repository.factory.Repository#createTag(java.lang.String)
	 */
	@Override
	public void createTag(String tagName) {
		
		// TODO: Out of scope
		// createTag(tagName, gitLabApi, idProject);
		
		
	}

	/**
	 * 
	 * @param gitLabApi
	 * @param idProject
	 * @param branchName
	 * @param ref
	 * @throws GitLabApiException
	 */
	private void createBranch(GitLabApi gitLabApi, Integer idProject, String branchName, String ref) throws GitLabApiException {
		gitLabApi.getRepositoryApi().createBranch(idProject, branchName, ref);
	}
	
	/**
	 * 
	 * @param gitLabApi
	 * @param idProject
	 * @param tagName
	 * @param ref
	 * @throws GitLabApiException
	 */
	private void createTag(GitLabApi gitLabApi, Integer idProject, String tagName, String ref)
			throws GitLabApiException {
		gitLabApi.getTagsApi().createTag(idProject, tagName, ref);
	}

}
