package ModelTest;

import Model.Pris;
import Model.Produkt;
import Model.Produktgruppe;
import Model.Salgsituation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalgsSituationTest {
    private String navn;
    @BeforeEach
    public void setUpBeforeEach_PNConstructorBasisData()
    {
        final ArrayList<Pris> prisArrayList = new ArrayList<>();
    }

    @Test
    void createPris_Test()
    {
        //Arrange
        Salgsituation salgsituation = new Salgsituation("Fredagsbar");

        //Act
        Produktgruppe produktgruppe = new Produktgruppe("flaskeøl");
        Produkt produkt = produktgruppe.createProdukt("Forårsbryg", "7% alk");
        Pris pris = salgsituation.createPris(70, 2, produkt);
        //Assert
        assertEquals(70, pris.getProduktPris());
        assertEquals(2, pris.getAntalKlip());
        assertTrue(salgsituation.getPrisArrayList().contains(pris));

    }
}
