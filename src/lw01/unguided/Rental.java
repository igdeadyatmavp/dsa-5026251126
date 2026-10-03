package lw01.unguided;

public abstract class Rental implements Chargeable {
    private String id;
    private int days;

    protected Rental(String id, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than zero.");
        }
        this.id = id;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public int getDays() {
        return days;
    }

    @Override
    public abstract int calculateCharge();

    public int calculateCharge(int units) {
        if (units <= 0) {
            throw new IllegalArgumentException("Units must be greater than zero.");
        }
        return units * calculateCharge();
    }

    public String label() {
        return id + " | " + label() + " | " + calculateCharge();
    }

    public String summary() {
        return id + " | " + label() + " | " + calculateCharge();    
    }
    
}
