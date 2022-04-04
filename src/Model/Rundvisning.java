package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rundvisning extends Ordre {
    private String startTid;
    private String slutTid;
    private LocalDate datoForRundvisning;


    public Rundvisning(String betalingsForm, LocalDate dato,String startTid, String slutTid, LocalDate datoForRundvisning) {
        super(betalingsForm, dato);
        this.startTid = startTid;
        this.slutTid = slutTid;
        this.datoForRundvisning = datoForRundvisning;
    }

    @Override
    public double samletOrdrePris() {
        double rundvisningPris =getOrdreLinjeArrayList().get(0).getPris().getProduktPris();
        int antal = getOrdreLinjeArrayList().get(0).getAntalAfProdukter();
        return antal*rundvisningPris;
    }

    public String getStartTid() {
        return startTid;
    }

    public void setStartTid(String startTid) {
        this.startTid = startTid;
    }

    public String getSlutTid() {
        return slutTid;
    }

    public void setSlutTid(String slutTid) {
        this.slutTid = slutTid;
    }


    @Override
    public String toString() {
        return "Rundvisning{" +
                "startTid=" + startTid +
                ", slutTid=" + slutTid +
                ", datoForRundvisning=" + datoForRundvisning +
                '}';
    }
}
