package Tests;

import Domain.IValidator;
import Domain.Medicament;
import Domain.MedicamentValidator;
import Repository.IRepository;
import Repository.InMemoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicamentRepositoryTest {

    @org.junit.jupiter.api.Test
    void findByIdWithExistingIdShouldReturnCorrectCake() {

        IValidator<Medicament> medicamentValidator = (IValidator<Medicament>) new MedicamentValidator();
        IRepository<Medicament> repo = new InMemoryRepository<>(medicamentValidator);
        Medicament added = new Medicament("1", "test", "test", 100,true);
        repo.upsert(added);
        Medicament found = repo.findById("1");
        assertNotNull(found, "Returned null for existing id!");
        assertEquals("1", found.getId(), String.format("Returned id %s instead of correct id=1!", found.getId()));
        assertEquals("test", found.getName(), String.format("Returned name=%s instead of %s", found.getName(), added.getName()));
        assertEquals("test", found.getManufacturer(), String.format("Returned manufacturer=%s instead of %s", found.getManufacturer(), added.getManufacturer()));
        assertEquals(100, found.getPrice(), String.format("Returned price=%s instead of %s", found.getPrice(), added.getPrice()));
        assertEquals(true, found.isPrescriptionNeeded(), String.format("Returned prescriptionNeeded=%s instead of %s", found.isPrescriptionNeeded(), added.isPrescriptionNeeded()));
    }

    @org.junit.jupiter.api.Test
    void upsertShouldInsertAndUpdateMed() {
        IValidator<Medicament> medicamentValidator = new MedicamentValidator();
        IRepository<Medicament> repo = new InMemoryRepository<>(medicamentValidator);
        Medicament med1 = new Medicament("1", "test", "test", 100,true);
        Medicament med2 = new Medicament("2", "test", "test", 100,true);
        Medicament med1Dupe = new Medicament("1", "test", "test", 100,true);
        repo.upsert(med1);
        List<Medicament> all = repo.getAll();
        assertEquals(1,all.size());
        assertEquals(med1,all.get(0));
/*
        try {
            repo.upsert(med1Dupe);
            fail("Exception not thrown for duplicate med id!");
        } catch  (RuntimeException rex) {
            assertTrue(true);
        }*/
    }
/*
    @org.junit.jupiter.api.Test
    void removeShouldRemoveMedWithExistingId(){
        IValidator<Medicament> medicamentValidator = (IValidator<Medicament>) new MedicamentValidator();
        IRepository<Medicament> repo = new InMemoryRepository<>(medicamentValidator);
        Medicament med1 = new Medicament("1", "test", "test", 100,true);
        Medicament med2 = new Medicament("2", "test", "test", 100,true);
        repo.upsert(med1);
        repo.upsert(med2);
        List<Medicament> all = repo.getAll();
        assertEquals(all.size(),2);
        repo.remove(med2.getId());
        assertEquals(all.size(),1);
        // avem 2 obiecte in lista, stergem 1, testam size sa fie actual 1
    } */

    @org.junit.jupiter.api.Test
    void getAllShouldReturnAllMeds() {
        IValidator<Medicament> medicamentValidator = (IValidator<Medicament>) new MedicamentValidator();
        IRepository<Medicament> repo = new InMemoryRepository<>(medicamentValidator);
        Medicament med1 = new Medicament("1", "test", "test", 100,true);
        Medicament med2 = new Medicament("2", "test", "test", 100,true);
        repo.upsert(med1);
        repo.upsert(med2);
        List<Medicament> all = repo.getAll();
        assertEquals(all, repo.getAll());
    }


}
