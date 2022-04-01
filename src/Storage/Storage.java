package Storage;

import Model.Ordre;
import Model.Produkt;
import Model.Produktgruppe;
import Model.Salgsituation;

import java.util.ArrayList;

public class Storage {
private static final ArrayList<Salgsituation> SALGSITUATION_ARRAY_LIST = new ArrayList<>();
private static final ArrayList<Ordre> ORDRE_ARRAY_LIST = new ArrayList<>();

    //------------------------------------------------------------------------------------
    //Salgssituation
    public static ArrayList<Salgsituation> getSalgsituationArrayList() {
        return new ArrayList<>(SALGSITUATION_ARRAY_LIST);
    }

    public static Salgsituation storeSalgsSituation(Salgsituation salgsituation){
        if (!SALGSITUATION_ARRAY_LIST.contains(salgsituation)){
            SALGSITUATION_ARRAY_LIST.add(salgsituation);
        }
        return salgsituation;
    }

    public static void removeSalgsSituation(Salgsituation salgsituation){
        if (SALGSITUATION_ARRAY_LIST.contains(salgsituation)){
            SALGSITUATION_ARRAY_LIST.remove(salgsituation);
        }
    }
    //------------------------------------------------------------------------------------
    //Ordre
    public static ArrayList<Ordre> getOrdreArrayList() {
        return new ArrayList<>(ORDRE_ARRAY_LIST);
    }

    public static Ordre storeOrdre(Ordre ordre){
        if (!ORDRE_ARRAY_LIST.contains(ordre)){
            ORDRE_ARRAY_LIST.add(ordre);
        }
        return ordre;
    }

    public static void removeOrdre(Ordre ordre){
        if (ORDRE_ARRAY_LIST.contains(ordre)){
            ORDRE_ARRAY_LIST.remove(ordre);
        }
    }
}
