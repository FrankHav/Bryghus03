package Model;

import java.time.LocalDate;

public class Udlejning extends Ordre {
    private LocalDate startDato;
    private LocalDate slutDato;
    private int pant;

    public Udlejning(String betalingsForm, LocalDate dato, LocalDate startDato, LocalDate slutDato, int pant) {
        super(betalingsForm, dato);
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.pant = pant;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public int getPant() {
        return pant;
    }

    public void setPant(int pant) {
        this.pant = pant;
    }

    @Override
    public double samletOrdrePris() {
        return super.samletOrdrePris() - pant;
    }
    //det virker måske
    //test


    @Override
    public String toString() {
        return "Udlejning{" +
                "startDato=" + startDato +
                ", slutDato=" + slutDato +
                ", pant=" + pant +
                '}';
    }
}
