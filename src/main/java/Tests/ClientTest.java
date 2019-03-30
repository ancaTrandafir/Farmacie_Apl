package Tests;
import Domain.*;
import Repository.IRepository;
import Repository.InMemoryRepository;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @org.junit.jupiter.api.Test
    void getIdShouldReturnCorrectId() {
        IValidator<Client> clientValidator = new ClientValidator();
        IRepository<Client> repo = new InMemoryRepository<>(clientValidator);
        Client client1 = new Client("1", "test", "test", "1111111111111",  "01.01.1990", "01.01.2001");
        assertEquals("1", client1.getId(), String.format("Returned %s, expected=%s", client1.getId(), "1"));
        Client client2 = new Client("2", "test", "test", "1111111111111",  "01.01.1990", "01.01.2001");
        assertEquals("2", client2.getId(), String.format("Returned %s, expected=%s", client2.getId(), "2"));
    }

    @org.junit.jupiter.api.Test
    void setIdShouldSetTheGivenId() {
        Client client = new Client("1", "test", "test", "1111111111111",  "01.01.1990", "01.01.2001");
        String setFirstName = "aaa";
        client.setFirstName(setFirstName);
        assertEquals(setFirstName, client.getFirstName(), String.format("Returned=%s, Expected=%s", client.getFirstName(), setFirstName));
    }

    @org.junit.jupiter.api.Test
    void constructorShouldSetAllFieldsCorrectly() {
        Client client = new Client("1", "test", "test", "1111111111111",  "01.01.1990", "01.01.2001");
        assertEquals("1", client.getId());
        assertEquals("test", client.getFirstName());
        assertEquals("test", client.getLastName());
        assertEquals("1111111111111", client.getCNP());
        assertEquals("01.01.1990", client.getDateOfBirth());
        assertEquals("01.01.2001", client.getDateOfRegistration());
    }

    @org.junit.jupiter.api.Test
    void settersShouldSetFieldsCorrectly() {
        Client client = new Client("1", "test", "test", "1111111111111",  "01.01.1990", "01.01.2001");
       // client.setId("2");
        client.setFirstName("test2");
        client.setLastName("test2");
        client.setCNP("1111111111111");
        client.setDateOfBirth("01.01.1990");
        client.setDateOfRegistration("01.01.2001");

        //assertEquals("2", client.getId());
        assertEquals("test2", client.getFirstName());
        assertEquals("test2", client.getLastName());
        assertEquals("1111111111111", client.getCNP());
        assertEquals("01.01.1990", client.getDateOfBirth());
        assertEquals("01.01.2001", client.getDateOfRegistration());
    }

    @org.junit.jupiter.api.Test
    void equalityShouldWorkCorrectly() {
        Client client1 = new Client("1", "test", "test", "1111111111111",  "01.01.1990", "01.01.2001");
        Client client2 = new Client("2", "test", "test", "1211111111111",  "01.01.1990", "01.01.2001");
        Client client3 = new Client("3", "test", "test", "2111111111111",  "01.01.1990", "01.01.2001");

        assertNotEquals(client1, client3);
        assertNotEquals(client3, client1);
        assertNotEquals(client3, client2);
        assertNotEquals(client2, client3);
        assertNotEquals(client1, client2);
        assertNotEquals(client2, client1);
    }


    @org.junit.jupiter.api.Test
    void toStringShouldReturnALongEnoughString() {
        Client client1 = new Client("3", "test", "test", "2111111111111",  "01.01.1990", "01.01.2001");

        assertTrue(client1.toString().length() > 20);
    }

    

}
