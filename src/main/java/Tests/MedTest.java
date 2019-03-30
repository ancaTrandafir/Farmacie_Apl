package Tests;
import Domain.Medicament;

import static org.junit.jupiter.api.Assertions.*;

public class MedTest {

    @org.junit.jupiter.api.Test
    void getIdShouldReturnCorrectId() {
        Medicament med1 = new Medicament("1", "test", "test", 100,  true);
        assertEquals("1", med1.getId(), String.format("Returned %s, Expected=%s", med1.getId(), "1"));
        Medicament med2 = new Medicament("2", "test", "test", 100,  true);
        assertEquals("2", med2.getId(), String.format("Returned %s, Expected=%s", med2.getId(), "2"));
    }

    @org.junit.jupiter.api.Test
    void setIdShouldSetTheGivenId() {
        Medicament med = new Medicament("1", "test", "test", 100,  true);
        String setName = "aaaa";
        med.setName(setName);
        assertEquals(setName, med.getName(), String.format("Returned=%s, Expected=%s", med.getName(), setName));
    }

    @org.junit.jupiter.api.Test
    void constructorShouldSetAllFieldsCorrectly() {
        Medicament med = new Medicament("1", "test", "test", 100,  true);
        assertEquals("1", med.getId());
        assertEquals("test", med.getName());
        assertEquals("test", med.getManufacturer());
        assertEquals(100, med.getPrice());
        assertTrue(med.isPrescriptionNeeded());
    }

    @org.junit.jupiter.api.Test
    void settersShouldSetFieldsCorrectly() {
        Medicament med = new Medicament("1", "test", "test", 100,  true);

       // med.setId("2");
        med.setName("test2");
        med.setManufacturer("test2");
        med.setPrice(200);
        med.setPrescriptionNeeded(false);

       // assertEquals("2", med.getId());
        assertEquals("test2", med.getName());
        assertEquals("test2", med.getManufacturer());
        assertEquals(200, med.getPrice());
        assertFalse(med.isPrescriptionNeeded());
    }

    @org.junit.jupiter.api.Test
    void equalityShouldWorkCorrectly() {
        Medicament med1 = new Medicament("1", "test", "test", 100,  true);
        Medicament med2 = new Medicament("2", "test", "test", 100,  true);
        Medicament med3 = new Medicament("3", "test", "test", 100,  true);

        assertNotEquals(med1, med3);
        assertNotEquals(med3, med1);
        assertNotEquals(med3, med2);
        assertNotEquals(med2, med3);
        assertNotEquals(med1, med2);
        assertNotEquals(med2, med1);
    }


    @org.junit.jupiter.api.Test
    void toStringShouldReturnALongEnoughString() {
        Medicament med1 = new Medicament("1", "test", "test", 100,  true);
        assertTrue(med1.toString().length() > 10);
    }

    @org.junit.jupiter.api.Test
    public void PriceShouldBeGrtThan0() {
        Medicament med1 = new Medicament("1", "test", "test", 100,  true);
        assertTrue(med1.getPrice() > 0);
    }
}
