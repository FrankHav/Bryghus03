package ModelTest;

import Model.Produkt;
import Model.Produktgruppe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProduktTest {
        private String navn;
        private String beskrivelse;
        private int lagerantal;

        @BeforeEach
        public void setUpBeforeEach_PNConstructorBasisData()
        {
            this.navn = "Påskebryg";
            this.beskrivelse = "7% lys øl 60cl";
            this.lagerantal = 0;
        }

        @Test
        void Produkt_korrektOprettelse()
        {
            //Arrange
            Produktgruppe produktgruppe = new Produktgruppe("Flaskeøl");

            //Act
            Produkt produkt = new Produkt(navn, beskrivelse, produktgruppe);
            //Assert
            //For at teste linket mellem produkt og produktgruppe
            assertEquals(produkt, produktgruppe.getProduktArrayList().get(0));
            //Lagerantal
            assertEquals(0, produkt.getLagerantal());
            //Navn
            assertEquals("Påskebryg", produkt.getNavn());
            //Beskrivelse.
            assertEquals("7% lys øl 60cl", produkt.getBeskrivelse());
        }


    }
