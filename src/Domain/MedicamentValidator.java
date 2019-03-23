package Domain;

public class MedicamentValidator implements IValidator<Medicament> {
    public void validate (Medicament med) {
        if (med.getPrice()<=0) {
            throw new RuntimeException("Price should be greater then 0");
        }
    }
}
