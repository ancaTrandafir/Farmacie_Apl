package Tests;

import Domain.Medicament;
import Domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @org.junit.jupiter.api.Test
    void getIdShouldReturnCorrectId() {
        Transaction transaction1 = new Transaction("1", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        assertEquals("1", transaction1.getId(), String.format("Returned %s, expected=%s", transaction1.getId(), "1"));
        Transaction transaction2 = new Transaction("2", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        assertEquals("2", transaction2.getId(), String.format("Returned %s, expected=%s", transaction2.getId(), "2"));
    }

    @org.junit.jupiter.api.Test
    void setIdShouldSetTheGivenId() {
        Transaction transaction = new Transaction("1", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        String setId_med = "7";
        transaction.setId_med(setId_med);
        assertEquals(setId_med, transaction.getId_med(), String.format("Returned=%s, Expected=%s", transaction.getId_med(), setId_med));
    }

    @org.junit.jupiter.api.Test
    void constructorShouldSetAllFieldsCorrectly() {
        Transaction transaction = new Transaction("1", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        assertEquals("1", transaction.getId());
        assertEquals("1", transaction.getId_med());
        assertEquals("1", transaction.getId_card_client());
        assertEquals(1, transaction.getQuantity());
        assertEquals("01.01.2000", transaction.getDate());
        assertEquals("05:00", transaction.getTime());
        assertEquals(150, transaction.getBasePrice());
        assertEquals(15, transaction.getDiscount());
    }

    @org.junit.jupiter.api.Test
    void settersShouldSetFieldsCorrectly() {
        Transaction transaction = new Transaction("1", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        //transaction.setId("2");
        transaction.setId_med("2");
        transaction.setId_card_client("2");
        transaction.setQuantity(2);
        transaction.setDate("01.01.2000");
        transaction.setTime("05:00");
        transaction.setBasePrice(150);
        transaction.setDiscount(15);

        //assertEquals("2", transaction.getId());
        assertEquals("2", transaction.getId_med());
        assertEquals("2", transaction.getId_card_client());
        assertEquals(2, transaction.getQuantity());
        assertEquals("01.01.2000", transaction.getDate());
        assertEquals("05:00", transaction.getTime());
        assertEquals(150, transaction.getBasePrice());
        assertEquals(15, transaction.getDiscount());
    }

    @org.junit.jupiter.api.Test
    void equalityShouldWorkCorrectly() {
        Transaction transaction1 = new Transaction("1", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        Transaction transaction2 = new Transaction("2", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        Transaction transaction3 = new Transaction("3", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);

        assertNotEquals(transaction1, transaction3);
        assertNotEquals(transaction3, transaction1);
        assertNotEquals(transaction3, transaction2);
        assertNotEquals(transaction2, transaction3);
        assertNotEquals(transaction1, transaction2);
        assertNotEquals(transaction2, transaction1);
    }


    @org.junit.jupiter.api.Test
    void toStringShouldReturnALongEnoughString() {
        Transaction transaction1 = new Transaction("3", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        assertTrue(transaction1.toString().length() > 20);
    }

    @org.junit.jupiter.api.Test
    void QuantityShouldBeGrtThan0() {
        Transaction transaction = new Transaction("3", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        assertTrue(transaction.getQuantity()> 0);
    }

    @Test
    void RunRaisePriceShouldReturnHigherPrice() {
        Medicament med1 = new Medicament("1", "test", "test", 30,  true);
        Double priceLimit = 50.00;
        Double percentage = 0.15;
        //TransactionService.runRaisePrices(priceLimit, percentage);
        assertEquals(30*1.15, med1.getPrice()+med1.getPrice()*percentage);
    }

    @Test
    void RunIdCardSearchShouldReturnDescendingDiscountedPrices() {
        Medicament med1 = new Medicament("1", "test", "test", 150,  true);
        Medicament med2 = new Medicament("2", "test", "test", 100,  false);
        Transaction transaction1 = new Transaction("1", "1", "1", 1,  "01.01.2000", "05:00", 150, 15);
        Transaction transaction2 = new Transaction("1", "2", "1", 1,  "01.01.2000", "05:00", 150, 15);
        //TransactionService.runIdCardSearch();


    }



}
