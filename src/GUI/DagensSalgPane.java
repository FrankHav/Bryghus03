package GUI;

import Controller.Controller;
import Model.Ordre;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class DagensSalgPane extends GridPane {
    private final ListView<Ordre> lvwOrdre = new ListView<>();



    public DagensSalgPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblOrdre = new Label("Dagens Salg");
        this.add(lblOrdre, 0, 0);

        this.add(lvwOrdre, 0, 1);
        lvwOrdre.getItems().setAll(Controller.getDagensOrdrer());

        Button btnAfslutOrdre = new Button("Opdater dagens ordrer");
        this.add(btnAfslutOrdre,0, 2);
        btnAfslutOrdre.setOnAction(event -> opdaterOdrer());

    }

    private void opdaterOdrer(){
        lvwOrdre.getItems().setAll(Controller.getDagensOrdrer());
    }


}
