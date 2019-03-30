package Domain;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientValidator implements IValidator<Client> {

    public void validate(Client client) throws InvalidCNPException, NonUniqueCNPException {

        List<String> list = new ArrayList<>(); // lista de CNP
        String CNP = client.getCNP();
        if (CNP.length() == 13) {
            list.add(client.getCNP());
        } else {
            throw new InvalidCNPException("CNP should be 13 digitis long.");
            }

        for (int i = 0; i < list.size(); i++) {
            if (!client.getCNP().contains(CNP)) {
                list.add(client.getCNP());
            } else {
                throw new NonUniqueCNPException("CNP should be unique");
            }
        }


        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            try {
                format.parse(client.getDateOfBirth());
            } catch (ParseException pe) {
                throw new RuntimeException("The date of birth is not in a correct format!");
            }

            try {
                format.parse(client.getDateOfRegistration());
            } catch (ParseException pe) {
                throw new RuntimeException("The date of registration is not in a correct format!");
            }
        }
    }



