package Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class ClientValidator implements IValidator<Client> {

    public void validate(Client client) {

        String CNP = client.getCNP();
        String errors = "";
        if (CNP.length() != 13) {
            errors += "CNP must be 13 digits long.\n";
        }

        List<String> list = new ArrayList<>(); // lista de CNP
        list.add(client.getCNP());
        for (int i = 0; i < list.size(); i++) {
            if (client.getCNP().contains(CNP)) {
                errors += "CNP must be unique";
            }

        }

        if (!errors.equals("")) {
            throw new RuntimeException(errors); }

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



