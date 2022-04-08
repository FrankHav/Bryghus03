package ControllerTest;

import Controller.Controller;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerTest {
    private Udlejning udlejningSlut1Jan;
    private Udlejning udlejningStart15Jan;
    private Udlejning udlejningStart30Jan;
    private Pant pantUdlejning1Jan;
    private Pant pantUdlejning15Jan;
    private Pant pantUdlejning30Jan;

    @BeforeEach
    void setup(){

        udlejningSlut1Jan = new Udlejning("Kort", LocalDate.now(),LocalDate.of(2020,12,5),LocalDate.of(2021,01,01));
        udlejningStart15Jan = new Udlejning("Kort", LocalDate.now(),LocalDate.of(2021,01,15),LocalDate.of(2021,01,20));
        udlejningStart30Jan = new Udlejning("Kort", LocalDate.now(),LocalDate.of(2021,01,30),LocalDate.of(2021,02,01));
        Produktgruppe produktgruppe = new Produktgruppe("Test");
        Salgsituation salgsituation = new Salgsituation("Test");
        pantUdlejning1Jan = produktgruppe.createPant("Testprodukt1","Et produkt til test",100);
        pantUdlejning15Jan = produktgruppe.createPant("Testprodukt2","Et produkt til test",100);
        pantUdlejning30Jan=  produktgruppe.createPant("Testprodukt3","Et produkt til test",100);
        Pris pris1 = salgsituation.createPris(100,2,pantUdlejning1Jan);
        Pris pris2 = salgsituation.createPris(100,2,pantUdlejning15Jan);
        Pris pris3 = salgsituation.createPris(100,2,pantUdlejning30Jan);
        udlejningSlut1Jan.createOrdrelinje(5,0,0,pris1);
        udlejningStart15Jan.createOrdrelinje(5,0,0,pris2);
        udlejningStart30Jan.createOrdrelinje(5,0,0,pris3);
    }

    @Test
    void udlejedeProdukterMellemDatoer_PåSlutDato(){
        //arrange
        ArrayList<Ordre> ordrer = new ArrayList<>();
        ordrer.add(udlejningSlut1Jan);
        ordrer.add(udlejningStart15Jan);
        ordrer.add(udlejningStart30Jan);
        LocalDate startDato = LocalDate.of(2021,01,01);
        LocalDate slutDato = LocalDate.of(2021,01,30);
        ArrayList<Pant> forventetListe = new ArrayList<>();
        forventetListe.add(pantUdlejning1Jan);
        forventetListe.add(pantUdlejning15Jan);
        forventetListe.add(pantUdlejning30Jan);

        //act
        ArrayList<Pant> faktiskListe = Controller.udlejedeProdukterMellemDatoer(ordrer,startDato,slutDato);
        //assert
        assertEquals(forventetListe.size(),faktiskListe.size());
        for(Pant pant:forventetListe){
            assertTrue(faktiskListe.contains(pant));
        }}

        @Test
        void udlejedeProdukterMellemDatoer_1DagEfterFørsteUdlejningSlutter(){
            //arrange
            ArrayList<Ordre> ordrer = new ArrayList<>();
            ordrer.add(udlejningSlut1Jan);
            ordrer.add(udlejningStart15Jan);
            ordrer.add(udlejningStart30Jan);
            LocalDate startDato = LocalDate.of(2021,01,02);
            LocalDate slutDato = LocalDate.of(2021,01,30);
            ArrayList<Pant> forventetListe = new ArrayList<>();
            forventetListe.add(pantUdlejning15Jan);
            forventetListe.add(pantUdlejning30Jan);

            //act
            ArrayList<Pant> faktiskListe = Controller.udlejedeProdukterMellemDatoer(ordrer,startDato,slutDato);
            //assert
            assertEquals(forventetListe.size(),faktiskListe.size());
            for(Pant pant:forventetListe){
                assertTrue(faktiskListe.contains(pant));
            }
    }

    @Test
    void udlejedeProdukterMellemDatoer_SlutterPåStartDato(){
        //arrange
        ArrayList<Ordre> ordrer = new ArrayList<>();
        ordrer.add(udlejningSlut1Jan);
        ordrer.add(udlejningStart15Jan);
        ordrer.add(udlejningStart30Jan);
        LocalDate startDato = LocalDate.of(2021,01,30);
        LocalDate slutDato = LocalDate.of(2021,02,05);
        ArrayList<Pant> forventetListe = new ArrayList<>();
        forventetListe.add(pantUdlejning30Jan);

        //act
        ArrayList<Pant> faktiskListe = Controller.udlejedeProdukterMellemDatoer(ordrer,startDato,slutDato);
        //assert
        assertEquals(forventetListe.size(),faktiskListe.size());
        for(Pant pant:forventetListe){
            assertTrue(faktiskListe.contains(pant));
        }
    }
    @Test
    void udlejedeProdukterMellemDatoer_datoUdenforInterval(){
        //arrange
        ArrayList<Ordre> ordrer = new ArrayList<>();
        ordrer.add(udlejningSlut1Jan);
        ordrer.add(udlejningStart15Jan);
        ordrer.add(udlejningStart30Jan);
        LocalDate startDato = LocalDate.of(2021,02,02);
        LocalDate slutDato = LocalDate.of(2021,02,03);
        ArrayList<Pant> forventetListe = new ArrayList<>();

        //act
        ArrayList<Pant> faktiskListe = Controller.udlejedeProdukterMellemDatoer(ordrer,startDato,slutDato);
        //assert
        assertEquals(forventetListe.size(),faktiskListe.size());
        for(Pant pant:forventetListe){
            assertTrue(faktiskListe.contains(pant));
        }
    }

}
