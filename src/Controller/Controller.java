package Controller;

import Model.*;
import Storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    //------------------------------------------------------------------------------------
    //Produkt

    public static Produkt createProdukt(String navn, String beskrivelse, Produktgruppe produktgruppe){
        Produkt produkt = produktgruppe.createProdukt(navn, beskrivelse);
        return produkt;
    }

    //------------------------------------------------------------------------------------
    //Produktgruppe

    public static Produktgruppe createProduktGruppe(String navn){
        Produktgruppe produktgruppe = new Produktgruppe(navn);
        return produktgruppe;
    }

    //------------------------------------------------------------------------------------
    //SalgsSituation

    public static Salgsituation createSalgsSituation(String navn){
        Salgsituation salgsituation = new Salgsituation(navn);
        Storage.storeSalgsSituation(salgsituation);
        return salgsituation;
    }

    public static ArrayList<Salgsituation> getSalgsSituation(){
        return Storage.getSalgsituationArrayList();
    }

    //------------------------------------------------------------------------------------
    //pris

    public static Pris createPris(double produktPris, int antalKlip, Produkt produkt, Salgsituation salgsituation){
        Pris pris = salgsituation.createPris(produktPris, antalKlip, produkt);
        return pris;
    }


    //-----------------------------------------------------------------------------------
    //Ordre

    public static Ordre createOrdre(String betalingsForm, LocalDate dato){
        Ordre ordre = new Ordre(betalingsForm,dato);
        Storage.storeOrdre(ordre);
        return ordre;
    }

    public static ArrayList<Ordre> getOrdrer(){
        return Storage.getOrdreArrayList();
    }

    //-----------------------------------------------------------------------------------
    //OrdreLinje

    public static OrdreLinje createOrdreLinje(int antalAfProdukter, int linjeNr, int antalBrugteKlip,Pris pris,Ordre ordre){
        OrdreLinje ordreLinje = ordre.createOrdrelinje(antalAfProdukter, linjeNr, antalBrugteKlip, pris);
        return ordreLinje;
    }


    //-----------------------------------------------------------------------------------

    public static ArrayList<Ordre> salgMellemDatoer(LocalDate startDato, LocalDate slutDato){
        ArrayList<Ordre> resultat = new ArrayList<>();
        for (int i = 0; i < Storage.getOrdreArrayList().size();i++){
            if (startDato.isAfter(Storage.getOrdreArrayList().get(i).getDato())
                    || startDato.isEqual(Storage.getOrdreArrayList().get(i).getDato())
                    && slutDato.isBefore(Storage.getOrdreArrayList().get(i).getDato())
                    || slutDato.isEqual(Storage.getOrdreArrayList().get(i).getDato())){
                resultat.add(Storage.getOrdreArrayList().get(i));
            }
        }
        return resultat;
    }

    public static int getBrugteKlip(){

    }

}
