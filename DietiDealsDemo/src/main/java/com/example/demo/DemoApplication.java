package com.example.demo;

import com.example.demo.data_access.AsteRepository;
import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(UtentiRepository userRep, AsteRepository asteRep) {
		return args -> {
			Utente user = new Utente(
					"prova",
					"mario",
					"rossi",
					"1234",
					"...",
					"Napoli",
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(user);
			Asta classica = new AstaClassica(1L, Timestamp.from(Instant.now()), "cosa1", "una cosa", "cat1", null, false, user, new ArrayList<Offerta>(), 0f);
			Asta silenziosa = new AstaSilenziosa(2L, Timestamp.from(Instant.now()), "cosa2", "una cosa", "cat1", null, false, user, new ArrayList<Offerta>());
			asteRep.save(classica);
			asteRep.save(silenziosa);
		};
	}
}
