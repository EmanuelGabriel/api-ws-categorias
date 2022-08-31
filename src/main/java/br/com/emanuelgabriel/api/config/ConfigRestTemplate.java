package br.com.emanuelgabriel.api.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Configuration
public class ConfigRestTemplate {
	
	@Primary
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(1000))
				.setReadTimeout(Duration.ofMillis(1000))
				.build();
	}

		@Bean
		public RestTemplate getRestTemplate(@Value("${service.BearerToken}") String bearerToken) {
			var restTemplate = new RestTemplate();
			restTemplate.getInterceptors().add((request, body, clientHttpRequestExecution) -> {
				HttpHeaders headers = request.getHeaders();
				if (!headers.containsKey("Authorization")) {
					var token = bearerToken.toLowerCase().startsWith("bearer") ? bearerToken : "Bearer " + bearerToken;
					request.getHeaders().add("Authorization", token);
				}
				return clientHttpRequestExecution.execute(request, body);
			});
			return restTemplate;
		}
}
