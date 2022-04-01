package Model;

import java.util.ArrayList;

public class Salgsituation {
    private String navn;
    private final ArrayList<Pris> prisArrayList = new ArrayList<>();

    public Salgsituation(String navn) {
        this.navn = navn;
    }

    public Pris createPris(double produktPris, int antalKlip,Produkt produkt){
        Pris pris = new Pris(produktPris, antalKlip, produkt);
        prisArrayList.add(pris);
        return pris;
    }

    public void removePris(Pris pris){
            prisArrayList.remove(pris);
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public ArrayList<Pris> getPrisArrayList() {
        return new ArrayList<>(prisArrayList);
    }
}
