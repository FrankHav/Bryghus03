package Model;

import java.util.ArrayList;

public class Produkt {
    private String navn;
    private String beskrivelse;
    private int lagerantal;
    private Produktgruppe produktgruppe;
    Sampakning sampakning;
    private final ArrayList<Pris> prisArrayList = new ArrayList<>();


    public Produkt(String navn, String beskrivelse,Produktgruppe produktgruppe) {
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.lagerantal = 0;
        this.produktgruppe = produktgruppe;
    }

    public void addPris(Pris pris){
        if(!prisArrayList.contains(pris))
            prisArrayList.add(pris);
            pris.setProdukt(this);
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
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
