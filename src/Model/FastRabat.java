package Model;

public class FastRabat implements Rabat{
    private double fastDiscount;

    public FastRabat(double fastDiscount) {
        this.fastDiscount = fastDiscount;
    }

    @Override
    public double getRabat(Double pris) {
        return fastDiscount;
    }
}
