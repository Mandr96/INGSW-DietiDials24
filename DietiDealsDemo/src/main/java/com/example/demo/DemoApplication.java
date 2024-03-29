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
			// Primo scenario
			userRep.save(venditore);
			Utente enrico = new Utente(
					"enrico.f@gmail.com",
					"Enrico",
					"Fausti",
					passwordEncoder.encode("5678"),
					"Sono una persona semplice",
					"Napoli",
					null,
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(enrico);
			Utente fabio = new Utente(
					"fabbio.sic@gmail.com",
					"Enrico",
					"Fausti",
					passwordEncoder.encode("pass"),
					"Super venditore super affidabile",
					"Napoli",
					null,
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(fabio);
			Utente franc = new Utente(
					"francipo@gmail.com",
					"Francesco",
					"Pollio",
					passwordEncoder.encode("pass2"),
					"Qui per comprare",
					"Napoli",
					null,
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(franc);
//
			Asta classica = new AstaClassica(-1L, Timestamp.from(Instant.now().plusSeconds(10000L)), "Controller PS5 come nuovo", "Vendo per inutilizzo. Il controller è come nuovo.", "Elettronica", null, false, fabio, new ArrayList<Offerta>(), 30f);
			asteRep.save(classica);
			Asta silenziosa = new AstaSilenziosa(-1L, Timestamp.from(Instant.now().plusSeconds(100000L)), "Controller PS5 usato", "Vendo controller usato per PS5. Non accetto al di sotto dei 35 euro", "Libri", null, false, venditore, new ArrayList<Offerta>());
			asteRep.save(silenziosa);

			Asta inversa  = new AstaInversa(-1L, Timestamp.from(Instant.now().plusSeconds(100000L)), "cosa inversa", "una cosa inversa", "Collezionismo", null, false, compratore, new ArrayList<Offerta>(), 50F);
			asteRep.save(inversa);

			offerteRep.save(new Offerta(-1L,39f,null,true, franc, classica));
			offerteRep.save(new Offerta(-1L,40f,null,true, franc, silenziosa));

			// Secondo scenario
			userRep.save(venditore);
			Utente ale = new Utente(
					"tiziocaio@libero.it",
					"Tizio",
					"Caio",
					passwordEncoder.encode("pwSicura1234"),
					"Sono una persona semplice",
					"Napoli",
					null,
					new ArrayList<Notifica>(),
					new ArrayList<Asta>(),
					new ArrayList<Offerta>()
			);
			userRep.save(ale);
			offerteRep.save(new Offerta(-1L,35f,null,true, ale, silenziosa));
		};
	}
}
