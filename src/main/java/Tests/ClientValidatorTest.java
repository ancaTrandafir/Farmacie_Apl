package Tests;

import Domain.Client;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ClientValidatorTest {
    String CNP;

    @Test
    public void CNPShouldBe13() {
        Client client = new Client("1", "test", "test", "2900502090051",  "01.01.1990", "01.01.2001");
        assertEquals("13", client.getCNP().length());
    }

    @org.junit.jupiter.api.Test
    public void CNPShouldBeUnique() {
        Client client1 = new Client("1", "test", "test", "2900502090051",  "01.01.1990", "01.01.2001");
        Client client2 = new Client("2", "test", "test", "1900502090051",  "01.01.1990", "01.01.2001");
        assertNotEquals(client1.getCNP(), client2.getCNP());
    }

    @org.junit.jupiter.api.Test
    public void dateShouldBeCorrectFormat() {
        SimpleDateFormat date = new SimpleDateFormat("01.01.2001");
        Client client = new Client("1", "test", "test", "2900502090051",  "01.01.1990", "01 martie 2001");
        assertEquals(date, client.getDateOfBirth());
        assertNotEquals(date, client.getDateOfRegistration());
    }





}
