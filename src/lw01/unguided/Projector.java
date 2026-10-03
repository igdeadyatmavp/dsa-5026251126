package lw01.unguided;

public class Projector extends Rental {
    public Projector(String id, int days) {
        super(id, days);
    }

    @Override
    public int calculateCharge() {
        int days = getDays();
        int basecharge;

    if (days <= 3) {
                basecharge = days * 60000;
            } else {
                basecharge = 3 * 60000 + ((days - 3) * 45000);
            }

            return basecharge + 20000;
    }

    @Override 
    public String label() {
        return "Projector";
    }
}
