package com.microservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableOAuth2Client
@RestController
public class ClientApp {

	@Bean
	public OAuth2RestTemplate restTemplate() {
		return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext());
	}

	@Autowired
	private OAuth2RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ClientApp.class, args);
	}

	@Bean
	protected OAuth2ProtectedResourceDetails resource() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setAccessTokenUri("http://localhost:9090/oauth/token");
		details.setClientId("sami");
		details.setClientSecret(encoder.encode("sami"));
		details.setGrantType("password");
		return details;
	}

	@RequestMapping("/execute")
	public String execute(Principal principal) throws URISyntaxException {
		
		User user = (User) ((Authentication) principal).getPrincipal();
		URI uri = new URI("http://localhost:9090/hello");
		RequestEntity<String> request = new RequestEntity<String>(HttpMethod.POST, uri);
		AccessTokenRequest accessTokenRequest = this.restTemplate.getOAuth2ClientContext().getAccessTokenRequest();
		accessTokenRequest.set("username", user.getUsername());
		accessTokenRequest.set("password", user.getPassword());
		return restTemplate.exchange(request, String.class).getBody();
	}
	
}
