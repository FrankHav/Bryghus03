package Model;

import java.util.ArrayList;

public class Produkt {
    private String navn;
    private String beskrivelse;
    private int lagerantal;
    private Produktgruppe produktgruppe;
    Sampakning sampakning;

    public Produkt(String navn, String beskrivelse,Produktgruppe produktgruppe) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.lagerantal = 0;
        this.produktgruppe = produktgruppe;
    }

    public String getNavn() {
        return navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public int getLagerantal() {
        return lagerantal;
    }

    public Produktgruppe getProduktgruppe() {
        return produktgruppe;
    }

    public Sampakning getSampakning() {
        return sampakning;
    }
}
