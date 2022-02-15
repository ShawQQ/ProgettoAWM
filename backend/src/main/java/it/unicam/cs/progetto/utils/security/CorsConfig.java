package it.unicam.cs.progetto.utils.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.
						addMapping("/api/**").
						allowedMethods("GET", "POST", "PUT", "DELETE").
						allowedOrigins("http://localhost:4200").
						allowedOrigins("http://localhost:8100").
						allowedOrigins("*").
						allowedHeaders("*");
			}
		};
	}
}
