package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rundvisning extends Ordre {
    private LocalTime startTid;
    private LocalTime slutTid;
    private LocalDate datoForRundvisning;
    private int prisPrPerson;
    private int antalPersoner;

    /**
     * @param antalPersoner Der skal være mindst 15 personer.
     */
    public Rundvisning(String betalingsForm, LocalDate dato,LocalTime startTid, LocalTime slutTid, LocalDate datoForRundvisning, int pris, int antalPersoner) {
        super(betalingsForm, dato);
        this.startTid = startTid;
        this.slutTid = slutTid;
        this.datoForRundvisning = datoForRundvisning;
        this.prisPrPerson = pris;
        this.antalPersoner = antalPersoner;
    }

    @Override
    public double samletOrdrePris() {
        return prisPrPerson*antalPersoner;
    }

    public LocalTime getStartTid() {
        return startTid;
    }

    public void setStartTid(LocalTime startTid) {
        this.startTid = startTid;
    }

    public LocalTime getSlutTid() {
        return slutTid;
    }

    public void setSlutTid(LocalTime slutTid) {
        this.slutTid = slutTid;
    }

    public int getPrisPrPerson() {
        return prisPrPerson;
    }

    public void setPrisPrPerson(int prisPrPerson) {
        this.prisPrPerson = prisPrPerson;
    }

    public int getAntalPersoner() {
        return antalPersoner;
    }

    public void setAntalPersoner(int antalPersoner) {
        this.antalPersoner = antalPersoner;
    }
}
