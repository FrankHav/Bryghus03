package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ordre {
    private String betalingsForm;
    private LocalDate dato;
    private ArrayList<OrdreLinje> ordreLinjeArrayList = new ArrayList<>();

    public Ordre(String betalingsForm, LocalDate dato) {
        this.betalingsForm = betalingsForm;
        this.dato = dato;
    }

    public ArrayList<OrdreLinje> getOrdreLinjeArrayList() {
        return new ArrayList<>(ordreLinjeArrayList);
    }

    public OrdreLinje createOrdrelinje(int antalAfProdukter, int linjeNr, int antalBrugteKlip,Pris pris){
        OrdreLinje ordreLinje = new OrdreLinje(antalAfProdukter, linjeNr, antalBrugteKlip, pris);
        ordreLinjeArrayList.add(ordreLinje);
        return ordreLinje;
    }

    public void removeOrdreLinje(OrdreLinje ordreLinje){
        if (ordreLinjeArrayList.contains(ordreLinje))
            ordreLinjeArrayList.remove(ordreLinje);
    }

    public double samletOrdrePris(){
        double sum = 0;
        for (int i = 0; i < ordreLinjeArrayList.size(); i++){
            sum+= ordreLinjeArrayList.get(i).getSamletPrisMedRabat();
        }
        return sum;
    }

    public String getBetalingsForm() {
        return betalingsForm;
    }

    public LocalDate getDato() {
        return dato;
    }
}
