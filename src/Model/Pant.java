package Model;

public class Pant extends Produkt {
    private double pant;

    public Pant(String navn, String beskrivelse, Produktgruppe produktgruppe, double pant) {
        super(navn, beskrivelse, produktgruppe);
        this.pant = pant;
    }

    public double getPant() {
        return pant;
    }

    public void setPant(double pant) {
        this.pant = pant;
    }
}
