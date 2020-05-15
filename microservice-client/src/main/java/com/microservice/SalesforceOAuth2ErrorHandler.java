package com.microservice;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.oauth2.client.http.OAuth2ErrorHandler;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

public class SalesforceOAuth2ErrorHandler extends OAuth2ErrorHandler {

	public SalesforceOAuth2ErrorHandler(OAuth2ProtectedResourceDetails resource) {
		super(resource);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws CredentialsExpiredException, IOException {
		if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			throw new InvalidTokenException("401 Unauthorized");
		}
		super.handleError(response);
	}

}
