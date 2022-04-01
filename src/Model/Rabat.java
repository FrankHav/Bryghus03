package Model;

public interface Rabat {
    default double getRabat(Double pris){
        return pris;
    }
}
