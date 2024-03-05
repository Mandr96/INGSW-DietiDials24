package com.example.demo;

import com.example.demo.data_access.AsteRepository;
import com.example.demo.data_access.OfferteRepository;
import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class DemoApplication {


	//QUESTO PEZZO DI CODICE SERVE TEMPORANEAMENTE PER CREARE UN UTENTE CON PASSWORD CODIFICATA
	private final PasswordEncoder passwordEncoder;
    public DemoApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UtentiRepository userRep, AsteRepository asteRep, OfferteRepository offerteRep) {
		return args -> {
			Utente user = new Utente(
					"prova",
					"mario",
					"rossi",
					//necessario per rendere possibile il login (dato che fa la decodifica)
					passwordEncoder.encode("1234"),
					"...",
					"Napoli",
					null,
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(user);
//
//			Asta classica = new AstaClassica(1L, Timestamp.from(Instant.now()), "cosa", "una cosa", "cat1", null, false, user, new ArrayList<Offerta>(), 0f);
//			asteRep.save(classica);
//
//			Asta silenziosa = new AstaSilenziosa(2L, Timestamp.from(Instant.now().plusSeconds(100000L)), "cosa prova", "una cosa", "cat1", null, false, user, new ArrayList<Offerta>());
//			asteRep.save(silenziosa);
//
//			classica = new AstaInversa(1L, Timestamp.from(Instant.now().plusSeconds(100000L)), "cosa 2", "una cosa 2", "cat1", null, false, user, new ArrayList<Offerta>(), 0f);
//			asteRep.save(classica);
//
//			classica = new AstaClassica(1L, Timestamp.from(Instant.now().plusSeconds(100000L)), "cosa 2", "una cosa 2", "cat1", null, false, user, new ArrayList<Offerta>(), 0f);
//			asteRep.save(classica);
//
//			offerteRep.save(new Offerta(1L,10f,null,true,user,classica));
//			//System.out.println(asteRep.searchAste("AstaClassica", "cat1", "cosa", 0));
		};
	}
}
