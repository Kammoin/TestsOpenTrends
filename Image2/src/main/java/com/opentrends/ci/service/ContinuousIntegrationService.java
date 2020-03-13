package com.opentrends.ci.service;

import java.io.IOException;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.opentrends.ci.bean.Build;
import com.opentrends.ci.repositories.BuildRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for Jenkins
 * 
 * @author iorodriguez
 *
 */
@Service
@Slf4j
@Data
@ConfigurationProperties("jenkins")
public class ContinuousIntegrationService {

	private static final String ACCEPT = "Accept";

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE = "Content-type";

	private String url;
	private String user;
	private String password;

	@Autowired
	private BuildRepository buildRepository;

	/**
	 * Post the build to rest service of jenkins server via HttpClient. We can use other
	 * advanced options like RestTemplate (and LoadBalance Ribbon in scenarios with replicas) or Feign
	 * @param buildSpec
	 * @return {@link HttpStatus}
	 */
	public HttpStatus build(Build buildSpec) {

		
		CloseableHttpClient client = null;
		
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	
		try{
			
			//XXX:I make some decisions here. To simplify I assume that all parameters of build are necessary and mandatory.
			//Also I assume that Jenkins has been configured with option "Run with parameters" and expect ALL parameters to launch the job/pipeline.
			HttpPost httpPost = new HttpPost(constructPostURL(buildSpec));
	
			addHeaders(httpPost);
			log.debug("build HttPost::{}", httpPost);
			
			client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			
			log.debug("build StatusCodeResponse::{}", response.getStatusLine().getStatusCode());
			log.debug("Response::{}", response);
			
			return 200 == response.getStatusLine().getStatusCode() ? HttpStatus.OK : httpStatus;
			
		} catch (AuthenticationException e) {
			log.error("ERROR::Build::Authentication error::", e);
			httpStatus = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED;
//			httpStatus = HttpStatus.OK;  //Uncomment to mock a 200 response.
			
		} catch (ClientProtocolException e) {
			log.error("ERROR::Build::Client Protocol error::", e);
			httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
//			httpStatus = HttpStatus.OK;  //Uncomment to mock a 200 response.
			
		} catch (IOException e) {
			log.error("ERROR::Build::IOException error::", e);
			httpStatus = HttpStatus.BAD_GATEWAY;
//			httpStatus = HttpStatus.OK;  //Uncomment to mock a 200 response.
			
		} finally {
			if(null != client)
				try {
					client.close();
				} catch (IOException e) {
					log.error("", e);
				}
		}
		return httpStatus;
		
		

	}

	/**
	 * @param buildSpec
	 * @return {@link String}
	 */
	private String constructPostURL(Build buildSpec) {
		StringBuilder urlToPostBuild = new StringBuilder(url);
		urlToPostBuild.append("/job/");
		urlToPostBuild.append(buildSpec.getName());
		urlToPostBuild.append("/buildWithParameters?BuildId=");
		urlToPostBuild.append(buildSpec.getBuildId());
		urlToPostBuild.append("&PathRepo=");
		urlToPostBuild.append(buildSpec.getPathRepo());
		urlToPostBuild.append("&Version=");
		urlToPostBuild.append(buildSpec.getVersion());
		
		log.debug("constructPostURL::{}", urlToPostBuild.toString());
		
		return urlToPostBuild.toString();
	}

	/**
	 * Add headers (include credentials) to HttPost.
	 * @param httpPost
	 * @throws AuthenticationException
	 */
	private void addHeaders(HttpPost httpPost) throws AuthenticationException {
		
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user,password);
		httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
		httpPost.addHeader(CONTENT_TYPE, APPLICATION_JSON);
		httpPost.addHeader(ACCEPT, APPLICATION_JSON);
	}

}
