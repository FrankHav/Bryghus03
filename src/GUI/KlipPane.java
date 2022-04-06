package GUI;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class KlipPane extends GridPane {
    private final TextField txfSolgteKlippekort = new TextField();
    private final TextField txfBrugteKlip = new TextField();
    private final DatePicker dpStartDato = new DatePicker();
    private final DatePicker dpSlutDato = new DatePicker();
    private Label lblError;


    public KlipPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblSolgteKlippekort = new Label("Solgte klip: ");
        this.add(lblSolgteKlippekort, 0, 0);
        this.add(txfSolgteKlippekort,1,0);
        txfSolgteKlippekort.setEditable(false);

        Label lblBrugteKlip = new Label("Brugt klip: ");
        this.add(lblBrugteKlip, 0, 1);
        this.add(txfBrugteKlip, 1, 1);
        txfBrugteKlip.setEditable(false);

        Label lblStartDato = new Label("StartDato: ");
        this.add(lblStartDato, 0, 2);
        this.add(dpStartDato, 1, 2);



        Label lblSlutDato = new Label("SlutDato: ");
        this.add(lblSlutDato, 0, 4);
        this.add(dpSlutDato, 1, 4);



        Button btnOpretOrdre = new Button("Se klip for dato");
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
            txfBrugteKlip.setText(String.valueOf(Controller.brugtKlip(dpStartDato.getValue(), dpSlutDato.getValue())));
            txfSolgteKlippekort.setText(String.valueOf(Controller.solgtKlip(dpStartDato.getValue(), dpSlutDato.getValue())));
            lblError.setText("");
        }

    }

}
