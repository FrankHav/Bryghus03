package Model;

import java.time.LocalDate;

public class Udlejning extends Ordre {
    private LocalDate startDato;
    private LocalDate slutDato;

    public Udlejning(String betalingsForm, LocalDate dato, LocalDate startDato, LocalDate slutDato) {
        super(betalingsForm, dato);
        this.startDato = startDato;
        this.slutDato = slutDato;
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


    @Override
    public double samletOrdrePris() {
        return super.samletOrdrePris();
    }



    @Override
    public String toString() {
        return "Udlejning{" +
                "startDato=" + startDato +
                ", slutDato=" + slutDato+
                '}';
    }
}
