package Model;

import java.util.ArrayList;

public class Produkt {
    private String navn;
    private String beskrivelse;
    private int lagerantal;
    private final ArrayList<Pris> prisListe = new ArrayList<>();
    private Produktgruppe produktgruppe;
    Sampakning sampakning;


    public Produkt(String navn, String beskrivelse,Produktgruppe produktgruppe) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.lagerantal = 0;
        this.produktgruppe = produktgruppe;
    }


    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public ArrayList<Pris> getPrisListe() {
        return new ArrayList<>(prisListe);
    }

    public void addPris(Pris pris){
        if(!prisListe.contains(pris)){
            prisListe.add(pris);
            pris.setProdukt(this);
        }
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

    @Override
    public String toString() {
        return "Produkt{" +
                "navn='" + navn + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                ", lagerantal=" + lagerantal +
                ", produktgruppe=" + produktgruppe +
                ", sampakning=" + sampakning +
                '}';
    }
}
