package Model;

import java.util.ArrayList;

public class OrdreLinje {
    private int antalAfProdukter;
    private int linjeNr;
    private int antalBrugteKlip;
    Pris pris;
    Rabat rabat;

    public OrdreLinje(int antalAfProdukter, int linjeNr, int antalBrugteKlip,Pris pris) {
        this.antalAfProdukter = antalAfProdukter;
        this.linjeNr = linjeNr;
        this.antalBrugteKlip = antalBrugteKlip;
        this.pris = pris;
    }

    public int getAntalAfProdukter() {
        return antalAfProdukter;
    }

    public void setAntalAfProdukter(int antalAfProdukter) {
        this.antalAfProdukter = antalAfProdukter;
    }

    public int getLinjeNr() {
        return linjeNr;
    }

    public void setLinjeNr(int linjeNr) {
        this.linjeNr = linjeNr;
    }

    public int getAntalBrugteKlip() {
        return antalBrugteKlip;
    }

    public void setAntalBrugteKlip(int antalBrugteKlip) {
        this.antalBrugteKlip = antalBrugteKlip;
    }

    public Pris getPris() {
        return pris;
    }

    private double samletPris(){
        int klip = 0;
        while (antalBrugteKlip != 0 && antalBrugteKlip>=pris.getAntalKlip()){
                klip++;
                antalBrugteKlip = antalBrugteKlip - pris.getAntalKlip();
        }
        return antalAfProdukter - klip * pris.getProduktPris();
    }

    public Double getSamletPrisMedRabat() {
        double sum = samletPris();
        if (rabat!= null)
        sum = sum - rabat.getRabat(sum);
        return sum;
    }

    public void setRabat(Rabat rabat) {
        this.rabat = rabat;
    }
}
