package com.br.keycloak.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import com.br.keycloak.component.HttpComponent;
import com.br.keycloak.models.User;
import com.br.keycloak.utils.HttpParamsMapBuilder;

@Service
public class LoginService {

	@Value("${keycloak.auth-server-url}")
	private String keycloakServerUrl;

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.resource}")
	private String clientId;

	@Value("${keycloak.credentials.secret}")
	private String clientSecret;

	@Value("${keycloak.user-login.grant-type}")
	private String grantType;

	@Autowired
	private HttpComponent httpComponent;

	public ResponseEntity<String> login(User user) {
		httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = HttpParamsMapBuilder.builder().withClient(clientId)
				.withClientSecret(clientSecret).withGrantType(grantType).withUserName(user.getUsername())
				.withPassword(user.getPassword()).build();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpComponent.httpHeaders());

		try {
			ResponseEntity<String> response = httpComponent.restTemplate()
					.postForEntity(keycloakServerUrl + "/protocol/openid-connect/token", request, String.class);
			return ResponseEntity.ok(response.getBody());
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}
	
	public ResponseEntity<String> refreshToken(String refreshToken) {
		httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = HttpParamsMapBuilder.builder()
				.withClient(clientId)
				.withClientSecret(clientSecret)
				.withGrantType("refresh_token")
				.withRefreshToken(refreshToken)
				.build();

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpComponent.httpHeaders());

		try {
			ResponseEntity<String> response = httpComponent.restTemplate()
					.postForEntity(keycloakServerUrl + "/protocol/openid-connect/token", request, String.class);
			return ResponseEntity.ok(response.getBody());
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}

}
