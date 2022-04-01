package Model;

import java.util.ArrayList;

public class Sampakning extends Produkt {
    private int antal;
    private final ArrayList<Produkt> produktArrayList = new ArrayList<>();


    public Sampakning(String navn, String beskrivelse, Produktgruppe produktgruppe, int antal) {
        super(navn, beskrivelse, produktgruppe);
        this.antal = antal;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public ArrayList<Produkt> getProduktArrayList() {
        return produktArrayList;
    }

    public void addProduktTilSampakning(Produkt produkt){
        produktArrayList.add(produkt);
    }

    public void removeProduktTilSampakning(Produkt produkt){
        if (produktArrayList.contains(produkt))
            produktArrayList.remove(produkt);
    }

}
