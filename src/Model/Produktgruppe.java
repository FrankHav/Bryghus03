package Model;

import java.util.ArrayList;

public class Produktgruppe {
    private String navn;
    private final ArrayList<Produkt> produktArrayList = new ArrayList<>();

    public Produktgruppe(String navn) {
        this.navn = navn;
    }

    public ArrayList<Produkt> getProduktArrayList() {
        return new ArrayList<>(produktArrayList);
    }

    public Produkt createProdukt(String navn, String beskrivelse){
        Produkt produkt = new Produkt(navn, beskrivelse, this);
        produktArrayList.add(produkt);
        return produkt;
    }

    public Pant createPant(String navn, String beskrivelse, double pant) {
        Pant pantObjekt = new Pant(navn,beskrivelse,this,pant);
        produktArrayList.add(pantObjekt);
        return pantObjekt;
    }

    public String getNavn() {
        return navn;
    }

    @Override
    public String toString() {
        return "Produktgruppe{" +
                "navn='" + navn;
    }
}
