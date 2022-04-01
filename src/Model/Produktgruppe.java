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

    public String getNavn() {
        return navn;
    }

}
