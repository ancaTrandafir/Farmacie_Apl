package Domain;

import java.util.Objects;

public class Medicament extends Entity {

    private String name, manufacturer;
    private double price;
    private boolean prescriptionNeeded;

    public Medicament(String id, String name, String manufacturer, double price, boolean prescriptionNeeded) {
        super(id);
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.prescriptionNeeded = prescriptionNeeded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPrescriptionNeeded() {
        return prescriptionNeeded;
    }

    public void setPrescriptionNeeded(boolean prescriptionNeeded) {
        this.prescriptionNeeded = prescriptionNeeded;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicament)) return false;
        Medicament that = (Medicament) o;
        return Double.compare(that.getPrice(), getPrice()) == 0 &&
                isPrescriptionNeeded() == that.isPrescriptionNeeded() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getManufacturer(), that.getManufacturer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getManufacturer(), getPrice(), isPrescriptionNeeded());
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                ", prescriptionNeeded=" + prescriptionNeeded +
                '}';
    }
}
