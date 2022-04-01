package ModelTest;

import Model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdreTest {

    @Test
    void Produkt_korrektOprettelse()
    {
        //Arrange
        String betalingsForm = "Kontant";
        LocalDate dato = LocalDate.of(2022, 1, 1);
        //Act

        //Opretter produktgruppe, samt at produktgruppen laver et produkt
        Produktgruppe produktgruppe = new Produktgruppe("Flakseøl");
        Produkt produkt = produktgruppe.createProdukt("Påskebryg", "alk 7%");

        //Opretter en salgssituation, samt at salgssituationen laver en pris.
        Salgsituation salgsituation = new Salgsituation("Fredagsbar");
        Pris pris = salgsituation.createPris(70, 2, produkt);

        //opretter ordreren, som så opretter en ordrelinje.
        Ordre ordre = new Ordre(betalingsForm,dato);
        OrdreLinje ordreLinje = ordre.createOrdrelinje(5, 1, 4, pris);

        //Assert
        //Tester at ordreren bliver oprettet korrekt.
        assertEquals(betalingsForm, ordre.getBetalingsForm());
        assertEquals(dato, ordre.getDato());
        //Tester linksne mellem alle objekterne virker, og bliver tilføjet til de rigtigt arraylister.
        assertTrue(ordre.getOrdreLinjeArrayList().contains(ordreLinje));
        assertEquals(ordreLinje.getPris(), pris);
        assertTrue(salgsituation.getPrisArrayList().contains(pris));
        assertEquals(pris.getProdukt(), produkt);
        assertTrue(produktgruppe.getProduktArrayList().contains(produkt));
        assertEquals(produkt.getProduktgruppe(), produktgruppe);

    }
}
