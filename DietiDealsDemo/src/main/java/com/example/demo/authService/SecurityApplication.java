//package com.example.demo.authService;
//
//import com.example.demo.authService.auth.AuthenticationService;
//import com.example.demo.authService.auth.RegisterRequest;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//public class SecurityApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(SecurityApplication.class, args);
//	}
//
//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			var admin = RegisterRequest.builder()
//					.nome("Federico")
//					.cognome("Gargiulo")
//					.email("fede@mail.com")
//					.password("passwordf")
//					.build();
//			System.out.println("Fede token: " + service.register(admin).getAccessToken());
//
//			var manager = RegisterRequest.builder()
//					.nome("Gianmarco")
//					.cognome("Lembo")
//					.email("lembo@mail.com")
//					.password("passwordl")
//					.build();
//			System.out.println("Gian token: " + service.register(manager).getAccessToken());
//
//		};
//	}
//}
