package Domain;

import java.util.Objects;

public class Transaction extends Entity{
    private String id_med, id_card_client, date, time;
    private int quantity;
    private double basePrice;
    private double discount;

    public Transaction(String id, String id_med, String id_card_client, int quantity, String date, String time, double basePrice, double discount) {
        super(id);
        this.id_med = id_med;
        this.id_card_client = id_card_client;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.basePrice = basePrice;
        this.discount = discount;
    }

    /**
     * Custom getter for the discounted price.
     * @return the price after applying the discount.
     */
    public double getDiscountedPrice() {
        return basePrice*quantity - discount * basePrice*quantity;
    }

    public String getId_med() {
        return id_med;
    }

    public void setId_med(String id_med) {
        this.id_med = id_med;
    }

    public String getId_card_client() {
        return id_card_client;
    }

    public void setId_card_client(String id_card_client) {
        this.id_card_client = id_card_client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getQuantity() == that.getQuantity() &&
                Double.compare(that.getBasePrice(), getBasePrice()) == 0 &&
                Double.compare(that.getDiscount(), getDiscount()) == 0 &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getId_med(), that.getId_med()) &&
                Objects.equals(getId_card_client(), that.getId_card_client()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTime(), that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getId_med(), getId_card_client(), getDate(), getTime(), getQuantity(), getBasePrice(), getDiscount());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + getId() + '\'' +
                ", id_med='" + id_med + '\'' +
                ", id_card_client='" + id_card_client + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", quantity=" + quantity +
                ", basePrice=" + basePrice +
                ", discount=" + discount +
                '}';
    }
}
