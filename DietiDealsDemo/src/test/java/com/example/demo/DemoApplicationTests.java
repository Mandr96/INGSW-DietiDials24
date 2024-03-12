package com.example.demo;

import com.example.demo.data_access.AsteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class DemoApplicationTests {

	@Autowired
	public AsteRepository asteTestRep;

	@Test
	public void repFunctionalityTest() {
		assertNotNull(asteTestRep);
	}

	@Test
	public void contextLoads() {
	}



}
