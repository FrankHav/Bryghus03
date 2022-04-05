package Controller;

import Model.*;
import Storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;
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
        Storage.storeProduktgruppe(produktgruppe);
        return produktgruppe;
    }
    public static ArrayList<Produktgruppe> getProduktgrupper(){
        return Storage.getProduktgruppeArrayList();
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
        return 0;
    }
    //-----------------------------------------------------------------------------------
    public static Rundvisning createRundvisning(String betalingsForm, LocalDate dato, String startTid, String slutTid, LocalDate datoForRundvisning){
        Rundvisning rundvisning = new Rundvisning(betalingsForm, dato, startTid, slutTid, datoForRundvisning);
        Storage.storeOrdre(rundvisning);
        return rundvisning;
    }
    public static ArrayList<Ordre>getRundvisninger(){
        ArrayList<Ordre> rundvisningerList = new ArrayList<>();

        for(Ordre ordre: Storage.getOrdreArrayList()){
            if(ordre instanceof Rundvisning){
                rundvisningerList.add(ordre);
            }
        }
        return rundvisningerList;
    }
    //-----------------------------------------------------------------------------------
    public static FastRabat createFastRabat(Double fastDiscount){
        FastRabat fastRabat = new FastRabat(fastDiscount);
        return fastRabat;
    }
    public static ProcentRabat createProcentRabat(Double procentDiscount){
        ProcentRabat procentRabat = new ProcentRabat(procentDiscount);
        return procentRabat;
    }



    public static void initStorage(){
        Produktgruppe produktgruppeRund = createProduktGruppe("Rundvisning");
        Produktgruppe produktgruppe1 = createProduktGruppe("Flaskeøl");
        Produktgruppe produktgruppe2 = createProduktGruppe("Merch");
        Produkt produktRund = produktgruppeRund.createProdukt("Rundvisning","tilRundvisning");
        Produkt produkt1 = produktgruppe1.createProdukt("Forårsbryg","6% 60cl");
        Produkt produkt2 = produktgruppe1.createProdukt("Pilsner","5% 60cl");
        Produkt produkt3 = produktgruppe2.createProdukt("Classic","5% 60cl");


        Salgsituation salgsituationRund = Controller.createSalgsSituation("Rundvisning");
        Salgsituation salgsituationButik = Controller.createSalgsSituation("Butik");
        Salgsituation salgsituationFredagsBar = Controller.createSalgsSituation("Fredagsbar");

        Pris pris1 = salgsituationRund.createPris(100,0,produktRund);
        Pris pris2 = salgsituationButik.createPris(35,0,produkt1);
        Pris pris3 = salgsituationButik.createPris(35,0,produkt2);
        Pris pris4 = salgsituationButik.createPris(35,0,produkt3);

        Pris pris5 = salgsituationFredagsBar.createPris(70,2,produkt1);
        Pris pris6 = salgsituationFredagsBar.createPris(70,2,produkt2);
        Pris pris7 = salgsituationFredagsBar.createPris(70,2,produkt3);




        Rundvisning rundvisning = createRundvisning("kort",LocalDate.now(), "09:30", "11:30",LocalDate.now());
        Rundvisning rundvisning2 = createRundvisning("kort",LocalDate.now(), "11:30", "13:30",LocalDate.now());
        rundvisning.createOrdrelinje(10,0,0, pris1);
        rundvisning2.createOrdrelinje(15,0,0,pris1);



    }

}
