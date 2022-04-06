package Model;

public class Pris {
    private double produktPris;
    private int antalKlip;
    private Produkt produkt;

    public Pris(double produktPris, int antalKlip, Produkt produkt) {
        this.produktPris = produktPris;
        this.antalKlip = antalKlip;
        this.produkt = produkt;
        produkt.addPris(this);
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }

    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }

    public double getProduktPris() {
        return produktPris;
    }

    public void setProduktPris(double produktPris) {
        this.produktPris = produktPris;
    }

    public int getAntalKlip() {
        return antalKlip;
    }

    public void setAntalKlip(int antalKlip) {
        this.antalKlip = antalKlip;
    }

    public Produkt getProdukt() {
        return produkt;
    }


    @Override
    public String toString() {
        return "Pris{" +
                "produktPris=" + produktPris +
                ", antalKlip=" + antalKlip +
                ", produkt=" + produkt +
                '}';
    }
}
