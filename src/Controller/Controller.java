package Controller;

import GUI.UdlejningPane;
import Model.*;
import Storage.Storage;
import org.w3c.dom.ls.LSOutput;

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

    public static ArrayList<Ordre> getDagensOrdrer(){
        ArrayList<Ordre> dagensOrdrer = new ArrayList<>();
        for(Ordre ordre: Storage.getOrdreArrayList())
            if(ordre.getDato().equals(LocalDate.now()))
                dagensOrdrer.add(ordre);
        return dagensOrdrer;
    }

    public static int solgtKlip(LocalDate start, LocalDate slut){
        int solgt = 0;
        for(Ordre ordre: Storage.getOrdreArrayList())
            if(ordre.getDato().isAfter(start) && ordre.getDato().isBefore(slut))
                for(OrdreLinje ordreLinje: ordre.getOrdreLinjeArrayList()){
                    if(ordreLinje.getPris().getProdukt().getNavn().contains("Klippekort"))
                        solgt += ordreLinje.getAntalAfProdukter();
                }
        return solgt *4;
    }
    public static int brugtKlip(LocalDate start, LocalDate slut){
        int solgt = 0;
        for(Ordre ordre: Storage.getOrdreArrayList())
            if(ordre.getDato().isAfter(start) || ordre.getDato().isEqual(start)  && ordre.getDato().isBefore(slut) || ordre.getDato().isEqual(slut))
                for(OrdreLinje ordreLinje: ordre.getOrdreLinjeArrayList())
                    solgt +=ordreLinje.getAntalBrugteKlip();
        return solgt;
    }

    //Returnerer en liste med udelejede produkter indenfor en given slut og startdato.
    public static ArrayList<Pant> udlejedeProdukterMellemDatoer(LocalDate startDato, LocalDate slutDato){
        return udlejedeProdukterMellemDatoer(Storage.getOrdreArrayList(),startDato,slutDato);
    }

    public static ArrayList<Pant> udlejedeProdukterMellemDatoer(ArrayList<Ordre> ordrer,LocalDate startDato, LocalDate slutDato) {
        ArrayList<Pant> resultat = new ArrayList<>();
        for (int i = 0; i < ordrer.size(); i++) {
            if (ordrer.get(i) instanceof Udlejning){
                Udlejning udlejning = (Udlejning) ordrer.get(i);
                LocalDate startDatoUdlejning = udlejning.getStartDato();
                LocalDate slutDatoUdlejning = udlejning.getSlutDato();
                if (isBetween(startDato,slutDato,startDatoUdlejning) || isBetween(startDato,slutDato,slutDatoUdlejning)) {
                    for (OrdreLinje ordreLinje : udlejning.getOrdreLinjeArrayList()) {
                        resultat.add((Pant) ordreLinje.getPris().getProdukt());
                    }
                }
                }
        }
        return resultat;
    }
    //returnerer hvorvidt en dato ligger imellem en given start og slutdato.
    private static boolean isBetween(LocalDate start, LocalDate slut, LocalDate testDato){
        LocalDate startMinus1dag = start.minusDays(1);
        LocalDate slutPlus1dag = slut.plusDays(1);
        return testDato.isAfter(startMinus1dag) && testDato.isBefore(slutPlus1dag);

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
    public static Udlejning createUdlejning(String betalingsForm, LocalDate dato, LocalDate startDato, LocalDate slutDato){
        Udlejning udlejning = new Udlejning(betalingsForm, dato, startDato, slutDato);
        Storage.storeOrdre(udlejning);
        return udlejning;
    }
    public static ArrayList<Ordre>getUdlejningerAfklaret(){
        ArrayList<Ordre> udlejningList = new ArrayList<>();

        for(Ordre ordre: Storage.getOrdreArrayList()){
            if(ordre instanceof Udlejning && ordre.getBetalingsForm()!=null){
                udlejningList.add(ordre);
            }
        }
        return udlejningList;
    }

    public static ArrayList<Ordre>getUdlejningerIkkeAfklaret(){
        ArrayList<Ordre> udlejningList = new ArrayList<>();

        for(Ordre ordre: Storage.getOrdreArrayList()){
            if(ordre instanceof Udlejning && ordre.getBetalingsForm()==null){
                udlejningList.add(ordre);
            }
        }
        return udlejningList;
    }
    //-----------------------------------------------------------------------------------
    public static Pant createPant(String navn, String beskrivelse, Produktgruppe produktgruppe, double pant){
        Pant pantObjekt = produktgruppe.createPant(navn,beskrivelse,pant);
        return pantObjekt;
    }

    public static ArrayList<Pris> getPantProdukt() {
        ArrayList<Pris> pantList = new ArrayList<>();

        for (Produktgruppe produktgruppe : Storage.getProduktgruppeArrayList()) {
            if (produktgruppe.getNavn().contains("Udlejning")) {
                for (Produkt produkt : produktgruppe.getProduktArrayList()) {
                    pantList.add(produkt.getPrisListe().get(0));
                }
            }
        }
        return pantList;
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


    public static void initStorage() {
        Produktgruppe produktgruppeRund = createProduktGruppe("Rundvisning");
        Produktgruppe produktgruppe1 = createProduktGruppe("Flaskeøl");
        Produktgruppe produktgruppe2 = createProduktGruppe("Merch");
        Produktgruppe Udlejningen = createProduktGruppe("Udlejning");


        Produkt produktRund = produktgruppeRund.createProdukt("Rundvisning", "tilRundvisning");
        Produkt produkt1 = produktgruppe1.createProdukt("Forårsbryg", "6% 60cl");
        Produkt produkt2 = produktgruppe1.createProdukt("Pilsner", "5% 60cl");
        Produkt produkt3 = produktgruppe2.createProdukt("Classic", "5% 60cl");
        Pant Klosterbryg = Udlejningen.createPant("Klosterbryg", "20L", 200);
        Pant Kulsyre1 = Udlejningen.createPant("Kulsyre", "6 kg", 1000);


        Salgsituation salgsituationRund = Controller.createSalgsSituation("Rundvisning");
        Salgsituation salgsituationButik = Controller.createSalgsSituation("Butik");
        Salgsituation salgsituationFredagsBar = Controller.createSalgsSituation("Fredagsbar");
        Salgsituation salgsituationUdlejning = Controller.createSalgsSituation("Udlejning");

        Pris pris1 = salgsituationRund.createPris(100, 0, produktRund);
        Pris pris2 = salgsituationButik.createPris(35, 0, produkt1);
        Pris pris3 = salgsituationButik.createPris(35, 0, produkt2);
        Pris pris4 = salgsituationButik.createPris(35, 0, produkt3);

        Pris pris5 = salgsituationFredagsBar.createPris(70, 2, produkt1);
        Pris pris6 = salgsituationFredagsBar.createPris(70, 2, produkt2);
        Pris pris7 = salgsituationFredagsBar.createPris(70, 2, produkt3);
        Pris pris8 = salgsituationUdlejning.createPris(775, 0, Klosterbryg);
        Pris pris9 = salgsituationUdlejning.createPris(400, 0, Kulsyre1);


        Rundvisning rundvisning = createRundvisning("kort", LocalDate.now(), "09:30", "11:30", LocalDate.now());
        Rundvisning rundvisning2 = createRundvisning("kort", LocalDate.now(), "11:30", "13:30", LocalDate.now());
        rundvisning.createOrdrelinje(10, 0, 0, pris1);
        rundvisning2.createOrdrelinje(15, 0, 0, pris1);

        Udlejning udlejning = createUdlejning(null, LocalDate.now(), LocalDate.now(), LocalDate.of(2022, 05, 04));
        udlejning.createOrdrelinje(2, 0, 0, pris8);
        udlejning.createOrdrelinje(4, 0, 0, pris9);


        Udlejning udlejning2 = createUdlejning("Kort", LocalDate.now(), LocalDate.now(), LocalDate.of(2022, 05, 04));
        udlejning2.createOrdrelinje(4, 0, 0, pris8);

        Ordre ordre1 = createOrdre("Kort",LocalDate.now());
        ordre1.createOrdrelinje(2,0,2,pris5);
        ordre1.createOrdrelinje(1,0,1,pris6);
        ordre1.createOrdrelinje(3,0,0,pris7);





    }

}
