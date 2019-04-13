package Domain;

import CustomExceptions.InvalidCNPException;
import CustomExceptions.NonUniqueCNPException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClientValidator implements IValidator<Client> {

    public void validate(Client client) throws InvalidCNPException, NonUniqueCNPException {

        String CNP;
            try {
                CNP = client.getCNP();
                if (CNP.length() != 13) {
                    throw new InvalidCNPException("CNP should be 13 digits long");
                }
            }
            catch (InvalidCNPException exe) {
                 throw new RuntimeException(exe.getMessage());
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



