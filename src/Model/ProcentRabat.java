package Model;

public class ProcentRabat implements Rabat {
    private double discountProcent;

    public ProcentRabat(double discountProcent) {
        this.discountProcent = discountProcent;
    }

    @Override
    public double getRabat(Double price) {
        return price*discountProcent/100.0;
    }
}
