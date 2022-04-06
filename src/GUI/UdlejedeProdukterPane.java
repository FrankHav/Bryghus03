package GUI;

import Controller.Controller;
import Model.Ordre;
import Model.Pant;
import Model.Produkt;
import Storage.Storage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class UdlejedeProdukterPane extends GridPane {
    private final ListView<Pant> lvwProdukter = new ListView<>();
    private final DatePicker dpStartDato = new DatePicker();
    private final DatePicker dpSlutDato = new DatePicker();
    private Label lblError;



    public UdlejedeProdukterPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblOrdre = new Label("Dagens Salg");
        this.add(lblOrdre, 0, 0);

        this.add(lvwProdukter, 0, 1);
        lvwProdukter.getItems().setAll();

        Label lblStartDato = new Label("StartDato: ");
        this.add(lblStartDato, 0, 2);
        this.add(dpStartDato, 1, 2);

        Label lblSlutDato = new Label("SlutDato: ");
        this.add(lblSlutDato, 0, 4);
        this.add(dpSlutDato, 1, 4);

        Button btnOpretOrdre = new Button("Opdater liste");
        this.add(btnOpretOrdre, 0, 5);
        btnOpretOrdre.setOnAction(event -> opdater());

        lblError = new Label();
        this.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");

    }

    private void opdater() {
        if(dpSlutDato.getValue().isBefore(dpStartDato.getValue()))
            lblError.setText("Slutdato er før startdato");
        else {
            lvwProdukter.getItems().setAll(Controller.udlejedeProdukterMellemDatoer(dpStartDato.getValue(),dpSlutDato.getValue()));
            lblError.setText("");
        }
    }


}
