package com.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableResourceServer
@SpringBootApplication
@EnableAuthorizationServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Authserver {

	public static void main(String[] args) {
		SpringApplication.run(Authserver.class, args);
	}

	@GetMapping("/hello")
	//@PreAuthorize("hasRole('ADMIN')")
	public String hello() {
		return "Hello From Resource Server";
	}

}
