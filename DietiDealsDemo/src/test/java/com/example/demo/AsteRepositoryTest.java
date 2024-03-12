package com.example.demo;

import com.example.demo.data_access.AsteRepository;
import com.example.demo.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {AsteRepository.class})
@EnableAutoConfiguration
class AsteRepositoryTest {

    Asta a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;

    @Autowired
    public AsteRepository asteTestRep;

    @BeforeEach
    public void setUpDb() {
        a1 = new AstaClassica(-1L, null, "Asta uno", "...", "categoria uno", null, false, null, null, 0F);
        a2 = new AstaClassica(-1L, null, "Asta due", "...", "categoria due", null, false, null, null, 0F);
        a3 = new AstaSilenziosa(-1L, null, "Asta tre", "...", "categoria uno", null, false, null, null);
        a4 = new AstaInversa(-1L, null, "Asta quattro", "...", "categoria due", null, false, null, null, 0F);
        a5 = new AstaSilenziosa(-1L, null, "Asta articolo uno", "...", "categoria due", null, false, null, null);
        a6 = new AstaInversa(-1L, null, "Asta articolo due", "...", "categoria uno", null, false, null, null, 0F);
        a7 = new AstaClassica(-1L, null, "Asta articolo tre", "...", "categoria due", null, false, null, null, 0F);
        a8 = new AstaInversa(-1L, null, "Asta articolo quattro", "...", "categoria due", null, false, null, null, 0F);
        a9 = new AstaClassica(-1L, null, "Astaarticolouno", "...", "categoria uno", null, false, null, null, 0F);
        a10 = new AstaSilenziosa(-1L, null, "Astaarticolodue", "...", "categoria uno", null, false, null, null);
        a11 = new AstaInversa(-1L, null, "Astaarticolotre", "...", "categoria uno", null, false, null, null, 0F);
        a12 = new AstaSilenziosa(-1L, null, "Astaarticoloquattro", "...", "categoria due", null, false, null, null);
        asteTestRep.saveAll(List.of(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12));
    }

    @Test
    public void repFunctionalityTest() {
        assertNotNull(asteTestRep);
    }

    @Test
    public void astaDBsetupBasicTest(){
        List<Asta >result = asteTestRep.searchAste("%", "%", "Asta", 0);
        assertFalse(result.isEmpty());
    }


    @Test
    public void findAllAstePag0() {
        List<Asta> result = asteTestRep.searchAste("%", "%", "Asta", 0);
        assertEquals(10, result.size());
    }

    @Test
    public void findAllAstePag1() {
        List<Asta> result = asteTestRep.searchAste("%", "%", "Asta", 10);
        assertEquals(2, result.size());
    }

    @Test
    public void findAllAstePagInvalid() {
        assertThrows(DataIntegrityViolationException.class, () -> {asteTestRep.searchAste("%", "%", "Asta", -1);});
    }

    @Test
    public void findAllAsteInvalidType() {
        List<Asta> result = asteTestRep.searchAste("Asta", "%", "Asta", 0);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllAsteBlankType() {
        List<Asta> result = asteTestRep.searchAste("", "%", "Asta", 0);
        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllAsteClassiche() {
        List<Asta> result = asteTestRep.searchAste("AstaClassica", "%", "Asta", 0);
        List<Asta> aspected = List.of(a1, a2, a7, a9);
        assertEquals(aspected, result);
    }

    @Test
    public void cercaAsteByKeywordTest() {
        List<Asta> result = asteTestRep.searchAste("%", "%", "articolo", 0);
        List<Asta> aspected = List.of(a5, a6, a7, a8, a9, a10, a11, a12);
        assertEquals(aspected, result);
    }

    @Test
    public void cercaAsteBlankKeyword() {
        List<Asta> result = asteTestRep.searchAste("%", "%", "", 0);
        assertTrue(!result.isEmpty());
    }

    @Test
    public void cercaAsteByCategoriaTest() {
        List<Asta> result = asteTestRep.searchAste("%", "categoria uno", "Asta", 0);
        List<Asta> aspected = List.of(a1, a3, a6, a9, a10, a11);
        assertEquals(aspected, result);
    }

    @Test
    public void cercaAsteInverseByCategoriaDue() {
        List<Asta> result = asteTestRep.searchAste("AstaInversa", "categoria due", "Asta", 0);
        List<Asta> aspected = List.of(a4, a8);
        assertEquals(aspected, result);
    }

    @Test
    public void cercaAsteClassicheByKeywordByCategoriaUno() {
        List<Asta> result = asteTestRep.searchAste("AstaClassica", "categoria uno", "due", 0);
        List<Asta> aspected = List.of();
        assertEquals(aspected, result);
    }

    @Test
    public void cercaAsteSilenzioseByKeywordByCategoriaDue() {
        List<Asta> result = asteTestRep.searchAste("AstaSilenziosa", "categoria uno", "tre", 0);
        List<Asta> aspected = List.of(a3);
        assertEquals(aspected, result);
    }

    @Test
    public void cercaAsteEscludeAsteScadute() {
        Asta astaScaduta = new AstaClassica(-1L, null, "Scaduta", "...", "...", null, true, null, null, 0F);
        asteTestRep.save(astaScaduta);
        List<Asta> result = asteTestRep.searchAste("%", "%", "Scaduta", 0);
        assertTrue(result.isEmpty());
    }
}