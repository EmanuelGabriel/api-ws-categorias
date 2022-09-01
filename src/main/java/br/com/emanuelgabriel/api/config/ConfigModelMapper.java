package br.com.emanuelgabriel.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Configuration
public class ConfigModelMapper {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
