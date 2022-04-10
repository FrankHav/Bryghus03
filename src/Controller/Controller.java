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
        return solgtKlip(Storage.getOrdreArrayList(),start,slut);
    }

    public static int solgtKlip(ArrayList<Ordre> ordrer,LocalDate start, LocalDate slut){
        int solgt = 0;
        for(Ordre ordre: ordrer)
            if(isBetween(start,slut,ordre.getDato()))
                for(OrdreLinje ordreLinje: ordre.getOrdreLinjeArrayList()){
                    if(ordreLinje.getPris().getProdukt().getNavn().contains("Klippekort"))
                        solgt += ordreLinje.getAntalAfProdukter();
                }
        return solgt *4;
    }
    public static int brugtKlip(LocalDate start, LocalDate slut){
        int solgt = 0;
        for(Ordre ordre: Storage.getOrdreArrayList())
            if(isBetween(start,slut,ordre.getDato()))
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
        Produktgruppe fadøl = createProduktGruppe("Fadøl");
        Produktgruppe spiritus = createProduktGruppe("Spiritus");
        Produktgruppe malt = createProduktGruppe("Malt");
        Produktgruppe anlæg = createProduktGruppe("Anlæg");
        Produktgruppe glas = createProduktGruppe("Glas");


        Produkt produktRund = produktgruppeRund.createProdukt("Rundvisning", "tilRundvisning");
        Pant Kulsyre1 = Udlejningen.createPant("Kulsyre", "6 kg", 1000);

        Produkt flaskeKlosterbryg = produktgruppe1.createProdukt("Klosterbryg","5.60.66");
        Produkt flaskeSweetGeorgiaBrown = produktgruppe1.createProdukt("Sweet Georgia Brown","5.6,0.66");
        Produkt flaskeEkstraPilsner = produktgruppe1.createProdukt("Ekstra Pilsner","6.2,0.66");
        Produkt flaskeCelebration = produktgruppe1.createProdukt("Celebration","6.2,0.66");
        Produkt flaskeBlondie = produktgruppe1.createProdukt("Blondie","6.2,0.66");
        Produkt flaskeForårsbryg = produktgruppe1.createProdukt("Forårsbryg","6.2,0.66");
        Produkt flaskeIndiaPaleAle = produktgruppe1.createProdukt("India Pale Ale","6.2,0.66");
        Produkt flaskeJulebryg = produktgruppe1.createProdukt("Julebryg","6.2,0.66");
        Produkt flaskeJuletønden = produktgruppe1.createProdukt("Jultønden","6.2,0.66");
        Produkt flaskeOldStrongAle = produktgruppe1.createProdukt("Old Strong Ale","6.2,0.66");
        Produkt flaskeFregattenJylland = produktgruppe1.createProdukt("Fregatten Jylland","6.2,0.66");
        Produkt flaskeImperialStout = produktgruppe1.createProdukt("Imperial Stout","6.2,0.66");
        Produkt flaskeTribute = produktgruppe1.createProdukt("Tribute","6.2,0.66");
        Produkt flaskeBlackMonster = produktgruppe1.createProdukt("Black Monster","7.1,0.66");

        Produkt fadKlosterbryg = fadøl.createProdukt( "Klosterbryg", "5.6, 0.40");
        Produkt fadJazzClassic = fadøl.createProdukt("Jazz Classic","5.8,0.40");
        Produkt fadEkstraPilsner = fadøl.createProdukt("Ekstra Pilsner","6.2,0.40");
        Produkt fadCelebration = fadøl.createProdukt("Celebration","6.2,0.40");
        Produkt fadBlondie = fadøl.createProdukt("Blondie","6.2,0.40");
        Produkt fadForårsbryg = fadøl.createProdukt("Forårsbryg","6.2,0.40");
        Produkt fadIndiaPaleAle = fadøl.createProdukt("India Pale Ale","6.2,0.40");
        Produkt fadJulebryg = fadøl.createProdukt("Julebryg","6.2,0.40");
        Produkt fadImperialStout = fadøl.createProdukt("Imperial Stout","6.2,0.40");
        Produkt fadSpecial = fadøl.createProdukt("Imperial Stout","6.3,0.40");
        Produkt Æblebrus = fadøl.createProdukt("Æblebrus","0,0.40");
        Produkt chips = fadøl.createProdukt("Chips","");
        Produkt peanuts = fadøl.createProdukt("Peanuts","");
        Produkt cola = fadøl.createProdukt("Cola","0,0.40");
        Produkt nikoline = fadøl.createProdukt("Nikoline","0,0.40");
        Produkt sevenUp = fadøl.createProdukt("7-up","0,0.40");
        Produkt vand = fadøl.createProdukt("vand","0,0.40");
        Produkt ølpølser = fadøl.createProdukt("Ølpølser","");

        Produkt whiskey50cl45Procent = spiritus.createProdukt("Whisky 45% 50cl rør", "45,0.50");
        Produkt whiskey4cl45Procent = spiritus.createProdukt("Whisky 4cl", "45,0.04");
        Produkt whiskey50cl43Procent = spiritus.createProdukt("Whisky 43% 50cl rør", "43,0.50");
        Produkt udenEgesplint = spiritus.createProdukt("Whisky 40% 50cl u/ egesplint", "40,0.50");
        Produkt medEgesplint = spiritus.createProdukt("Whisky 40% 50cl m/ egesplint", "40,0.50");
        Produkt toWhiskyGlasMedBrikker = spiritus.createProdukt("2*whisky glas + brikker", "");
        Produkt liquorOfAarhus = spiritus.createProdukt("Liquor of Aarhus", "30,0.35");
        Produkt lyngGin50cl = spiritus.createProdukt("Lyng gin 50 cl", "45,0.50");
        Produkt lyngGin4cl = spiritus.createProdukt("Lyng gin 4 cl", "45,0.04");

        Produkt fustageKlosterbryg = Udlejningen.createPant("Klosterbryg","5.6,20",200);
        Produkt fustageJazzClassic = Udlejningen.createPant("Jazz Classic","5.8,25",200);
        Produkt fustageEkstraPilsner = Udlejningen.createPant("Ekstra Pilsner","6.2,25",200);
        Produkt fustageCelebration = Udlejningen.createPant("Celebration","6.2,20",200);
        Produkt fustageBlondie = Udlejningen.createPant("Blondie","6.2,25",200);
        Produkt fustageForårsbryg = Udlejningen.createPant("Forårsbryg","6.2,20",200);
        Produkt fustageIndiaPaleAle = Udlejningen.createPant("India Pale Ale","6.2,20",200);
        Produkt fustageJulebryg = Udlejningen.createPant("Julebryg","6.2,20",200);
        Produkt fustageImperialStout = Udlejningen.createPant("Imperial Stout","6.2,20",200);

        Produkt femOgTyveKg = malt.createProdukt("25 kg sæk", "");

        Produkt tShirt = produktgruppe2.createProdukt("t-shirt","");
        Produkt polo = produktgruppe2.createProdukt("polo","");
        Produkt cap = produktgruppe2.createProdukt("cap","");

        Produkt énHane = anlæg.createProdukt("1- hane","");
        Produkt toHaner = anlæg.createProdukt("2- haner","");
        Produkt barMedFlereHaner = anlæg.createProdukt("Bar med flere haner","");
        Produkt Levering = anlæg.createProdukt("Levering","");
        Produkt Krus = anlæg.createProdukt("Krus","");


        Salgsituation salgsituationRund = Controller.createSalgsSituation("Rundvisning");
        Salgsituation salgsituationButik = Controller.createSalgsSituation("Butik");
        Salgsituation salgsituationFredagsBar = Controller.createSalgsSituation("Fredagsbar");
        Salgsituation salgsituationUdlejning = Controller.createSalgsSituation("Udlejning");

        Pris prisFredagsBar1 = salgsituationFredagsBar.createPris(70,2,flaskeKlosterbryg);
        Pris prisFredagsBar2 = salgsituationFredagsBar.createPris(70,2,flaskeSweetGeorgiaBrown);
        Pris prisFredagsBar3 = salgsituationFredagsBar.createPris(70,2,flaskeEkstraPilsner);
        Pris prisFredagsBar4 = salgsituationFredagsBar.createPris(70,2,flaskeCelebration);
        Pris prisFredagsBar5 = salgsituationFredagsBar.createPris(70,2,flaskeBlondie);
        Pris prisFredagsBar6 = salgsituationFredagsBar.createPris(70,2,flaskeForårsbryg);
        Pris prisFredagsBar7 = salgsituationFredagsBar.createPris(70,2,flaskeIndiaPaleAle);
        Pris prisFredagsBar8 = salgsituationFredagsBar.createPris(70,2,flaskeJulebryg);
        Pris prisFredagsBar9 = salgsituationFredagsBar.createPris(70,2,flaskeJuletønden);
        Pris prisFredagsBar10 = salgsituationFredagsBar.createPris(70,2,flaskeOldStrongAle);
        Pris prisFredagsBar11 = salgsituationFredagsBar.createPris(70,2,flaskeFregattenJylland);
        Pris prisFredagsBar12 = salgsituationFredagsBar.createPris(70,2,flaskeImperialStout);
        Pris prisFredagsBar13 = salgsituationFredagsBar.createPris(70,2,flaskeTribute);
        Pris prisFredagsBar14 = salgsituationFredagsBar.createPris(100,3,flaskeBlackMonster);

        Pris prisFredagsBar15 =  salgsituationFredagsBar.createPris(38,1,fadKlosterbryg);
        Pris prisFredagsBar16 =  salgsituationFredagsBar.createPris(38,1,fadJazzClassic);
        Pris prisFredagsBar17 =  salgsituationFredagsBar.createPris(38,1,fadEkstraPilsner);
        Pris prisFredagsBar18 =  salgsituationFredagsBar.createPris(38,1,fadCelebration);
        Pris prisFredagsBar19 =  salgsituationFredagsBar.createPris(38,1,fadBlondie);
        Pris prisFredagsBar20 =  salgsituationFredagsBar.createPris(38,1,fadForårsbryg);
        Pris prisFredagsBar21 =  salgsituationFredagsBar.createPris(38,1,fadIndiaPaleAle);
        Pris prisFredagsBar22 =  salgsituationFredagsBar.createPris(38,1,fadJulebryg);
        Pris prisFredagsBar23 =  salgsituationFredagsBar.createPris(38,1,fadImperialStout);
        Pris prisFredagsBar24 =  salgsituationFredagsBar.createPris(38,1,fadSpecial);
        Pris prisFredagsBar25 =  salgsituationFredagsBar.createPris(15,0,Æblebrus);
        Pris prisFredagsBar26 =  salgsituationFredagsBar.createPris(10,0,chips);
        Pris prisFredagsBar27 =  salgsituationFredagsBar.createPris(15,0,peanuts);
        Pris prisFredagsBar28 =  salgsituationFredagsBar.createPris(15,0,cola);
        Pris prisFredagsBar29 =  salgsituationFredagsBar.createPris(15,0,nikoline);
        Pris prisFredagsBar30 =  salgsituationFredagsBar.createPris(15,0,sevenUp);
        Pris prisFredagsBar31 =  salgsituationFredagsBar.createPris(10,0,vand);
        Pris prisFredagsBar32 =  salgsituationFredagsBar.createPris(30,1,ølpølser);

        Pris prisFredagsBar33 =salgsituationFredagsBar.createPris(599,0,whiskey50cl45Procent);
        Pris prisFredagsBar34 =salgsituationFredagsBar.createPris(50,0,whiskey4cl45Procent);
        Pris prisFredagsBar35 =salgsituationFredagsBar.createPris(499,0,whiskey50cl43Procent);
        Pris prisFredagsBar36 =salgsituationFredagsBar.createPris(300,0,udenEgesplint);
        Pris prisFredagsBar37 =salgsituationFredagsBar.createPris(350,0,medEgesplint);
        Pris prisFredagsBar38 =salgsituationFredagsBar.createPris(80,0,toWhiskyGlasMedBrikker);
        Pris prisFredagsBar39 =salgsituationFredagsBar.createPris(175,0,liquorOfAarhus);
        Pris prisFredagsBar40 =salgsituationFredagsBar.createPris(350,0,lyngGin50cl);
        Pris prisFredagsBar41 =salgsituationFredagsBar.createPris(40,0,lyngGin4cl);

        Pris prisFredagsBar42 =salgsituationFredagsBar.createPris(70,0,tShirt);
        Pris prisFredagsBar43 =salgsituationFredagsBar.createPris(100,0,polo);
        Pris prisFredagsBar44 =salgsituationFredagsBar.createPris(30,0,cap);

        salgsituationButik.createPris(36,0,flaskeKlosterbryg);
        salgsituationButik.createPris(36,0,flaskeSweetGeorgiaBrown);
        salgsituationButik.createPris(36,0,flaskeEkstraPilsner);
        salgsituationButik.createPris(36,0,flaskeCelebration);
        salgsituationButik.createPris(36,0,flaskeBlondie);
        salgsituationButik.createPris(36,0,flaskeForårsbryg);
        salgsituationButik.createPris(36,0,flaskeIndiaPaleAle);
        salgsituationButik.createPris(36,0,flaskeJulebryg);
        salgsituationButik.createPris(36,0,flaskeJuletønden);
        salgsituationButik.createPris(36,0,flaskeOldStrongAle);
        salgsituationButik.createPris(36,0,flaskeFregattenJylland);
        salgsituationButik.createPris(36,0,flaskeImperialStout);
        salgsituationButik.createPris(36,0,flaskeTribute);
        salgsituationButik.createPris(60,0,flaskeBlackMonster);

        salgsituationButik.createPris(599,0,whiskey50cl45Procent);
        salgsituationButik.createPris(499,0,whiskey50cl43Procent);
        salgsituationButik.createPris(300,0,udenEgesplint);
        salgsituationButik.createPris(350,0,medEgesplint);
        salgsituationButik.createPris(80,0,toWhiskyGlasMedBrikker);
        salgsituationButik.createPris(175,0,liquorOfAarhus);
        salgsituationButik.createPris(350,0,lyngGin50cl);

        Pris pris8 = salgsituationUdlejning.createPris(775,0,fustageKlosterbryg);
        salgsituationUdlejning.createPris(625,0,fustageJazzClassic);
        salgsituationUdlejning.createPris(575,0,fustageEkstraPilsner);
        salgsituationUdlejning.createPris(775,0,fustageCelebration);
        salgsituationUdlejning.createPris(700,0,fustageBlondie);
        salgsituationUdlejning.createPris(775,0,fustageForårsbryg);
        salgsituationUdlejning.createPris(775,0,fustageIndiaPaleAle);
        salgsituationUdlejning.createPris(775,0,fustageJulebryg);
        salgsituationUdlejning.createPris(775,0,fustageImperialStout);

        Pris pris9 = salgsituationButik.createPris(400,0,Kulsyre1);

        salgsituationButik.createPris(300,0,femOgTyveKg);

        salgsituationButik.createPris(70,0,tShirt);
        salgsituationButik.createPris(100,0,polo);
        salgsituationButik.createPris(30,0,cap);

        salgsituationButik.createPris(250,0,énHane);
        salgsituationButik.createPris(400,0,toHaner);
        salgsituationButik.createPris(500,0,barMedFlereHaner);
        salgsituationButik.createPris(500,0,Levering);
        salgsituationButik.createPris(60,0,Krus);

        Pris pris1 = salgsituationRund.createPris(100, 0, produktRund);



        Rundvisning rundvisning = createRundvisning("kort", LocalDate.now(), "09:30", "11:30", LocalDate.now());
        Rundvisning rundvisning2 = createRundvisning("kort", LocalDate.now(), "11:30", "13:30", LocalDate.now());
        rundvisning.createOrdrelinje(10, 0, 0, pris1);
        rundvisning2.createOrdrelinje(15, 0, 0, pris1);

        Udlejning udlejning = createUdlejning(null, LocalDate.now(), LocalDate.now(), LocalDate.of(2022, 05, 04));
        udlejning.createOrdrelinje(2, 0, 0, pris8);
        udlejning.createOrdrelinje(4, 0, 0, pris9);


        Udlejning udlejning2 = createUdlejning("Kort", LocalDate.now(), LocalDate.now(), LocalDate.of(2022, 05, 04));
        udlejning2.createOrdrelinje(4, 0, 0, pris8);





    }

}
