package com.example.demo;

import com.example.demo.data_access.AsteRepository;
import com.example.demo.model.*;
import org.assertj.core.annotations.Beta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnableJpaRepositories(basePackages = "com.example.demo.data_access")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AsteRepositoryTest {

    @Autowired
    public AsteRepository asteTestRep;

    @Test
    public void repFunctionalityTest() {
        assertNotNull(asteTestRep);
    }

    @Test
    public void searchAsteBasicTest() {
        asteTestRep.save(new AstaClassica(-1L, null, "cosa", "una cosa classica", "Sport", null, false, null, new ArrayList<Offerta>(), 10f));
        asteTestRep.save(new AstaSilenziosa(-1L, null, "cosa prova", "una cosa silenziosa", "Libri", null, false, null, null));
        asteTestRep.save(new AstaInversa(-1L, null, "inversa", "una cosa inversa", "Collezionismo", null, false, null, null, 50F));

        List<Asta> result = asteTestRep.searchAste("tutte", "tutte", "cosa", 0);
        assertEquals(3, result.size());
    }
}