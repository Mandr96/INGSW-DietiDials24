package com.example.demo;

import com.example.demo.data_access.AsteRepository;
import com.example.demo.data_access.NotificheRepository;
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
	CommandLineRunner commandLineRunner(UtentiRepository userRep, AsteRepository asteRep, OfferteRepository offerteRep, NotificheRepository notificheRep) {
		return args -> {
			Utente venditore = new Utente(
					"prova",
					"Mario",
					"Rossi",
					//necessario per rendere possibile il login (dato che fa la decodifica)
					passwordEncoder.encode("1234"),
					"...",
					"Roma",
					null,
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(venditore);
			Utente compratore = new Utente(
					"prova2",
					"Fabio",
					"Sicignano",
					passwordEncoder.encode("5678"),
					"Sono una persona semplice",
					"Napoli",
					null,
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(compratore);
//
			Asta classica = new AstaClassica(-1L, Timestamp.from(Instant.now().plusSeconds(5L)), "cosa", "una cosa classica", "Sport", null, false, venditore, new ArrayList<Offerta>(), 10f);
			asteRep.save(classica);

			Asta silenziosa = new AstaSilenziosa(-1L, Timestamp.from(Instant.now().plusSeconds(100000L)), "cosa prova", "una cosa silenziosa", "Libri", null, false, venditore, new ArrayList<Offerta>());
			asteRep.save(silenziosa);

			Asta inversa  = new AstaInversa(-1L, Timestamp.from(Instant.now().plusSeconds(100000L)), "cosa inversa", "una cosa inversa", "Collezionismo", null, false, compratore, new ArrayList<Offerta>(), 50F);
			asteRep.save(inversa);

			offerteRep.save(new Offerta(-1L,15f,null,true, compratore, classica));
			offerteRep.save(new Offerta(-1L,40f,null,true, compratore, silenziosa));
			offerteRep.save(new Offerta(-1L,75f,null,true, venditore, inversa));
//			//System.out.println(asteRep.searchAste("AstaClassica", "cat1", "cosa", 0));

			notificheRep.save(new Notifica("Notifica prova", "...", false, venditore));
			notificheRep.save(new Notifica("Notifica prova 2", "...", false, venditore));
		};
	}
}
