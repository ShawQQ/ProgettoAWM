package it.unicam.cs.progetto;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendConfiguration {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
